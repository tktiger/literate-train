package com.example.craft.delivery;

import com.example.craft.domain.Order;

public class NextDayDeliveryStrategy implements DeliveryStrategy {
    private final int NEXTDAYHIGHDELIVERYFEE = 799;
    private final int NEXTDAYLOWTHRESHOLD = 15000;
    private final int NEXTDAYLOWDELIVERYFEE = 499;

    public int calculateDelivery(Order order, int subtotal) {
        int deliveryFee = NEXTDAYHIGHDELIVERYFEE;

        if (subtotal > NEXTDAYLOWTHRESHOLD) {
            deliveryFee = NEXTDAYLOWDELIVERYFEE;
        }

        return deliveryFee;
    }
}