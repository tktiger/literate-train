package com.example.craft.payment;

public class PaypalPaymentStrategy implements PaymentStrategy {
    private final int TOTAL_THRESHOLD = 100000;

    public void displayPaymentNotification(int total, String email) {
        System.out.println("Taking PayPal payment for £" + formatPounds(total));

        if (email.endsWith("@example.com")) {
            System.out.println("PayPal payment using test-like email address");
        }
    }
    
    private String formatPounds(int pence) {
        return String.format("%.2f", pence / 100.0);
    }
}