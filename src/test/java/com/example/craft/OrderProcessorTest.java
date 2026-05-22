package com.example.craft;

import com.example.craft.domain.Customer;
import com.example.craft.domain.CustomerType;
import com.example.craft.domain.Order;
import com.example.craft.domain.OrderItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessorTest {

    private final OrderProcessor processor = new OrderProcessor();

    @Test
    void student_customer_gets_discount_and_receipt_contains_total() {
        Customer customer = new Customer("Ada", "ada@example.com", "07123456789", CustomerType.STUDENT);
        Order order = new Order("ORD-1", customer, "STANDARD", "PAYPAL");
        order.addItem(new OrderItem("Book", 1, 1000));

        String receipt = processor.process(order);

        assertTrue(receipt.contains("Order: ORD-1"));
        assertTrue(receipt.contains("Discount: £1.50"));
        assertTrue(receipt.contains("Delivery: £3.99"));
        assertTrue(receipt.contains("Total: £12.49"));
    }

    @Test
    void staff_customer_gets_discount_and_receipt_contains_total() {
        Customer customer = new Customer("Tim", "tim.kay@email.com", "07777777777", CustomerType.STAFF);
        Order order = new Order("ORD-2", customer, "STANDARD", "CARD");
        order.addItem(new OrderItem("Book", 1, 1000));

        String receipt = processor.process(order);

        assertTrue(receipt.contains("Order: ORD-2"));
        assertTrue(receipt.contains("Discount: £2.00"));
        assertTrue(receipt.contains("Delivery: £3.99"));
        assertTrue(receipt.contains("Total: £11.99"));
    }

    @Test
    void delivery_free_when_subtotal_over_5000() {
        Customer customer = new Customer("Same", "sam.kay@email.com", "07777777777", CustomerType.STUDENT);
        Order order = new Order("ORD-3", customer, "STANDARD", "CARD");
        order.addItem(new OrderItem("Book", 10, 1000));

        String receipt = processor.process(order);

        assertTrue(receipt.contains("Order: ORD-3"));
        assertTrue(receipt.contains("Delivery: £0.00"));
    }
    
    @Test
    void throw_exception_on_illegal_delivery_type() {
        Customer customer = new Customer("Simon", "simon.kay@email.com", "07777777777", CustomerType.PREMIUM);
        Order order = new Order("ORD-4", customer, "ENHANCED", "CARD");
        order.addItem(new OrderItem("Book", 1, 1000));

        assertThrows(IllegalArgumentException.class, () -> {
            processor.process(order);
        });
    }

    @Test
    void throw_exception_on_empty_order() {

        assertThrows(IllegalArgumentException.class, () -> {
            processor.process(null);
        });
    }

    @Test
    void delivery_type_collection_has_no_delivery_fee() {
        Customer customer = new Customer("Simon", "simon.kay@email.com", "07777777777", CustomerType.PREMIUM);
        Order order = new Order("ORD-5", customer, "COLLECTION", "CARD");
        order.addItem(new OrderItem("Book", 1, 1000));

        String receipt = processor.process(order);

        assertTrue(receipt.contains("Order: ORD-5"));
        assertTrue(receipt.contains("Discount: £1.00"));
        assertTrue(receipt.contains("Delivery: £0.00"));
        assertTrue(receipt.contains("Total: £9.00"));
    }

    @Test
    void premium_customer_gets_larger_discount_on_five_items_or_more() {
        Customer customer = new Customer("Simon", "simon.kay@email.com", "07777777777", CustomerType.PREMIUM);
        Order order = new Order("ORD-6", customer, "COLLECTION", "CARD");
        order.addItem(new OrderItem("Book", 10, 1000));

        String receipt = processor.process(order);

        assertTrue(receipt.contains("Order: ORD-6"));
        assertTrue(receipt.contains("Discount: £13.00"));
        assertTrue(receipt.contains("Delivery: £0.00"));
        assertTrue(receipt.contains("Total: £87.00"));
    }

}
