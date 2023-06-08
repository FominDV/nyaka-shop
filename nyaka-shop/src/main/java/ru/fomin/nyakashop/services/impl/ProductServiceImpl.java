package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import ru.fomin.nyakashop.dto.*;
import ru.fomin.nyakashop.entities.*;
import ru.fomin.nyakashop.exceptions.ResourceNotFoundException;
import ru.fomin.nyakashop.mappers.impl.ProductMapperCast;
import ru.fomin.nyakashop.repositories.*;
import ru.fomin.nyakashop.services.CategoryService;
import ru.fomin.nyakashop.services.PriceService;
import ru.fomin.nyakashop.services.ProductService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductServiceImpl implements ProductService {

    final ProductRepository productRepository;
    final PriceService priceService;
    final CategoryService categoryService;
    @Autowired
    @Lazy
    final ProductMapperCast productMapper;
    final PriceRepository priceRepository;
    final BrandRepository brandRepository;
    final CountryRepository countryRepository;
    final CategoryRepository categoryRepository;

    @Value("${pageSize.product}")
    int pageSize;

    @Override
    public ProductsPage getProductsByFilter(int pageIndex, MultiValueMap<String, String> filterMap) {
        List<Product> productPage = productRepository.findAllByIsDeleted(false);

        List<ProductDto> products = productPage.stream()
                .filter(p -> {
                    if(!filterMap.containsKey("title")) return true;
                  return   p.getTitle().contains(filterMap.get("title").get(0));})
                .filter(p -> {

                    BigDecimal min = filterMap.containsKey("min")? BigDecimal.valueOf(Double.parseDouble(filterMap.get("min").get(0))) : BigDecimal.ZERO;
                   return p.getCost().compareTo(min) >= 0;})
                .filter(p -> {
                        BigDecimal max = filterMap.containsKey("max") ? BigDecimal.valueOf(Double.parseDouble(filterMap.get("max").get(0))) : BigDecimal.valueOf(Double.MAX_VALUE);
                  return   p.getCost().compareTo(max) <= 0;})
                .filter(p->{
                    if(!filterMap.containsKey("brandId")) return true;
                   return p.getBrand().getId().equals(Long.valueOf(filterMap.get("brandId").get(0)));
                })
                .filter(p->{
                    if(!filterMap.containsKey("categoryId")) return true;
                    return p.getCategories().stream().map(c->c.getId()).anyMatch(c->c.equals(Long.valueOf(filterMap.get("categoryId").get(0))));
                })
                .sorted(Comparator.comparing(Product::getUpdatedAt))
                .map(productMapper::convert)
                .collect(Collectors.toList());
        Integer pagesLength = products.size()/pageSize + (products.size()%pageSize==0?0:1);
        int startIndex = pageSize*pageIndex;
        int endIndex = pageIndex==pagesLength-1?startIndex -1+  products.size() - (pagesLength-1)*pageSize:startIndex + pageSize -1;
        products= products.isEmpty()?products: products.subList(startIndex,endIndex+1);
        return ProductsPage.builder()
                .totalPages(pagesLength)
                .content(products)
                .build();
    }

    @Override
    public Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product getProductOrThrow(Long id) throws ResourceNotFoundException {
        return getProduct(id).orElseThrow(() -> new ResourceNotFoundException(Product.class));
    }

    @Override
    @Transactional
    public Product update(Product product, BigDecimal cost) {
        Product currentProduct = productRepository.getById(product.getId());
        currentProduct.setDescription(product.getDescription());
        currentProduct.setBrand(brandRepository.findById(product.getBrand().getId()).get());
        currentProduct.setCountry(countryRepository.findById(product.getCountry().getId()).get());
        currentProduct.setUpdatedAt(LocalDateTime.now());
        if (!currentProduct.getCost().equals(cost)) {
            Price newPrice = Price.builder()
                    .cost(cost)
                    .product(currentProduct)
                    .build();
            priceRepository.save(newPrice);
        }
        return productRepository.save(currentProduct);
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product create(String title, String description, List<Long> categories, BigDecimal price, Long brandID, Long countryID) {
        Country country = countryRepository.findById(countryID).get();
        Brand brand=brandRepository.findById(brandID).get();
        List<Category> categoryList = categoryRepository.findAllById(categories);
        Product product = Product.builder()
                .title(title)
                .description(description)
                .categories(categoryList)
                .brand(brand)
                .country(country)
                .isDeleted(false)
                .build();
        product=productRepository.save(product);
        Price newPrice = Price.builder()
                .cost(price)
                .product(product)
                .build();
        priceRepository.save(newPrice);
        return create(product);
    }

    @Override
    public void setImage(Long productId, UUID imageId) {
        Product product = getProductOrThrow(productId);
        product.setImageId(imageId);
        productRepository.save(product);
    }



}
