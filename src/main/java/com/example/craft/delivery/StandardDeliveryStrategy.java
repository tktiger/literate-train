package com.example.craft.delivery;

import com.example.craft.domain.Order;
import com.example.craft.domain.Customer;

public class StandardDeliveryStrategy implements DeliveryStrategy {
    private final static int STANDARDDELIVERYFEE = 399;
    private final static int STANDARDFREETHRESHOLD = 5000;

    public int calculateDelivery(Order order, int subtotal) {
        int deliveryFee = STANDARDDELIVERYFEE;

        if (subtotal > STANDARDFREETHRESHOLD) {
            deliveryFee = 0;
        }

        return deliveryFee;
    }
}