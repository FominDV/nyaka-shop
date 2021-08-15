package ru.fomin.nyakashop.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.beans.Cart;
import ru.fomin.nyakashop.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartController {

    final CartService cartService;

    @GetMapping
    public Cart getCart() {
      return cartService.getCart();
    }

    @GetMapping("/add/{productId}")
    public void add(@PathVariable Long productId) {
       cartService.addProduct(productId);
    }

    @GetMapping("/decrement/{productId}")
    public void decrement(@PathVariable Long productId) {
         cartService.decrementProduct(productId);
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
