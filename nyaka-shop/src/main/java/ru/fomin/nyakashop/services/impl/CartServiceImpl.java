package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.dto.OrderItemDto;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Product;
import ru.fomin.nyakashop.exceptions.ResourceNotFoundException;
import ru.fomin.nyakashop.mappers.impl.ProductMapperCast;
import ru.fomin.nyakashop.services.CartService;
import ru.fomin.nyakashop.services.ProductService;
import ru.fomin.nyakashop.util.Cart;
import ru.fomin.nyakashop.util.SecurityUtils;

import java.util.*;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartServiceImpl implements CartService {

    @Autowired
    @Lazy
    final ProductService productService;
    @Autowired
    @Lazy
    final ProductMapperCast productMapper;
    final RedisTemplate<String, Object> redisTemplate;

    @Value("${cart.prefix}")
    String cartPrefix;

    @Override
    public void addProduct(Long productId) {
        execute(
                cart -> {
                    if (!cart.incrementProduct(productId)) {
                        Product product = productService.getProductOrThrow(productId);
                        ProductDto productDto = productMapper.convert(product);
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
        var cart = (Cart) redisTemplate.opsForValue().get(cartKey);
        for(OrderItemDto item:cart.getItems()){
            item.getProduct().setQuentity(productMapper.getQuentity(item.getProduct().getId()));
        }
        return cart;
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

    @Override
    public List<Cart> getAllCart() {
        Set<String> keys = redisTemplate.keys(cartPrefix+"*");
      List<Cart> carts = new ArrayList<>();
        for (String key : keys){
            // Get the corresponding value corresponding to the key
            Cart value =(Cart) redisTemplate.opsForValue().get(key);
            carts.add(value);
        }
        return carts;
    }

    @Override
    public Map<String, Cart> getCartMap() {
        Set<String> keys = redisTemplate.keys(cartPrefix+"*");
        Map<String, Cart> cartMap = new HashMap<>();
        for (String key : keys){
            // Get the corresponding value corresponding to the key
            Cart value =(Cart) redisTemplate.opsForValue().get(key);
            cartMap.put(key, value);
        }
        return cartMap;
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
