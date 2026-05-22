package com.example.craft.payment;

public class PaymentStrategyFactory {
    public static PaymentStrategy create(String paymentType) {
        if (paymentType.equals("CARD")) {
            return new CardPaymentStrategy();
        }
        if (paymentType.equals("PAYPAL")) {
            return new PaypalPaymentStrategy();
        }
        if (paymentType.equals("BANK_TRANSFER")) {
            return new BankTransferPaymentStrategy();
        }
        throw new IllegalArgumentException("Unknown payment type: " + paymentType);
    }
}