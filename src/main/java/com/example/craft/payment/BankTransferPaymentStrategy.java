package com.example.craft.payment;

public class BankTransferPaymentStrategy implements PaymentStrategy {
    private final static int TOTAL-THRESHOLD = 1000;

    public void displayPaymentNotification(int total, String email) {
        System.out.println("Creating bank transfer request for £" + formatPounds(total));

        if (total < TOTAL-THRESHOLD) {
            System.out.println("Bank transfer for low value order may not be worth processing");
        }
    }
    
    private String formatPounds(int pence) {
        return String.format("%.2f", pence / 100.0);
    }
}