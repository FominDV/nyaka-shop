package ru.fomin.nyakashop.services;

import com.paypal.orders.OrderRequest;

public interface PayPalService {

    OrderRequest createOrderRequest(Long orderId);

}
