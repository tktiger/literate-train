package com.example.craft.delivery;

import com.example.craft.domain.Order;

public class NextDayDeliveryStrategy implements DeliveryStrategy {
    private final int NEXTDAY-HIGH-DELIVERY-FEE = 799;
    private final int NEXTDAY-LOW-THRESHOLD = 15000;
    private final int NEXTDAY-LOW-DELIVERY_FEE = 499;

    public int calculateDelivery(Order order, int subtotal) {
        int deliveryFee = NEXTDAY-HIGH-DELIVERY-FEE;

        if (subtotal > NEXTDAY-LOW-THRESHOLD) {
            deliveryFee = NEXTDAY-LOW-DELIVERY-FEE;
        }

        return deliveryFee;
    }
}