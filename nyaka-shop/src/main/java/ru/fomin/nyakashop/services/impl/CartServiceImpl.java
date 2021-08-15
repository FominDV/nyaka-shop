package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.beans.Cart;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Product;
import ru.fomin.nyakashop.exceptions.ResourceNotFoundException;
import ru.fomin.nyakashop.mappers.MapperDto;
import ru.fomin.nyakashop.services.CartService;
import ru.fomin.nyakashop.services.ProductService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartServiceImpl implements CartService {

    final ProductService productService;
    final Cart cart;

    @Override
    public void addProduct(Long productId) {
        if (!cart.incrementProduct(productId)) {
            Product product = productService.getProductOrThrow(productId);
            ProductDto productDto = MapperDto.INSTANCE.toProductDto(product);
            cart.addProduct(productDto, product.getPrice().getId());
        }
    }

    @Override
    public void removeProduct(Long productId) {
        if (!cart.removeProduct(productId)) {
            throw new ResourceNotFoundException(Product.class);
        }
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void decrementProduct(Long productId) throws ResourceNotFoundException {
        if (!cart.decrementProduct(productId)) {
            throw new ResourceNotFoundException(Product.class);
        }
    }

    @Override
    public void clearCart() {
        cart.clearCart();
    }

}
