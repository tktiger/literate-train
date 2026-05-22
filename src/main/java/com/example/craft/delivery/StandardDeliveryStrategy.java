package com.example.craft.delivery;

import com.example.craft.domain.Order;
import com.example.craft.domain.Customer;

public class StandardDeliveryStrategy implements DeliveryStrategy {
    private final int STANDARD_DELIVERY_FEE = 399;
    private final int STANDARD_FREE_THRESHOLD = 5000;

    public int calculateDelivery(Order order, int subtotal) {
        int deliveryFee = STANDARD_DELIVERY_FEE;

        if (subtotal > STANDARD_FREE_THRESHOLD) {
            deliveryFee = 0;
        }

        return deliveryFee;
    }
}