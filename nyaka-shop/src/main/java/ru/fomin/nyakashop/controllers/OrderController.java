package ru.fomin.nyakashop.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.OrderDto;
import ru.fomin.nyakashop.services.OrderService;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class OrderController {

    final OrderService orderService;
    final ConversionService conversionService;

    @PostMapping
    public void createOrder(@RequestParam @NotBlank String address, @RequestParam @Length(max = 10, min = 1) @NotBlank String phone) {
        orderService.createOrder(address, phone);
    }

    @GetMapping
    public Page<OrderDto> getAllOrders(@RequestParam(name = "page", defaultValue = "1") Integer pageIndex) {
        return orderService.findAllByCurrentUser(--pageIndex)
                .map(order -> conversionService.convert(order, OrderDto.class));
    }

}
