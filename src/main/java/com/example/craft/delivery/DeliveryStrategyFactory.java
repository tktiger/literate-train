package com.example.craft.delivery;

public class DeliveryStrategyFactory {
    public static DeliveryStrategy create(String deliveryType) {
        if (deliveryType.equals("STANDARD")) {
            return new StandardDeliveryStrategy();
        }
        if (deliveryType.equals("NEXTDAY")) {
            return new NextDayDeliveryStrategy();
        }
        if (deliveryType.equals("COLLECTION")) {
            return new CollectionDeliveryStrategy();
        }
        throw new IllegalArgumentException("Unknown delivery type: " + deliveryType);
    }
}