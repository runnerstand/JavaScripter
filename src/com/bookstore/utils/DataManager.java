package com.bookstore.utils;

import com.bookstore.models.Book;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
}
