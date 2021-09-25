package ru.fomin.nyakashop.services;

import ru.fomin.nyakashop.util.Cart;

public interface CartService {

    void addProduct( Long productId);

    void removeProduct( Long productId);

    Cart getCart();

    void decrementProduct( Long productId);

    void clearCart();

}
