package library.repository;

import library.model.Book;

import java.sql.*;
import java.util.ArrayList;

public class BookRepository {
    private final String URL  = "jdbc:mysql://localhost:3306/bibliotek";
    private final String USER = "root";
    private final String PASS = "Apelsinkr0kant!";

public ArrayList<Book> getAllBooks() {
    ArrayList<Book> books = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT b.title, b.year_published, b.available_copies, bd.summary, bd.language, bd.page_count, a.first_name, a.last_name FROM books b JOIN book_descriptions bd ON bd.book_id=b.id JOIN book_authors ba ON b.id=ba.book_id JOIN authors a ON a.id=ba.author_id");

            while (rs.next()) {
                String title = rs.getString("title");
                int yearPublished = rs.getInt("year_published");
                int availableCopies = rs.getInt("available_copies");
                String summary = rs.getString("summary");
                String language = rs.getString("language");
                int pageCount = rs.getInt("page_count");
                String author = rs.getString("first_name") + " " + rs.getString("last_name");

                Book book = new Book(title, yearPublished, availableCopies, summary, language, pageCount, author);
                books.add(book);

            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return books;
        }
}
