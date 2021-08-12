package ru.fomin.nyakashop.mappers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.fomin.nyakashop.dto.OrderItemDto;
import ru.fomin.nyakashop.entities.OrderItemEn;
import ru.fomin.nyakashop.entities.Product;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemMapper {

    @Resource
    ProductMapper productMapper;

    public List<OrderItemDto> convertToOrderItemList(List<OrderItemEn> orderItemList) {
        return orderItemList.stream()
                .map(this::convertToOrderItem)
                .collect(Collectors.toList());
    }

    public OrderItemDto convertToOrderItem(OrderItemEn orderItemEn) {
        return OrderItemDto.builder()
                .productDto(productMapper.convertToProduct(orderItemEn.getProduct()))
                .priceId(orderItemEn.getPrice().getId())
                .quantity(orderItemEn.getQuantity())
                .build();
    }

    public OrderItemEn convertToOrderItemEn(OrderItemDto orderItemDto) {
        Product productEn = new Product();
        productEn.setId(orderItemDto.getProductDto().getId());
        return new OrderItemEn(productEn, orderItemDto.getQuantity());
    }

    public List<OrderItemEn> convertToOrderItemEnList(List<OrderItemDto> orderItemDtoList) {
        return orderItemDtoList.stream()
                .map(this::convertToOrderItemEn)
                .collect(Collectors.toList());
    }

}
