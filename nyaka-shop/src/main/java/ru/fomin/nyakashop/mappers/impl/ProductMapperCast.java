package ru.fomin.nyakashop.mappers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.dto.BrandDto;
import ru.fomin.nyakashop.dto.CategoryDto;
import ru.fomin.nyakashop.dto.CountryDto;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.OrderItem;
import ru.fomin.nyakashop.entities.Product;
import ru.fomin.nyakashop.entities.Shipment;
import ru.fomin.nyakashop.enums.OrderStatus;
import ru.fomin.nyakashop.repositories.FeedbackRepository;
import ru.fomin.nyakashop.repositories.OrderItemRepository;
import ru.fomin.nyakashop.repositories.ProductRepository;
import ru.fomin.nyakashop.repositories.ShipmentsRepository;
import ru.fomin.nyakashop.services.CartService;
import ru.fomin.nyakashop.services.ResourceService;
import ru.fomin.nyakashop.util.Cart;
import ru.fomin.nyakashop.util.SecurityUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductMapperCast {

    @Autowired
            @Lazy
    ResourceService resourceService;
    @Autowired
    @Lazy
    CartService cartService;
    @Autowired
    @Lazy
    OrderItemRepository orderItemRepository;
    @Autowired
    @Lazy
    ShipmentsRepository shipmentsRepository;
    @Autowired
    @Lazy
    ProductRepository productRepository;
    @Autowired
    @Lazy
    FeedbackRepository feedbackRepository;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm");

    public ProductDto convert(Product p) {
        UUID imageId = p.getImageId();
        String imageURL = imageId == null ?
                "" :
                resourceService.getResourceUrl(imageId);
        return ProductDto.builder()
                .id(p.getId())
                .title(p.getTitle())
                .description(p.getDescription())
                .categories(
                        p.getCategories().stream().map(c -> CategoryDto.builder().id(c.getId()).title(c.getTitle()).build()).collect(Collectors.toList())
                )
                .brand(BrandDto.builder().id(p.getBrand().getId()).title(p.getBrand().getTitle()).build())
                .country(CountryDto.builder().id(p.getCountry().getId()).title(p.getCountry().getTitle()).build())
                .price(p.getCost())
                .priceID(p.getPrice().getId())
                .imageUrl(imageURL)
                .quentity(getQuentity(p))
                .isBoughtByUser(isBoughtByUser(p))
                .createAt(dtf.format(p.getCreatedAt()))
                .updateAt(dtf.format(p.getUpdatedAt()))
                .build();
    }

    private Boolean isBoughtByUser(Product product){
        if( SecurityUtils.getEmail() == null) return false;
        return !orderItemRepository.findAllByProduct_IdAndOrder_User_LoginAndOrder_Status(product.getId(), SecurityUtils.getEmail(),OrderStatus.FINISHED)
                .isEmpty();
    }

   public Integer getQuentity(Product product) {
        List<Cart> carts = cartService.getAllCart();
        List<OrderItem> orderItems = orderItemRepository.findAll();
        List<Shipment> shipments = shipmentsRepository.findAll();
        Integer cartQuentity = carts.stream()
                .flatMap(c -> c.getItems().stream())
                .filter(i -> i.getProduct().getId().equals(product.getId()))
                .map(i -> i.getQuantity())
                .reduce(0, Integer::sum);
        Integer orderItemsQuentity = orderItems.stream()
                .filter(i -> i.getProduct().getId().equals(product.getId()))
                .filter(i->i.getOrder().getStatus() != OrderStatus.CANCELED)
                .map(i -> i.getQuantity())
                .reduce(0, Integer::sum);
        Integer shipmentsQuentity = shipments.stream()
                .filter(sh -> sh.getProduct().getId().equals(product.getId()))
                .map(sh -> sh.getQuantity())
                .reduce(0, Integer::sum);
        return shipmentsQuentity - orderItemsQuentity - cartQuentity;
    }

    public Integer getQuentity(Long productId){
        return getQuentity(productRepository.getById(productId));
    }

}
