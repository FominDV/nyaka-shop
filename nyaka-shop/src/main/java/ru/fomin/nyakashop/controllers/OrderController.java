package ru.fomin.nyakashop.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.OrderDto;
import ru.fomin.nyakashop.services.OrderService;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class OrderController {

    final OrderService orderService;

    @PostMapping
    public void createOrder(@RequestParam @NotBlank String address, @RequestParam @NotBlank String phone) {
        orderService.createOrder(address, phone);
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
       // return orderService.findAll().stream().map(OrderDto::new).collect(Collectors.toList());
        return null;
    }

}
