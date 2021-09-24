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

import java.util.UUID;
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
    public void addProduct(String keySuffix, Long productId) {
        execute(
                keySuffix,
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
    public void removeProduct(String keySuffix, Long productId) {
        execute(
                keySuffix,
                cart -> {
                    if (!cart.removeProduct(productId)) {
                        throw new ResourceNotFoundException(Product.class);
                    }
                }
        );
    }

    @Override
    public Cart getCart() {
        return getCartByFullKey(getFullCartKey());
    }

    @Override
    public Cart getCart(String keySuffix) {
        return getCartByFullKey(getFullCartKey(keySuffix));
    }

    @Override
    public void decrementProduct(String keySuffix, Long productId) throws ResourceNotFoundException {
        execute(
                keySuffix,
                cart -> {
                    if (!cart.decrementProduct(productId)) {
                        throw new ResourceNotFoundException(Product.class);
                    }
                }
        );
    }

    @Override
    public void clearCart(String keySuffix) {
        execute(keySuffix, Cart::clearCart);
    }

    @Override
    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void merge(String keySuffix) {
        String guestCartKey = getFullCartKey(keySuffix);
        String userCartKey = getFullCartKey();
        Cart guestCart = getCartByFullKey(guestCartKey);
        Cart userCart = getCartByFullKey(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    private String getFullCartKey(String suffix) {
        return cartPrefix + suffix;
    }

    private String getFullCartKey() {
        return cartPrefix + SecurityUtils.getEmail();
    }

    private Cart getCartByFullKey(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    private void execute(String keySuffix, Consumer<Cart> action) {
        String cartKey = getFullCartKey(keySuffix);
        Cart cart = getCartByFullKey(cartKey);
        action.accept(cart);
        updateCart(cartKey, cart);
    }

    private void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }

}
