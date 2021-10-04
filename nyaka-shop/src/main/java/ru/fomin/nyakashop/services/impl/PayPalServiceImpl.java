package ru.fomin.nyakashop.services.impl;

import com.paypal.orders.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fomin.nyakashop.exceptions.ResourceNotFoundException;
import ru.fomin.nyakashop.services.OrderService;
import ru.fomin.nyakashop.services.PayPalService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayPalServiceImpl implements PayPalService {

    final OrderService orderService;

    @Transactional
    public OrderRequest createOrderRequest(Long orderId) {
//        ru.fomin.nyakashop.entities.Order order = orderService.getOrderOrThrow(orderId);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("Summer Market")
                .landingPage("BILLING")
                .shippingPreference("SET_PROVIDED_ADDRESS");
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .referenceId(orderId.toString())
                .description("Summer Order")
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value("1000")
                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("USD").value("1000"))))
                .items(List.of(new Item()
                        .name("Prod")
                        .unitAmount(new Money().currencyCode("USD").value("100"))
                        .quantity("10")
                ))
                .shippingDetail(new ShippingDetail().name(new Name().fullName("John Doe"))
                        .addressPortable(new AddressPortable().addressLine1("123 Townsend St").addressLine2("Floor 6")
                                .adminArea2("San Francisco").adminArea1("AC").postalCode("94107").countryCode("US")));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }

//    @Transactional
//    public OrderRequest createOrderRequest(Long orderId) {
//        ru.fomin.nyakashop.entities.Order order = orderService.getOrderOrThrow(orderId);
//
//        OrderRequest orderRequest = new OrderRequest();
//        orderRequest.checkoutPaymentIntent("CAPTURE");
//
//        ApplicationContext applicationContext = new ApplicationContext()
//                .brandName("Summer Market")
//                .landingPage("BILLING")
//                .shippingPreference("SET_PROVIDED_ADDRESS");
//        orderRequest.applicationContext(applicationContext);
//
//        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
//        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
//                .referenceId(orderId.toString())
//                .description("Summer Order")
//                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("UK").value(order.getTotalPrice().toString())
//                        .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("UK").value(order.getTotalPrice().toString()))))
//                .items(order.getItems().stream()
//                        .map(orderItem -> new Item()
//                                .name(orderItem.getProduct().getTitle())
//                                .unitAmount(new Money().currencyCode("UK").value(orderItem.getPrice().toString()))
//                                .quantity(String.valueOf(orderItem.getQuantity())))
//                        .collect(Collectors.toList()))
//                .shippingDetail(new ShippingDetail().name(new Name().fullName(order.getUser().getFirstName()))
//                        .addressPortable(new AddressPortable().addressLine1("123 Townsend St").addressLine2("Floor 6")
//                                .adminArea2("San Francisco").adminArea1("CA").postalCode("94107").countryCode("UK")));
//        purchaseUnitRequests.add(purchaseUnitRequest);
//        orderRequest.purchaseUnits(purchaseUnitRequests);
//        return orderRequest;
//    }

}
