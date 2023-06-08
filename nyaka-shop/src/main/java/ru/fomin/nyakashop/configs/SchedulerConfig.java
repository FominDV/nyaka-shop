package ru.fomin.nyakashop.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.fomin.nyakashop.services.CartService;
import ru.fomin.nyakashop.util.Cart;

import java.util.Date;
import java.util.Map;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    CartService cartService;
    @Autowired
    RedisTemplate redisTemplate;

    @Scheduled(fixedDelay = 1800000)
    void clearCart() {
        var carts = cartService.getCartMap();
        var timeNow = new Date();
        for (Map.Entry<String, Cart> entry : carts.entrySet()) {
            var cart = entry.getValue();
            if (!cart.isEmpty() && (cart.getDeathTime().equals(timeNow) || cart.getDeathTime().before(timeNow))) {
                cart.clearCart();
                redisTemplate.opsForValue().set(entry.getKey(), cart);
            }
        }
    }

}
