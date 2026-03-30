package library.controllers;

import library.Main;
import library.model.Book;
import library.repository.BookRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class BookController {

    public void showBookMenu() {
        BookRepository bookrepository = new BookRepository();
        boolean active = true;
        Scanner scanner = new Scanner(System.in);

        while (active) {
            System.out.println("---- Book Menu ----");
            System.out.println("1. Show all books");
            System.out.println("2. Show all available books");
            System.out.println("3. Search book");
            System.out.println("4. Add book");
            System.out.println("5. Update book");
            System.out.println("6. Delete book");
            System.out.println("0. Return to main menu");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    ArrayList<Book> books = new ArrayList<>(bookrepository.getAllBooks());
                    for (Book b : books) {
                        System.out.println(b);
                    }
                    break;
                }
                case 2: {
                    ArrayList<Book> books = new ArrayList<>(bookrepository.getAllAvailableBooks());
                    for (Book b : books) {
                        System.out.println(b);
                    }
                    break;
                }

                case 3: {
                    System.out.println("Sök på en bok-titel:");
                    ArrayList<Book> books = bookrepository.searchBook(scanner.nextLine());
                    for (Book b : books) {
                        System.out.println(b.toSearchString());
                    }
                    break;
                }
                case 4: {
                   bookrepository.addBook();
                   break;
                }
                case 5: {
                   bookrepository.editBook();
                    break;
                }
                case 6: {
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
