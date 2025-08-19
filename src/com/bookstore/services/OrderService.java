package com.bookstore.services;

import com.bookstore.models.Order;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OrderService {
    private final Queue<Order> orderQueue;
    private final List<Order> processedOrders;

    public OrderService() {
        this.orderQueue = new LinkedList<>();
        this.processedOrders = new ArrayList<>();
    }

    public void placeOrder(Order order) {
        orderQueue.add(order);
        System.out.println("Order " + order.getId() + " has been placed and is waiting for processing.");
    }

    public Order processNextOrder() {
        Order order = orderQueue.poll();
        if (order != null) {
            processedOrders.add(order);
            System.out.println("Processing order: " + order.getId());
        } else {
            System.out.println("No orders to process.");
        }
        return order;
    }

    public List<Order> getProcessedOrders() {
        return new ArrayList<>(processedOrders);
    }

    public boolean hasPendingOrders() {
        return !orderQueue.isEmpty();
    }

    public Order findOrderById(String id) {
        Order order = orderQueue.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (order != null) {
            return order;
        }
        return processedOrders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Order> getAllOrders() {
        List<Order> allOrders = new ArrayList<>(processedOrders);
        allOrders.addAll(orderQueue);
        return allOrders;
    }

    public void addProcessedOrder(Order order) {
        processedOrders.add(order);
    }

    public Queue<Order> getPendingOrders() {
        return orderQueue;
    }
}
