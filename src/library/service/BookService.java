package library.service;

import library.model.Book;
import library.repository.BookRepository;

import java.util.ArrayList;

public class BookService {
    BookRepository bookrepository = new BookRepository();

    public ArrayList<Book> getAllBooks() {
        return bookrepository.getAllBooks();
    }

    public ArrayList<Book> getAllAvailableBooks() {
        ArrayList<Book> books = bookrepository.getAllBooks();
        ArrayList<Book> availableBooks = new ArrayList<>();
        for (Book b : books) {
            if (b.getAvailableCopies() > 0) {
                availableBooks.add(b);
            }
        } return availableBooks;
    }


}
