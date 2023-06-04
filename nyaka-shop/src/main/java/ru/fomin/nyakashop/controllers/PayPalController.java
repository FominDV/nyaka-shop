//package ru.fomin.nyakashop.controllers;
//
//import com.paypal.core.PayPalHttpClient;
//import com.paypal.http.HttpResponse;
//import com.paypal.orders.Order;
//import com.paypal.orders.OrderRequest;
//import com.paypal.orders.OrdersCaptureRequest;
//import com.paypal.orders.OrdersCreateRequest;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import ru.fomin.nyakashop.enums.OrderStatus;
//import ru.fomin.nyakashop.services.OrderService;
//import ru.fomin.nyakashop.services.PayPalService;
//
//import java.io.IOException;
//
//@Controller
//@RequestMapping("/api/v1/paypal")
//@RequiredArgsConstructor
//@CrossOrigin("*")
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class PayPalController {
//
//    final OrderService orderService;
//    final PayPalService payPalService;
//    final PayPalHttpClient payPalClient;
//
//    @PostMapping("/create/{orderId}")
//    public ResponseEntity<?> createOrder(@PathVariable Long orderId) throws IOException {
//        OrdersCreateRequest request = new OrdersCreateRequest();
//        request.prefer("return=representation");
//        request.requestBody(payPalService.createOrderRequest(orderId));
//        com.paypal.http.HttpResponse<Order> response = payPalClient.execute(request);
//        return new ResponseEntity<>(response.result().id(), HttpStatus.valueOf(response.statusCode()));
//    }
//
//    @PostMapping("/capture/{payPalId}")
//    public ResponseEntity<?> captureOrder(@PathVariable String payPalId) throws IOException {
//        OrdersCaptureRequest request = new OrdersCaptureRequest(payPalId);
//        request.requestBody(new OrderRequest());
//
//        HttpResponse<Order> response = payPalClient.execute(request);
//        com.paypal.orders.Order payPalOrder = response.result();
//        if ("COMPLETED".equals(payPalOrder.status())) {
//            long orderId = Long.parseLong(payPalOrder.purchaseUnits().get(0).referenceId());
//            ru.fomin.nyakashop.entities.Order order = orderService.getOrderOrThrow(orderId);
//            order.setStatus(OrderStatus.PAID);
//            orderService.update(order);
//            return new ResponseEntity<>("Order completed!", HttpStatus.valueOf(response.statusCode()));
//        }
//        return new ResponseEntity<>(payPalOrder, HttpStatus.valueOf(response.statusCode()));
//    }
//}
