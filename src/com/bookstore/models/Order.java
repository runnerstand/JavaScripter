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
    private String status;

    public Order(String id, String customerId, List<Book> books) {
        this.id = id;
        this.customerId = customerId;
        this.books = books;
        // Sort books by title
        Collections.sort(this.books, Comparator.comparing(Book::getTitle));
        this.totalPrice = calculateTotalPrice();
        this.status = "AWAITING";
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private double calculateTotalPrice() {
        return books.stream().mapToDouble(Book::getPrice).sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderID: ").append(id).append("\n");
        sb.append("CustomerID: ").append(customerId).append("\n");
        sb.append("Books:\n");
        for (Book book : books) {
            sb.append("\t- ").append(book.getTitle()).append(" by ").append(book.getAuthor()).append("\n");
        }
        sb.append("TotalPrice: ").append(totalPrice).append("\n");
        sb.append("Status: ").append(status).append("\n");
        return sb.toString();
    }
}
