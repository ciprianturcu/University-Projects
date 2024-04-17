import model.Book;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class View {
    private static BookManagerFacade facade = new BookManagerFacade();
    private static Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            printMenu();
            int command = safeNextInt();
            switch (command) {
                case 1:
                    addBook();
                    break;
                case 2:
                    listBooks();
                    break;
                case 3:
                    searchBooks();
                    break;
                case 4:
                    sortBooks();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addBook() {
        scanner.nextLine(); // Flush scanner
        System.out.println("Enter title:");
        String title = safeNextLine("Title cannot be empty.");
        System.out.println("Enter author:");
        String author = safeNextLine("Author cannot be empty.");
        System.out.println("Enter ISBN:");
        String isbn = safeNextLine("ISBN cannot be empty.");
        System.out.println("Enter year published:");
        int yearPublished = safeNextInt();

        Command addCommand = new AddBookCommand(facade, new Book(title, author, isbn, yearPublished));
        addCommand.execute();
        System.out.println("Book added successfully.");
    }

    private static void listBooks() {
        List<Book> allBooks = facade.getAllBooks();
        allBooks.forEach(System.out::println);
    }

    private static void searchBooks() {
        System.out.println("Enter author to search by:");
        String searchAuthor = scanner.nextLine();
        List<Book> booksByAuthor = facade.searchBooksByAuthor(searchAuthor);
        booksByAuthor.forEach(System.out::println);
    }

    private static void sortBooks() {
        printSortMenu();
        int sortOption = safeNextInt();
        List<Book> sortedBooks = null;
        switch (sortOption) {
            case 1:
                sortedBooks = facade.sortBooksByTitle();
                break;
            case 2:
                sortedBooks = facade.sortBooksByAuthor();
                break;
            case 3:
                sortedBooks = facade.sortBooksByYearPublished();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid sort option. Returning to main menu.");
                return;
        }
        if (sortedBooks != null) {
            sortedBooks.forEach(System.out::println);
        }
    }

    private static void printMenu() {
        System.out.println("1. Add a book");
        System.out.println("2. List all books");
        System.out.println("3. Search for a book");
        System.out.println("4. Sort");
        System.out.println("0. Exit");
    }

    private static void printSortMenu() {
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Publish year");
        System.out.println("0. Exit");
    }

    private static int safeNextInt() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // consume the invalid input
            } finally {
                scanner.nextLine(); // always consume the newline
            }
        }
    }

    private static String safeNextLine(String errorMessage) {
        String input;
        while ((input = scanner.nextLine()).isEmpty()) {
            System.out.println(errorMessage);
        }
        return input;
    }
}
