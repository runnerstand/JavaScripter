package com.bookstore.services;

import com.bookstore.models.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BookService {
    private List<Book> books;
    private Stack<Book> addedBooksStack;

    public BookService() {
        this.books = new ArrayList<>();
        this.addedBooksStack = new Stack<>();
    }

    public void addBook(Book book) {
        books.add(book);
        addedBooksStack.push(book);
    }

    public void undoAddBook() {
        if (!addedBooksStack.isEmpty()) {
            Book lastAddedBook = addedBooksStack.pop();
            books.remove(lastAddedBook);
            System.out.println("Undo successful. Removed book: " + lastAddedBook.getTitle());
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    public void updateBook(Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(updatedBook.getId())) {
                books.set(i, updatedBook);
                return;
            }
        }
    }

    public void deleteBook(String id) {
        books.removeIf(book -> book.getId().equals(id));
    }

    public Book findBookById(String id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }
}
