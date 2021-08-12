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

    List<OrderItemDto> orderItemDtoList;
    BigDecimal totalPrice;
    int totalQuantity;
    Consumer<OrderItemDto> incrementProductConsumer;


    @PostConstruct
    public void init() {
        orderItemDtoList = new ArrayList<>();
        incrementProductConsumer = orderItem -> {
            orderItem.incrementQuantity();
        };
    }

    public void addProduct(OrderItemDto addingOrderItemDto) {
        getOrderItem(addingOrderItemDto)
                .ifPresentOrElse(incrementProductConsumer, getAddableNewProduct(addingOrderItemDto));
        refreshPriceAndQuantityAfterAdding(addingOrderItemDto.getProductDto());
    }

    public void removeProduct(OrderItemDto removingOrderItemDto) {
        OrderItemDto currentOrderItemDto = getOrderItem(removingOrderItemDto)
                .orElseThrow(() -> new RuntimeException("order item was not found into the cart"));
        currentOrderItemDto.decrementQuantity();
        if (currentOrderItemDto.getQuantity() < 1) {
            orderItemDtoList.remove(currentOrderItemDto);
        }
        refreshPriceAndQuantityAfterRemoving(removingOrderItemDto.getProductDto());
    }

    public void clearCart() {
        orderItemDtoList.clear();
        totalPrice = BigDecimal.ZERO;
        totalQuantity = 0;
    }

    public boolean isEmpty(){
        return orderItemDtoList.isEmpty();
    }

    private Optional<OrderItemDto> getOrderItem(OrderItemDto orderItemDto) {
        return getOrderItem(orderItemDto.getPriceId());
    }

    private Optional<OrderItemDto> getOrderItem(Long productPriceId) {
        return orderItemDtoList.stream()
                .filter(orderItem -> orderItem.getPriceId().equals(productPriceId))
                .findFirst();
    }

    private Runnable getAddableNewProduct(OrderItemDto orderItemDto) {
        return () -> {
            orderItemDto.incrementQuantity();
            orderItemDtoList.add(orderItemDto);
        };
    }

    private void refreshPriceAndQuantityAfterAdding(ProductDto newProductDto) {
        totalQuantity++;
        totalPrice =totalPrice.add( newProductDto.getPrice());
    }

    private void refreshPriceAndQuantityAfterRemoving(ProductDto removingProductDto) {
        totalQuantity--;
        totalPrice =totalPrice.subtract(removingProductDto.getPrice());
    }

}
