//package ru.fomin.nyakashop.mappers;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.springframework.core.convert.converter.Converter;
//import ru.fomin.nyakashop.dto.OrderDto;
//import ru.fomin.nyakashop.entities.Order;
//
//@Mapper(componentModel = "spring", uses = OrderItemDtoMapper.class)
//public interface OrderDtoMapper extends Converter<Order, OrderDto> {
//
//    @Override
//    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "dd.MM.yyyy HH.mm")
//    OrderDto convert(Order order);
//
//}
