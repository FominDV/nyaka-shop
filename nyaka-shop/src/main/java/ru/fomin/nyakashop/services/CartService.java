package ru.fomin.nyakashop.services;

public interface CartService {

    void addProduct(Long productId);

    void removeProduct(Long orderItemId);

}
