package ru.fomin.nyakashop.beans;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.fomin.nyakashop.dto.OrderItem;
import ru.fomin.nyakashop.dto.Product;


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

    List<OrderItem> orderItemList;
    BigDecimal totalPrice;
    int totalQuantity;
    Consumer<OrderItem> incrementProductConsumer;


    @PostConstruct
    public void init() {
        orderItemList = new ArrayList<>();
        incrementProductConsumer = orderItem -> {
            orderItem.incrementQuantity();
        };
    }

    public void addProduct(OrderItem addingOrderItem) {
        getOrderItem(addingOrderItem)
                .ifPresentOrElse(incrementProductConsumer, getAddableNewProduct(addingOrderItem));
        refreshPriceAndQuantityAfterAdding(addingOrderItem.getProduct());
    }

    public void removeProduct(OrderItem removingOrderItem) {
        OrderItem currentOrderItem = getOrderItem(removingOrderItem)
                .orElseThrow(() -> new RuntimeException("order item was not found into the cart"));
        currentOrderItem.decrementQuantity();
        if (currentOrderItem.getQuantity() < 1) {
            orderItemList.remove(currentOrderItem);
        }
        refreshPriceAndQuantityAfterRemoving(removingOrderItem.getProduct());
    }

    public void clearCart() {
        orderItemList.clear();
        totalPrice = BigDecimal.ZERO;
        totalQuantity = 0;
    }

    public boolean isEmpty(){
        return orderItemList.isEmpty();
    }

    private Optional<OrderItem> getOrderItem(OrderItem orderItem) {
        return getOrderItem(orderItem.getPriceId());
    }

    private Optional<OrderItem> getOrderItem(Long productPriceId) {
        return orderItemList.stream()
                .filter(orderItem -> orderItem.getPriceId().equals(productPriceId))
                .findFirst();
    }

    private Runnable getAddableNewProduct(OrderItem orderItem) {
        return () -> {
            orderItem.incrementQuantity();
            orderItemList.add(orderItem);
        };
    }

    private void refreshPriceAndQuantityAfterAdding(Product newProduct) {
        totalQuantity++;
        totalPrice =totalPrice.add( newProduct.getPrice());
    }

    private void refreshPriceAndQuantityAfterRemoving(Product removingProduct) {
        totalQuantity--;
        totalPrice =totalPrice.subtract(removingProduct.getPrice());
    }

}
