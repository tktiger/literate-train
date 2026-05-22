package com.example.craft.delivery;

import com.example.craft.domain.Order;
import com.example.craft.domain.Customer;

public class StandardDeliveryStrategy implements DeliveryStrategy {
    private final static int STANDARD-DELIVERY-FEE = 399;
    private final static int STANDARD-FREE-THRESHOLD = 5000;

    public int calculateDelivery(Order order, int subtotal) {
        int deliveryFee = STANDARD-DELIVERY-FEE;

        if (subtotal > STANDARD-FREE-THRESHOLD) {
            deliveryFee = 0;
        }

        return deliveryFee;
    }
}