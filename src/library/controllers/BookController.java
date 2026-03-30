package library.controllers;

import library.Main;
import library.model.Book;
import library.repository.BookRepository;
import library.service.BookService;

import java.util.ArrayList;
import java.util.Scanner;

public class BookController {
    BookService bookservice = new BookService();

    public void showBookMenu() {
        BookRepository bookrepository = new BookRepository();
        boolean active = true;
        Scanner scanner = new Scanner(System.in);

        while (active) {
            System.out.println("---- Book Menu ----");
            System.out.println("1. Show all books");
            System.out.println("2. Show all available books");
            System.out.println("3. Show top ten most popular books");
            System.out.println("4. Search book");
            System.out.println("5. Add book");
            System.out.println("6. Update book");
            System.out.println("7. Delete book");
            System.out.println("0. Return to main menu");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    ArrayList<Book> books = new ArrayList<>(bookservice.getAllBooks());
                    for (Book b : books) {
                        System.out.println(b);
                    }
                    break;
                }
                case 2: {
                    ArrayList<Book> books = new ArrayList<>(bookservice.getAllAvailableBooks());
                    for (Book b : books) {
                        System.out.println(b);
                    }
                    break;
                }

                case 3: {
                    ArrayList<Book> books = new ArrayList<>(bookservice.getPopularBooks());
                    for (Book b : books) {
                        System.out.println(b);
                    }
                    break;
                }

                case 4: {
                    System.out.println("Sök på en bok-titel:");
                    ArrayList<Book> books = bookservice.searchBook(scanner.nextLine());
                    for (Book b : books) {
                        System.out.println(b.toSearchString());
                    }
                    break;
                }
                case 5: {
                   bookservice.addBook();
                   break;
                }
                case 6: {
                   bookservice.editBook();
                    break;
                }
                case 7: {
                   // bookrepository.deleteBook();
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
