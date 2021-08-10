package ru.fomin.nyakashop.mappers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.fomin.nyakashop.dto.OrderItem;
import ru.fomin.nyakashop.entities.OrderItemEn;
import ru.fomin.nyakashop.entities.ProductEn;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemMapper {

    @Resource
    ProductMapper productMapper;

    public List<OrderItem> convertToOrderItemList(List<OrderItemEn> orderItemList) {
        return orderItemList.stream()
                .map(this::convertToOrderItem)
                .collect(Collectors.toList());
    }

    public OrderItem convertToOrderItem(OrderItemEn orderItemEn) {
        return OrderItem.builder()
                .product(productMapper.convertToProduct(orderItemEn.getProduct()))
                .priceId(orderItemEn.getPrice().getId())
                .quantity(orderItemEn.getQuantity())
                .build();
    }

    public OrderItemEn convertToOrderItemEn(OrderItem orderItem) {
        ProductEn productEn = new ProductEn();
        productEn.setId(orderItem.getProduct().getId());
        return new OrderItemEn(productEn, orderItem.getQuantity());
    }

    public List<OrderItemEn> convertToOrderItemEnList(List<OrderItem> orderItemList) {
        return orderItemList.stream()
                .map(this::convertToOrderItemEn)
                .collect(Collectors.toList());
    }

}
