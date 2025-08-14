import com.bookstore.models.Book;
import com.bookstore.models.Order;
import com.bookstore.services.BookService;
import com.bookstore.services.OrderService;
import com.bookstore.utils.DataManager;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final BookService bookService = new BookService();
    private static final OrderService orderService = new OrderService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String BOOKS_FILE = "data/books.dat";

    public static void main(String[] args) {
        // Load existing data
        List<Book> loadedBooks = DataManager.loadBooks(BOOKS_FILE);
        for (Book book : loadedBooks) {
            bookService.addBook(book);
        }

        System.out.println("Welcome to the Online Bookstore!");

        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addBook();
                case 2 -> viewBooks();
                case 3 -> placeOrder();
                case 4 -> processOrder();
                case 5 -> {
                    DataManager.saveBooks(bookService.getAllBooks(), BOOKS_FILE);
                    System.out.println("Data saved. Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Bookstore Menu ---");
        System.out.println("1. Add a new book");
        System.out.println("2. View all books");
        System.out.println("3. Place an order");
        System.out.println("4. Process next order");
        System.out.println("5. Save and Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addBook() {
        System.out.print("Enter book ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        bookService.addBook(new Book(id, title, author, price));
        System.out.println("Book added successfully!");
    }

    private static void viewBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        System.out.println("\n--- Available Books ---");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private static void placeOrder() {
        System.out.print("Enter your customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter book ID to order: ");
        String bookId = scanner.nextLine();

        Book book = bookService.findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        Order order = new Order("ORD" + System.currentTimeMillis(), customerId, Collections.singletonList(book));
        orderService.placeOrder(order);
    }

    private static void processOrder() {
        if (orderService.hasPendingOrders()) {
            orderService.processNextOrder();
        } else {
            System.out.println("No pending orders to process.");
        }
    }
}