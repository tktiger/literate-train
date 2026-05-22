package com.example.craft.delivery;

import com.example.craft.domain.Order;

public interface DeliveryStrategy {
    int calculateDelivery(Order order, int subtotal);
}