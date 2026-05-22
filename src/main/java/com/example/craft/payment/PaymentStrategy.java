package com.example.craft.payment;

public interface PaymentStrategy {
    void displayPaymentNotification(int total, String email);
}