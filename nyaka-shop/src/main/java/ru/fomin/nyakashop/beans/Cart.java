package ru.fomin.nyakashop.beans;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.fomin.nyakashop.dto.OrderItemDto;
import ru.fomin.nyakashop.dto.ProductDto;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Component
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {

    List<OrderItemDto> items;
    BigDecimal totalPrice;
    int totalQuantity;

    @PostConstruct
    public void init() {
        items = new ArrayList<>();
        totalPrice = BigDecimal.ZERO;
    }

    public boolean removeProduct(Long productId) {
        return processOrderItem(productId, this::remove);
    }

    public boolean decrementProduct(Long productId) {
        return processOrderItem(productId, this::decrement);
    }

    public boolean incrementProduct(Long productId) {
        return processOrderItem(productId, this::increment);
    }

    public void addProduct(ProductDto productDto, Long priceId) {
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .product(productDto)
                .priceId(priceId)
                .build();
        items.add(orderItemDto);
        increment(productDto.getPrice());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clearCart() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
        totalQuantity = 0;
    }

    private void increment(OrderItemDto orderItemDto) {
        orderItemDto.incrementQuantity();
        increment(orderItemDto.getProduct().getPrice());
    }

    private void increment(BigDecimal price) {
        totalQuantity++;
        totalPrice = totalPrice.add(price);
    }

    private void decrement(OrderItemDto item) {
        totalQuantity--;
        totalPrice = totalPrice.subtract(item.getProduct().getPrice());
        item.decrementQuantity();
        if (item.isEmpty()) {
            items.remove(item);
        }
    }

    private void remove(OrderItemDto item) {
        items.remove(item);
        int quantity = item.getQuantity();
        totalQuantity -= quantity;
        totalPrice = totalPrice.subtract(item.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity)));
    }

    private boolean processOrderItem(Long productId, Consumer<OrderItemDto> itemConsumer) {
        Optional<OrderItemDto> itemOptional = items.stream()
                .filter(orderItemDto -> orderItemDto.getProduct().getId() == productId)
                .findFirst();
        itemOptional.ifPresent(itemConsumer);
        return itemOptional.isPresent();
    }

}
