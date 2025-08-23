package com.bookstore.utils;

import com.bookstore.models.Book;
import com.bookstore.models.Customer;
import com.bookstore.models.Order;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class DataManager {

    public static void saveBooks(List<Book> books, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Book> loadBooks(String filename) {
        List<Book> books = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            return books;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            @SuppressWarnings("unchecked")
            List<Book> loadedBooks = (List<Book>) ois.readObject();
            books = loadedBooks;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static void saveCustomers(List<Customer> customers, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Customer> loadCustomers(String filename) {
        List<Customer> customers = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            return customers;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            @SuppressWarnings("unchecked")
            List<Customer> loadedCustomers = (List<Customer>) ois.readObject();
            customers = loadedCustomers;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static void saveOrders(List<Order> allOrders, Queue<Order> pendingOrders, String filename) {
        Map<String, List<Order>> ordersToSave = new HashMap<>();
        ordersToSave.put("ALL", allOrders);
        ordersToSave.put("PENDING", new ArrayList<>(pendingOrders));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(ordersToSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, List<Order>> loadOrders(String filename, List<Book> allBooks) {
        Map<String, List<Order>> orders = new HashMap<>();
        orders.put("PENDING", new ArrayList<>());
        orders.put("PROCESSED", new ArrayList<>());
        File file = new File(filename);
        if (!file.exists()) {
            return orders;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            @SuppressWarnings("unchecked")
            Map<String, List<Order>> loadedOrders = (Map<String, List<Order>>) ois.readObject();
            List<Order> all = loadedOrders.get("ALL");
            List<Order> pending = loadedOrders.get("PENDING");
            List<Order> processed = new ArrayList<>();
            for (Order order : all) {
                if (!pending.contains(order)) {
                    processed.add(order);
                }
            }
            orders.put("PENDING", pending);
            orders.put("PROCESSED", processed);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
