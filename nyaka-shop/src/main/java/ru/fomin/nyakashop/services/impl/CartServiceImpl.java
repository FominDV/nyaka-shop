package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.beans.Cart;
import ru.fomin.nyakashop.dto.OrderItem;
import ru.fomin.nyakashop.dto.Product;
import ru.fomin.nyakashop.mappers.OrderItemMapper;
import ru.fomin.nyakashop.services.CartService;
import ru.fomin.nyakashop.services.OrderItemService;
import ru.fomin.nyakashop.services.ProductService;

import javax.annotation.Resource;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartServiceImpl implements CartService {

    @Resource
    ProductService productService;

    @Resource
    OrderItemMapper orderItemMapper;

    @Resource
    OrderItemService orderItemService;

    Cart cart;

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public void addProduct(Long productId) {
        Product product = productService.getProduct(productId);
        OrderItem orderItem = OrderItem.builder().product(product).build();
        cart.addProduct(orderItem);
    }

    @Override
    public void removeProduct(Long orderItemId) {
        OrderItem orderItem = OrderItem.builder().build();
        cart.removeProduct(orderItem);
    }

}
