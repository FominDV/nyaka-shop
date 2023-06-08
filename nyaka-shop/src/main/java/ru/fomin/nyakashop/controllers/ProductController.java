package ru.fomin.nyakashop.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.dto.ProductsPage;
import ru.fomin.nyakashop.entities.Product;
import ru.fomin.nyakashop.entities.Shipment;
import ru.fomin.nyakashop.enums.OrderStatus;
import ru.fomin.nyakashop.mappers.UniversalMapper;
import ru.fomin.nyakashop.mappers.impl.ProductMapperCast;
import ru.fomin.nyakashop.repositories.OrderItemRepository;
import ru.fomin.nyakashop.repositories.ProductRepository;
import ru.fomin.nyakashop.repositories.ShipmentsRepository;
import ru.fomin.nyakashop.services.CartService;
import ru.fomin.nyakashop.services.ProductService;
import ru.fomin.nyakashop.services.ResourceService;
import ru.fomin.nyakashop.services.impl.ResourceServiceImpl;
import ru.fomin.nyakashop.util.Cart;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductController {

    final ProductService productService;
    final UniversalMapper universalMapper;
    final CartService cartService;
    final ShipmentsRepository shipmentsRepository;
    final ProductMapperCast productMapperCast;
    final RedisTemplate redisTemplate;
    final OrderItemRepository orderItemRepository;
    final ProductRepository productRepository;
    final ResourceService resourceService;

    final ResourceServiceImpl imageService;

    @GetMapping(value = "/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return universalMapper.convert(productService.getProductOrThrow(id), ProductDto.class);
    }

    @ApiOperation(value = "Create a new 'Student' object")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "Number of the page", required = true, paramType = "query"),
            @ApiImplicitParam(name = "id",defaultValue = "12",value = "Enter an ID for the student.", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "Enter a Name for the Student.", dataTypeClass = String.class, paramType = "query")
    })

    @GetMapping
    public ProductsPage getProductList(
            @RequestParam(name = "page", defaultValue = "1") int pageIndex,
          @ApiIgnore @RequestParam MultiValueMap<String, String> filterMap
    ) {
        return productService.getProductsByFilter(--pageIndex, filterMap);
    }

    @GetMapping("/image")
    public String getImage(
            @RequestParam(name = "productId", required = false) Long productId
    ) {
        UUID imageId = productRepository.findById(productId).get().getImageId();
        if(imageId==null) return "img/not_found.jpg";
        return resourceService.getResourceUrl(imageId);
    }

    @PostMapping
    @ResponseBody
    public Long createNewProduct(@RequestBody ProductDto newProductDtoDto) {
        return productService.create(
                newProductDtoDto.getTitle(),
                newProductDtoDto.getDescription(),
                newProductDtoDto.getCategories().stream().map(c->c.getId()).collect(Collectors.toList()),
                newProductDtoDto.getPrice(),
        newProductDtoDto.getBrand().getId(),
        newProductDtoDto.getCountry().getId()
        ).getId();
    }

    @PutMapping
    public void updateProduct(@RequestBody ProductDto productDto) {
        productService.update(universalMapper.convert(productDto, Product.class), productDto.getPrice());
    }

    @DeleteMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
       var product = productService.getProduct(id).get();
       var cartMap = cartService.getCartMap();
        for(Map.Entry<String, Cart> entry: cartMap.entrySet()){
            var cart = entry.getValue();
            cart.setItems( cart.getItems().stream()
                    .filter(i->!i.getProduct().getId().equals(id))
                    .collect(Collectors.toList()));
            redisTemplate.opsForValue().set(entry.getKey(), cart);
        }
       var shipment = Shipment.builder()
               .product(product)
               .quantity(productMapperCast.getQuentity(product)*(-1))
               .build();
       shipmentsRepository.save(shipment);
        product.setIsDeleted(true);
        if(orderItemRepository.findAllByProduct_Id(id).stream()
                .anyMatch(i->i.getOrder().getStatus()!= OrderStatus.CANCELED&&i.getOrder().getStatus()!= OrderStatus.FINISHED)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
