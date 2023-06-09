package ru.fomin.nyakashop.mappers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.dto.OrderItemDto;
import ru.fomin.nyakashop.entities.OrderItem;
import ru.fomin.nyakashop.entities.Product;
import ru.fomin.nyakashop.mappers.UniversalMapper;
import ru.fomin.nyakashop.repositories.PriceRepository;
import ru.fomin.nyakashop.repositories.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemDtoMapper {

    @Autowired
    ProductMapperCast productMapperCast;
    @Autowired
    UniversalMapper universalMapper;
    @Autowired
    PriceRepository priceRepository;
    @Autowired
    ProductRepository productRepository;

   public OrderItemDto convert(OrderItem orderItem){
        return OrderItemDto.builder()
                .product(productMapperCast.convert(orderItem.getProduct()))
                .priceId(orderItem.getProduct().getPrices().stream().filter(p->p.getCost().equals(orderItem.getCost())).findFirst().get().getId())
                .cost(orderItem.getCost())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public OrderItem convert(OrderItemDto orderItemDto){
       return OrderItem.builder()
               .product(productRepository.getById(orderItemDto.getProduct().getId()))
               .quantity(orderItemDto.getQuantity())
               .cost(priceRepository.findAllByCostAndProduct_Id(orderItemDto.getProduct().getPrice(), orderItemDto.getProduct().getId()).get(0).getCost())
               .build();
    }

    public List<OrderItem> convert(List<OrderItemDto> orderItemDtos){
       return orderItemDtos.stream().map(this::convert).collect(Collectors.toList());
    }

}
