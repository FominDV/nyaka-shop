package ru.fomin.nyakashop.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fomin.nyakashop.services.CartService;
import ru.fomin.nyakashop.util.Cart;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartController {

    final CartService cartService;

    @GetMapping
    public Cart getCart() {
        return cartService.getCart();
    }

    @GetMapping("/{uuid}/add/{productId}")
    public void add(@PathVariable String uuid, @PathVariable Long productId) {
        cartService.addProduct(uuid, productId);
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrement(@PathVariable String uuid, @PathVariable Long productId) {
        cartService.decrementProduct(uuid, productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void remove(@PathVariable String uuid, @PathVariable Long productId) {
        cartService.removeProduct(uuid, productId);
    }

    @GetMapping("/{uuid}/clear")
    public void clear(@PathVariable String uuid) {
        cartService.clearCart(uuid);
    }

    @GetMapping("/{uuid}/merge")
    public void merge( @PathVariable String uuid) {
        cartService.merge(uuid);
    }

}
