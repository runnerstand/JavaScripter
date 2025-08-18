package com.bookstore.models;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Order implements Serializable {
    private String id;
    private String customerId;
    private List<Book> books;
    private double totalPrice;

    public Order(String id, String customerId, List<Book> books) {
        this.id = id;
        this.customerId = customerId;
        this.books = books;
        // Sort books by title
        Collections.sort(this.books, Comparator.comparing(Book::getTitle));
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
