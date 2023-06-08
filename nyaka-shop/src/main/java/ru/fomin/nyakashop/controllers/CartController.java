package ru.fomin.nyakashop.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fomin.nyakashop.mappers.impl.ProductMapperCast;
import ru.fomin.nyakashop.services.CartService;
import ru.fomin.nyakashop.util.Cart;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartController {

    @Autowired
    @Lazy
    final CartService cartService;
    @Autowired
    @Lazy
    final ProductMapperCast productMapperCast;

    @GetMapping
    public Cart getCart() {
        return cartService.getCart();
    }

    @GetMapping("/add/{productId}")
    public HttpStatus add(@PathVariable Long productId) {
        Integer quantity = productMapperCast.getQuentity(productId);
        if(quantity<=0) return HttpStatus.UNAUTHORIZED;
        cartService.addProduct(productId);
        return HttpStatus.OK;
    }

    @GetMapping("/decrement/{productId}")
    public HttpStatus decrement(@PathVariable Long productId) {
        cartService.decrementProduct(productId);
        return HttpStatus.OK;
    }

    @GetMapping("/remove/{productId}")
    public void remove(@PathVariable Long productId) {
        cartService.removeProduct(productId);
    }

    @GetMapping("/clear")
    public void clear() {
        cartService.clearCart();
    }

}
