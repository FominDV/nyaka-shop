package ru.fomin.nyakashop.services;

import ru.fomin.nyakashop.util.Cart;

public interface CartService {

    void addProduct(String keySuffix, Long productId);

    void removeProduct(String keySuffix, Long productId);

    Cart getCart();

    Cart getCart(String keySuffix);

    void decrementProduct(String keySuffix, Long productId);

    void clearCart(String keySuffix);

    String generateCartUuid();

    void merge(String keySuffix);

}
