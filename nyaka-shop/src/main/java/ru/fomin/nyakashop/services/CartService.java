package ru.fomin.nyakashop.services;

import ru.fomin.nyakashop.beans.Cart;

public interface CartService {

    void addProduct(Long productId);

    void removeProduct(Long orderItemId);

    Cart getCart();

    void decrementProduct(Long productId);

    void clearCart();

}
