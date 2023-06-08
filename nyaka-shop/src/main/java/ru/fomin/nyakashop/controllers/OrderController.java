package ru.fomin.nyakashop.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.OrderDto;
import ru.fomin.nyakashop.mappers.UniversalMapper;
import ru.fomin.nyakashop.mappers.impl.OrderDtoMapper;
import ru.fomin.nyakashop.services.CartService;
import ru.fomin.nyakashop.services.OrderService;

import javax.validation.constraints.NotBlank;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@Api(value = "order resource")
public class OrderController {

    final OrderService orderService;
    final OrderDtoMapper orderDtoMapper;
    final CartService cartService;

    @PostMapping
    @ApiOperation(value = "create new order for current user", response = void.class)
    public Boolean createOrder(@RequestParam @NotBlank String address, @RequestParam @Length(max = 10, min = 1) @NotBlank String phone) {
        if(cartService.getCart().isEmpty()) return false;
        orderService.createOrder(address, phone);
        return true;
    }

    @GetMapping
    @ApiOperation(value = "returns all orders of current user", response = Page.class)
    public Page<OrderDto> getAllOrders(@RequestParam(name = "page", defaultValue = "1") Integer pageIndex) {
        return orderService.findAllByCurrentUser(--pageIndex)
                .map(order -> orderDtoMapper.convert(order));
    }

}
