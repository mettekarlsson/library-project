package library.controllers;

import library.Main;
import library.model.Book;
import library.repository.BookRepository;
import library.service.BookService;

import java.util.ArrayList;
import java.util.Scanner;

public class BookController {
    BookService bookservice = new BookService();
    BookRepository bookrepository = new BookRepository();
    Scanner scanner = new Scanner(System.in);


    public void memberMainMenu() {
        boolean active = true;

        while (active) {
            System.out.println("---- Welcome ----");
            System.out.println("1. Books");
            System.out.println("2. Loans");
            System.out.println("3. My profile");
            System.out.println("4. Authors? ");
            System.out.println("0. Log out");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    memberBookMenu();
                    break;
                }
                case 2: {
                    //memberLoanMenu();
                    break;
                }
                case 3: {
                    //memberProfileMenu();
                    break;
                }
                case 4: {
                   // memberAuthorMenu();
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }
            }
        }
    }

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
            System.out.println("0. Log out");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    bookservice.addBook();
                    break;
                }
                case 2: {
                    bookservice.editBook();
                    break;
                }
                case 3: {
                    bookservice.deleteBook();
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
