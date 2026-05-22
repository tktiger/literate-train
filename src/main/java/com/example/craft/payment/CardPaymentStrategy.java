package com.example.craft.payment;

public class CardPaymentStrategy implements PaymentStrategy {
    private final int TOTAL_THRESHOLD = 100000;

    public void displayPaymentNotification(int total, String email) {
        System.out.println("Taking card payment for £" + formatPounds(total));

        if (total > TOTAL_THRESHOLD) {
            System.out.println("Large card payment requires manual review");
        }
    }
    
    private String formatPounds(int pence) {
        return String.format("%.2f", pence / 100.0);
    }
}