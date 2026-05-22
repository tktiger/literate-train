package com.example.craft.delivery;

import com.example.craft.domain.Order;
import com.example.craft.domain.Customer;

public class CollectionDeliveryStrategy implements DeliveryStrategy {
    public int calculateDelivery(Order order, int subtotal) {
        int deliveryFee = 0;

        if (order.getCustomer().getPhoneNumber() == null) {
            System.out.println("Collection selected but no phone number was provided");
        }

        return deliveryFee;
    }
}