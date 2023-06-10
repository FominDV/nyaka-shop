package ru.fomin.nyakashop.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Role;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.OrderDto;
import ru.fomin.nyakashop.dto.StatusDto;
import ru.fomin.nyakashop.enums.OrderStatus;
import ru.fomin.nyakashop.mappers.UniversalMapper;
import ru.fomin.nyakashop.mappers.impl.OrderDtoMapper;
import ru.fomin.nyakashop.repositories.OrderRepository;
import ru.fomin.nyakashop.services.CartService;
import ru.fomin.nyakashop.services.OrderService;

import javax.validation.constraints.NotBlank;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.fomin.nyakashop.enums.OrderStatus.*;

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
    final OrderRepository orderRepository;

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

    @GetMapping("/filter")
    @PreAuthorize("hasRole('MODERATOR')")
    public Page<OrderDto> getAllOrders(@RequestParam(name = "page", defaultValue = "1") Integer pageIndex, @RequestParam(name = "status", required = false) String status) {
        List<OrderStatus> statuses = status==null? Arrays.stream(OrderStatus.values()).collect(Collectors.toList()) : List.of(OrderStatus.valueOf(status));
        return orderService.findAllByStatus(--pageIndex, statuses)
                .map(order -> orderDtoMapper.convert(order));
    }

    @PutMapping
    @Transactional
    @PreAuthorize("hasRole('MODERATOR')")
    public StatusDto changeStatus( @RequestParam(name = "orderId") Long orderId){
      var order =  orderRepository.findById(orderId).get();
      OrderStatus newStatus;
         switch (order.getStatus()){
             case NEW: newStatus= COLLECTING;
             break;
             case COLLECTING :newStatus= DELIVERING;
                 break;
             case DELIVERING : newStatus= OrderStatus.FINISHED;
                 break;
             default : throw new IllegalStateException("Unexpected value: " + order.getStatus());
        };
        order.setStatus(newStatus);
        orderRepository.save(order);
        return StatusDto.builder()
                .statusName(newStatus.name())
                .DsName(newStatus.getName())
                .build();
    }

    @DeleteMapping
    @Transactional
    @PreAuthorize("hasRole('MODERATOR')")
    public StatusDto cancelOrder( @RequestParam(name = "orderId") Long orderId){
        var order =  orderRepository.findById(orderId).get();
        if(order.getStatus()== CANCELED||order.getStatus()== FINISHED) throw new IllegalStateException("Unexpected value: " + order.getStatus());
        order.setStatus(CANCELED);
        orderRepository.save(order);
        return StatusDto.builder()
                .statusName(CANCELED.name())
                .DsName(CANCELED.getName())
                .build();
    }

}
