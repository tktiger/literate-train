package com.example.craft;

import com.example.craft.domain.Customer;
import com.example.craft.domain.CustomerType;
import com.example.craft.domain.Order;
import com.example.craft.domain.OrderItem;
import com.example.craft.delivery.*;
import com.example.craft.payment.*;

public class OrderProcessor {

    private record BasketValues(int orderTotal, int itemCount) {}

    private void orderValidation(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order must not be null");
        }

        if (order.getCustomer() == null) {
            throw new IllegalArgumentException("Customer must not be null");
        }

        if (order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        Customer customer = order.getCustomer();

        if (customer.getName() == null || customer.getName().trim().length() == 0) {
            throw new IllegalArgumentException("Customer name is invalid");
        }

        if (customer.getEmail() == null || !customer.getEmail().contains("@")) {
            throw new IllegalArgumentException("Customer email is invalid");
        }

        if (customer.getType() == null) {
            throw new IllegalArgumentException("Customer type is required");
        }
    }

    private BasketValues validateItemsAndTotals(Order order) {
        int subtotal = 0;
        int itemCount = 0;

        for (OrderItem item : order.getItems()) {
            if (item == null) {
                throw new IllegalArgumentException("Order item must not be null");
            }

            if (item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Item quantity must be positive");
            }

            if (item.getUnitPricePence() <= 0) {
                throw new IllegalArgumentException("Item price must be positive");
            }

            subtotal = subtotal + item.getQuantity() * item.getUnitPricePence();
            itemCount = itemCount + item.getQuantity();
        }

        return new BasketValues(subtotal, itemCount);
        
    }

    private int calculateDiscount(Customer customer, int subtotal, int itemCount) {
        int discount = 0;

        if (customer.getType() == CustomerType.STUDENT) {
            discount = (int) (subtotal * 0.15);

            if (subtotal > 10000) {
                discount = discount + 250;
            }

            System.out.println("Student discount applied");
        } else if (customer.getType() == CustomerType.PREMIUM) {
            discount = (int) (subtotal * 0.10);

            if (itemCount > 5) {
                discount = discount + 300;
            }

            System.out.println("Premium discount applied");
        } else if (customer.getType() == CustomerType.STAFF) {
            discount = (int) (subtotal * 0.20);

            if (subtotal > 20000) {
                discount = discount + 500;
            }

            System.out.println("Staff discount applied");
        } else {
            discount = 0;
            System.out.println("No discount applied");
        }

        if (discount > subtotal) {
            discount = subtotal;
        }

        return discount;
    }

    private int calculateDelivery(Order order, Customer customer, int subtotal) {

        int deliveryFee = 0;
        
        DeliveryStrategy newDel = DeliveryStrategyFactory.create(order.getDeliveryType());
        deliveryFee = newDel.calculateDelivery(order, subtotal);
        System.out.println("Delivery Strategy Cost = " + deliveryFee);

        return deliveryFee;
    }

    private int calculateTotal(Order order, Customer customer, int subtotal, int discount, int deliveryFee) {
        int total = subtotal - discount + deliveryFee;

        if (total <= 0) {
            throw new IllegalStateException("Order total must be positive");
        }

        PaymentStrategy newPay = PaymentStrategyFactory.create(order.getPaymentType());
        newPay.displayPaymentNotification(total, customer.getEmail());

        return total;
    }

    private String generateReceipt(Order order, Customer customer, int total, int subtotal, int discount, int deliveryFee) {
        System.out.println("Saving order " + order.getOrderId());
        System.out.println("Saving order " + order.getOrderId() + " for customer " + customer.getName());

        System.out.println("Sending email to " + customer.getEmail());
        System.out.println("Dear " + customer.getName() + ", your order has been processed.");
        System.out.println("Order " + order.getOrderId() + " total was £" + formatPounds(total));

        if (customer.getPhoneNumber() != null && customer.getPhoneNumber().startsWith("07")) {
            System.out.println("Sending SMS to " + customer.getPhoneNumber());
            System.out.println("Order " + order.getOrderId() + " confirmed by SMS");
        }

        if (customer.getType() == CustomerType.PREMIUM && total > 5000) {
            System.out.println("Sending premium customer follow-up email");
        }

        return "Receipt\n"
                + "-------\n"
                + "Order: " + order.getOrderId() + "\n"
                + "Customer: " + customer.getName() + "\n"
                + "Subtotal: £" + formatPounds(subtotal) + "\n"
                + "Discount: £" + formatPounds(discount) + "\n"
                + "Delivery: £" + formatPounds(deliveryFee) + "\n"
                + "Total: £" + formatPounds(total) + "\n";
    }

    public String process(Order order) {
        
        //Validate the order:
        orderValidation(order);

        //Validate order items and calculate the order total and item count
        BasketValues basket = validateItemsAndTotals(order);
        int subtotal = basket.orderTotal;
        
        int discount = calculateDiscount(order.getCustomer() , basket.orderTotal, basket.itemCount);
        int deliveryFee = calculateDelivery(order, order.getCustomer(), basket.orderTotal);

        int total = calculateTotal(order, order.getCustomer(), basket.orderTotal, discount, deliveryFee);
        
        String receipt = generateReceipt(order, order.getCustomer(), total, subtotal, discount, deliveryFee);
        System.out.println(receipt);
        return receipt;

        
    }

    private String formatPounds(int pence) {
        return String.format("%.2f", pence / 100.0);
    }
}
