package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Product;
import ru.fomin.nyakashop.exceptions.ResourceNotFoundException;
import ru.fomin.nyakashop.services.CartService;
import ru.fomin.nyakashop.services.ProductService;
import ru.fomin.nyakashop.util.Cart;
import ru.fomin.nyakashop.util.SecurityUtils;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartServiceImpl implements CartService {

    final ProductService productService;
    final ConversionService conversionService;
    final RedisTemplate<String, Object> redisTemplate;

    @Value("${cart.prefix}")
    String cartPrefix;

    @Override
    public void addProduct(Long productId) {
        execute(
                cart -> {
                    if (!cart.incrementProduct(productId)) {
                        Product product = productService.getProductOrThrow(productId);
                        ProductDto productDto = conversionService.convert(product, ProductDto.class);
                        cart.addProduct(productDto, product.getPrice().getId());
                    }
                }
        );
    }

    @Override
    public void removeProduct(Long productId) {
        execute(
                cart -> {
                    if (!cart.removeProduct(productId)) {
                        throw new ResourceNotFoundException(Product.class);
                    }
                }
        );
    }

    @Override
    public Cart getCart() {
        String cartKey = getCartKey();
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    @Override
    public void decrementProduct(Long productId) throws ResourceNotFoundException {
        execute(
                cart -> {
                    if (!cart.decrementProduct(productId)) {
                        throw new ResourceNotFoundException(Product.class);
                    }
                }
        );
    }

    @Override
    public void clearCart() {
        execute( Cart::clearCart);
    }

    private String getCartKey() {
        return cartPrefix + SecurityUtils.getEmail();
    }

    private void execute( Consumer<Cart> action) {
        Cart cart = getCart();
        action.accept(cart);
        redisTemplate.opsForValue().set(getCartKey(), cart);
    }

}
