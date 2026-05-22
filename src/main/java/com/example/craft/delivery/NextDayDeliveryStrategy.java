package com.example.craft.delivery;

import com.example.craft.domain.Order;
import com.example.craft.domain.Customer;

public class NextDayDeliveryStrategy implements DeliveryStrategy {
    private final int NEXTDAY_HIGH_DELIVERY_FEE = 799;
    private final int NEXTDAY_LOW_THRESHOLD = 15000;
    private final int NEXTDAY_LOW_DELIVERY_FEE = 499;

    public int calculateDelivery(Order order, int subtotal) {
        int deliveryFee = NEXTDAY_HIGH_DELIVERY_FEE;

        if (subtotal > NEXTDAY_LOW_THRESHOLD) {
            deliveryFee = NEXTDAY_LOW_DELIVERY_FEE;
        }

        return deliveryFee;
    }
}