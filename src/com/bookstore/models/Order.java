package com.bookstore.models;

import java.util.List;

public class Order {
    private String id;
    private String customerId;
    private List<Book> books;
    private double totalPrice;

    public Order(String id, String customerId, List<Book> books) {
        this.id = id;
        this.customerId = customerId;
        this.books = books;
        this.totalPrice = calculateTotalPrice();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<Book> getBooks() {
        return books;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private double calculateTotalPrice() {
        return books.stream().mapToDouble(Book::getPrice).sum();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", books=" + books +
                ", totalPrice=" + totalPrice +
                '}';
    }
}