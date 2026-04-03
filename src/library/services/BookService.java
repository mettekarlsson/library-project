package library.services;

import library.models.Book;
import library.repositories.BookRepository;

import java.util.ArrayList;

public class BookService {
    BookRepository bookRepository = new BookRepository();

    //bara ett mellansteg mellan repository och controller
    public ArrayList<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    //filtrerar ut available books från getAllBooks()
    public ArrayList<Book> getAllAvailableBooks() {
        ArrayList<Book> books = bookRepository.getAllBooks();
        ArrayList<Book> availableBooks = new ArrayList<>();
        for (Book b : books) {
            if (b.getAvailableCopies() > 0) {
                availableBooks.add(b);
            }
        } return availableBooks;
    }

    //bara ett mellansteg mellan repository och controller
    public ArrayList<Book> getPopularBooks() {
        return bookRepository.getPopularBooks();
    }

    //bara ett mellansteg mellan repository och controller
    public ArrayList<Book> searchBook(String searchTerm) {
        return bookRepository.searchBook(searchTerm);
    }

    //bara ett mellansteg mellan repository och controller
    public void addBook() {
        bookRepository.addBook();
    }

    //bara ett mellansteg mellan repository och controller
    public void editBook() {
        bookRepository.editBook();
    }

    //bara ett mellansteg mellan repository och controller
    public void deleteBook() {
        bookRepository.deleteBook();
    }


}
