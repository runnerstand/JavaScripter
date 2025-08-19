package com.bookstore.utils;

import com.bookstore.models.Book;
import com.bookstore.models.Customer;
import com.bookstore.models.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class DataManager {

    public static void saveBooks(List<Book> books, String filename) {
        File file = new File(filename);
        file.getParentFile().mkdirs(); // Ensure the directory exists

        try (PrintWriter writer = new PrintWriter(file)) {
            // Write header
            writer.println("id,title,author,price");
            // Write book data
            for (Book book : books) {
                writer.printf("%s,%s,%s,%.2f%n",
                        book.getId(), book.getTitle(), book.getAuthor(), book.getPrice());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Book> loadBooks(String filename) {
        List<Book> books = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            return books; // Return empty list if file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    try {
                        String id = parts[0];
                        String title = parts[1];
                        String author = parts[2];
                        double price = Double.parseDouble(parts[3]);
                        books.add(new Book(id, title, author, price));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid book entry: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static void saveCustomers(List<Customer> customers, String filename) {
        File file = new File(filename);
        file.getParentFile().mkdirs();

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("id,name");
            for (Customer customer : customers) {
                writer.printf("%s,%s%n", customer.getId(), customer.getName());
            }
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

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    customers.add(new Customer(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static void saveOrders(List<Order> allOrders, Queue<Order> pendingOrders, String filename) {
        File file = new File(filename);
        file.getParentFile().mkdirs();

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("id,customerId,bookIds,status"); // Add status column
            for (Order order : allOrders) {
                List<String> bookIds = new ArrayList<>();
                for (Book book : order.getBooks()) {
                    bookIds.add(book.getId());
                }
                // Check if the order is still in the pending queue
                String status = pendingOrders.contains(order) ? "PENDING" : "PROCESSED";
                writer.printf("%s,%s,%s,%s%n", order.getId(), order.getCustomerId(), String.join(";", bookIds), status);
            }
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

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String id = parts[0];
                    String customerId = parts[1];
                    String[] bookIds = parts[2].split(";");
                    String status = parts[3];

                    List<Book> orderBooks = new ArrayList<>();
                    for (String bookId : bookIds) {
                        for (Book book : allBooks) {
                            if (book.getId().equals(bookId)) {
                                orderBooks.add(book);
                                break;
                            }
                        }
                    }
                    Order order = new Order(id, customerId, orderBooks);
                    if ("PENDING".equals(status)) {
                        orders.get("PENDING").add(order);
                    } else {
                        orders.get("PROCESSED").add(order);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
