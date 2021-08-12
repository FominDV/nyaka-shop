package ru.fomin.nyakashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.OrderDto;
import ru.fomin.nyakashop.services.OrderService;
import ru.fomin.nyakashop.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    public void createOrder(Principal principal, @RequestParam String address, @RequestParam String phone) {
//        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("Unable to create order. User not found"));;
//        orderService.createOrder(user, address, phone);
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getOrderList();
    }
}
