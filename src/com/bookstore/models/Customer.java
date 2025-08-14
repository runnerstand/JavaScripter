package com.bookstore.models;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String id;
    private final String name;
    private final List<Order> orderHistory;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
        this.orderHistory = new ArrayList<>();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void addOrder(Order order) {
        this.orderHistory.add(order);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}