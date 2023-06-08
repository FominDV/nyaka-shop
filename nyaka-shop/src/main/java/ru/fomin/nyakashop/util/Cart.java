package ru.fomin.nyakashop.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.fomin.nyakashop.dto.OrderItemDto;
import ru.fomin.nyakashop.dto.ProductDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Consumer;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {

    List<OrderItemDto> items = new ArrayList<>();
    BigDecimal totalPrice = BigDecimal.ZERO;
    int totalQuantity;
    Date deathTime;
    String deathTimeString;



    public boolean removeProduct(Long productId) {
        var result = processOrderItem(productId, this::remove);
        updateDeathTime();
        return result;
    }

    public boolean decrementProduct(Long productId) {
        var result= processOrderItem(productId, this::decrement);
        updateDeathTime();
        return result;
    }

    public boolean incrementProduct(Long productId) {
        var result= processOrderItem(productId, this::increment);
        updateDeathTime();
        return result;
    }

    public void addProduct(ProductDto productDto, Long priceId) {
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .product(productDto)
                .priceId(priceId)
                .build();
        items.add(orderItemDto);
        increment(productDto.getPrice());
        updateDeathTime();
    }

    @JsonIgnore
    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clearCart() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
        totalQuantity = 0;
        updateDeathTime();
    }

    private void increment(OrderItemDto orderItemDto) {
        orderItemDto.incrementQuantity();
        increment(orderItemDto.getProduct().getPrice());
        updateDeathTime();
    }

    private void increment(BigDecimal price) {
        totalQuantity++;
        totalPrice = totalPrice.add(price);
        updateDeathTime();
    }

    private void decrement(OrderItemDto item) {
        totalQuantity--;
        totalPrice = totalPrice.subtract(item.getProduct().getPrice());
        item.decrementQuantity();
        if (item.isEmpty()) {
            items.remove(item);
        }
        updateDeathTime();
    }

    private void remove(OrderItemDto item) {
        items.remove(item);
        int quantity = item.getQuantity();
        totalQuantity -= quantity;
        totalPrice = totalPrice.subtract(item.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity)));
        updateDeathTime();

    }

    private boolean processOrderItem(Long productId, Consumer<OrderItemDto> itemConsumer) {
        Optional<OrderItemDto> itemOptional = items.stream()
                .filter(orderItemDto -> Objects.equals(orderItemDto.getProduct().getId(), productId))
                .findFirst();
        itemOptional.ifPresent(itemConsumer);
        var result= itemOptional.isPresent();
        updateDeathTime();
        return result;
    }

    private void updateDeathTime(){
        if(isZeroQuantity()){
            deathTime=null;
            deathTimeString=null;
            return;
        }
        if(deathTime==null){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm");
            var time =  LocalDateTime.now()
                    .plusDays(1).truncatedTo(ChronoUnit.HOURS);
            deathTimeString = dtf.format(time);
            deathTime = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        }
    }

    private boolean isZeroQuantity(){
        return !items.stream().anyMatch(i->i.getQuantity()>0);
    }

}
