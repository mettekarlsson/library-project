package library.service;

import library.model.Book;
import library.repository.BookRepository;

import java.util.ArrayList;

public class BookService {
    BookRepository bookrepository = new BookRepository();

    //bara ett mellansteg mellan repository och controller
    public ArrayList<Book> getAllBooks() {
        return bookrepository.getAllBooks();
    }

    //filtrerar ut available books från getAllBooks()
    public ArrayList<Book> getAllAvailableBooks() {
        ArrayList<Book> books = bookrepository.getAllBooks();
        ArrayList<Book> availableBooks = new ArrayList<>();
        for (Book b : books) {
            if (b.getAvailableCopies() > 0) {
                availableBooks.add(b);
            }
        } return availableBooks;
    }

    //bara ett mellansteg mellan repository och controller
    public ArrayList<Book> getPopularBooks() {
        return bookrepository.getPopularBooks();
    }

    //bara ett mellansteg mellan repository och controller
    public ArrayList<Book> searchBook(String searchTerm) {
        return bookrepository.searchBook(searchTerm);
    }

    //bara ett mellansteg mellan repository och controller
    public void addBook() {
        bookrepository.addBook();
    }

    //bara ett mellansteg mellan repository och controller
    public void editBook() {
        bookrepository.editBook();
    }

    //bara ett mellansteg mellan repository och controller
    public void deleteBook() {
        bookrepository.deleteBook();
    }


}
