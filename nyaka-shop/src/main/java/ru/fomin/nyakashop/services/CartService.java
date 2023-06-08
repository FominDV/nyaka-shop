package ru.fomin.nyakashop.services;

import ru.fomin.nyakashop.util.Cart;

import java.util.List;
import java.util.Map;

public interface CartService {

    void addProduct( Long productId);

    void removeProduct( Long productId);

    Cart getCart();

    void decrementProduct( Long productId);

    void clearCart();

    List<Cart> getAllCart();

    Map<String, Cart> getCartMap();

}
