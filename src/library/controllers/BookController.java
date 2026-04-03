package library.controllers;

import library.models.Book;
import library.services.BookService;

import java.util.ArrayList;
import java.util.Scanner;

public class BookController {
    BookService bookService = new BookService();
    Scanner scanner = new Scanner(System.in);

    public void memberBookMenu() {
        boolean active = true;

        while (active) {
            System.out.println("---- Book Menu ----");
            System.out.println("1. Show all books");
            System.out.println("2. Show all available books");
            System.out.println("3. Show top ten most popular books");
            System.out.println("4. Search book");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    ArrayList<Book> books = new ArrayList<>(bookService.getAllBooks());
                    for (Book b : books) {
                        System.out.println(b);
                    }
                    break;
                }
                case 2: {
                    ArrayList<Book> books = new ArrayList<>(bookService.getAllAvailableBooks());
                    for (Book b : books) {
                        System.out.println(b);
                    }
                    break;
                }

                case 3: {
                    ArrayList<Book> books = new ArrayList<>(bookService.getPopularBooks());
                    for (Book b : books) {
                        System.out.println(b);
                    }
                    break;
                }

                case 4: {
                    System.out.println("Sök på en bok-titel:");
                    ArrayList<Book> books = bookService.searchBook(scanner.nextLine());
                    for (Book b : books) {
                        System.out.println(b.toSearchString());
                    }
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }
            }

        }
    }

    public void adminBookMenu() {
        boolean active = true;
        while (active) {
            System.out.println("1. Add book");
            System.out.println("2. Update book");
            System.out.println("3. Delete book");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    bookService.addBook();
                    break;
                }
                case 2: {
                    bookService.editBook();
                    break;
                }
                case 3: {
                    bookService.deleteBook();
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }
            }
        }
    }
}
