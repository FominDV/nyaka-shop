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
import java.util.function.Consumer;

@Component
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {

    List<OrderItemDto> orderItemDtoList;
    BigDecimal totalPrice;
    int totalQuantity;

    @PostConstruct
    public void init() {
        orderItemDtoList = new ArrayList<>();
        totalPrice = BigDecimal.ZERO;
    }

    public boolean decrementProduct(Long productId) {
        return processOrderItem(productId, this::decrement);
    }

    public boolean incrementProduct(Long productId) {
        return processOrderItem(productId, this::increment);
    }

    public void addProduct(ProductDto productDto, Long priceId) {
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .productDto(productDto)
                .priceId(priceId)
                .build();
        orderItemDtoList.add(orderItemDto);
        increment(productDto.getPrice());
    }

    public boolean isEmpty() {
        return orderItemDtoList.isEmpty();
    }

    public void clearCart() {
        orderItemDtoList.clear();
        totalPrice = BigDecimal.ZERO;
        totalQuantity = 0;
    }

    private void increment(OrderItemDto orderItemDto) {
        orderItemDto.incrementQuantity();
        increment(orderItemDto.getProductDto().getPrice());
    }

    private void increment(BigDecimal price) {
        totalQuantity++;
        totalPrice = totalPrice.add(price);
    }

    private void decrement(OrderItemDto orderItemDto) {
        totalQuantity--;
        totalPrice = totalPrice.subtract(orderItemDto.getProductDto().getPrice());
        if (orderItemDto.isEmpty()) {
            orderItemDtoList.remove(orderItemDto);
        }
    }

    private boolean processOrderItem(Long productId, Consumer<OrderItemDto> itemConsumer) {
        return orderItemDtoList.stream()
                .filter(orderItemDto -> orderItemDto.getProductDto().getId() == productId)
                .peek(itemConsumer)
                .findFirst()
                .isPresent();
    }

}
