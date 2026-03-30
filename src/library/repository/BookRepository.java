package library.repository;

import library.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BookRepository {
    private final String URL = "jdbc:mysql://localhost:3306/bibliotek";
    private final String USER = "root";
    private final String PASS = "Apelsinkr0kant!";
    Scanner scanner = new Scanner(System.in);

    //visa alla böcker i biblioteket
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


    //sök på en boktitel
    public ArrayList<Book> searchBook(String searchTerm) {
        ArrayList<Book> books = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT b.title, b.year_published, b.available_copies, bd.summary, a.first_name, a.last_name FROM books b JOIN book_descriptions bd ON bd.book_id=b.id JOIN book_authors ba ON b.id=ba.book_id JOIN authors a ON a.id=ba.author_id WHERE title LIKE ?")) {

            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                int yearPublished = rs.getInt("year_published");
                int availableCopies = rs.getInt("available_copies");
                String summary = rs.getString("summary");
                String author = rs.getString("first_name") + " " + rs.getString("last_name");

                Book book = new Book(title, yearPublished, availableCopies, summary, author);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return books;

    }

    //lägg till en bok, ink författare, bokbeskrivning, författarbeskrivning samt junction tables
    public void addBook() {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO books (title, isbn, year_published, total_copies, available_copies) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            System.out.println("Skriv in titeln på boken:");
            stmt.setString(1, scanner.nextLine());
            System.out.println("Skriv in isbn för boken:");
            stmt.setString(2, scanner.nextLine());
            System.out.println("Skriv in vilket år boken publicerades:");
            stmt.setInt(3, Integer.parseInt(scanner.nextLine()));
            System.out.println("Skriv in totalt antal kopior av boken:");
            stmt.setInt(4, Integer.parseInt(scanner.nextLine()));
            System.out.println("Skriv in antal tillgängliga kopior av boken:");
            stmt.setInt(5, Integer.parseInt(scanner.nextLine()));

            int bookRowsAffected = stmt.executeUpdate();
//            //kontrollerar att boken har lagts till
//            if (bookRowsAffected > 0) {
//                System.out.println("Du har lagt till boken.");
//            } else {
//                System.out.println("Tilläggnigen misslyckades.");
//            }
            ResultSet generatedBookKey = stmt.getGeneratedKeys();
            int bookId = 0;
            if (generatedBookKey.next()) {
                bookId = generatedBookKey.getInt(1);
            }

            //lägg till info i book_descriptions
            try (PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO book_descriptions (book_id, summary, language, page_count) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt3.setInt(1, bookId);
                System.out.println("Skriv in summeringen för boken:");
                stmt3.setString(2, scanner.nextLine());
                System.out.println("Skriv in språket för boken:");
                stmt3.setString(3, scanner.nextLine());
                System.out.println("Skriv in antalet sidor för boken:");
                stmt3.setInt(4, Integer.parseInt(scanner.nextLine()));

                stmt3.executeUpdate();

            }

            //lägg till kategori-info
            int categoryId = 0;
            try (PreparedStatement stmt4 = conn.prepareStatement("SELECT * FROM categories WHERE name = ?")) {
                System.out.println("Skriv in vilken kategori boken tillhör:");
                stmt4.setString(1, scanner.nextLine());

                ResultSet bookCategoryRowsAffected = stmt4.executeQuery();
                if (bookCategoryRowsAffected.next()) {
                    categoryId = bookCategoryRowsAffected.getInt("id");
                }
            }

            try (PreparedStatement stmt5 = conn.prepareStatement("INSERT INTO book_categories (book_id, category_id) VALUES (?, ?)")) {
                stmt5.setInt(1, bookId);
                stmt5.setInt(2, categoryId);
                stmt5.executeUpdate();
            }

            //lägg till info i authors
            int authorId = 0;
            try (PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO authors (first_name, last_name, nationality, birth_date) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                System.out.println("Skriv in författarens förnamn:");
                stmt2.setString(1, scanner.nextLine());
                System.out.println("Skriv in författarens efternamn:");
                stmt2.setString(2, scanner.nextLine());
                System.out.println("Skriv in författarens nationalitet:");
                stmt2.setString(3, scanner.nextLine());
                System.out.println("Skriv in författarens födelsedag:");
                stmt2.setString(4, scanner.nextLine());

                stmt2.executeUpdate();

                ResultSet generatedAuthorKey = stmt2.getGeneratedKeys();
                if (generatedAuthorKey.next()) {
                    authorId = generatedAuthorKey.getInt(1);
                }
            }

            //lägg till info i book_authors
            try (PreparedStatement stmt6 = conn.prepareStatement("INSERT INTO book_authors (book_id, author_id) VALUES (?, ?)")) {
                stmt6.setInt(1, bookId);
                stmt6.setInt(2, authorId);
                stmt6.executeUpdate();
            }

            //lägg till info i author_descriptions
            try (PreparedStatement stmt7 = conn.prepareStatement("INSERT INTO author_descriptions (author_id, biography, website) VALUES (?, ?, ?)")) {
                stmt7.setInt(1, authorId);
                System.out.println("Skriv in biografi för författaren:");
                stmt7.setString(2, scanner.nextLine());
                System.out.println("Skriv in hemsidan till författaren:");
                stmt7.setString(3, scanner.nextLine());

                stmt7.executeUpdate();

            }


        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());

        }
    }

    public void editBook() {
        System.out.println("Skriv in ID på den bok du vill uppdatera:");
        int bookId = Integer.parseInt(scanner.nextLine());
        boolean active = true;

        while (active) {
            System.out.println("Vilken kolumn vill du uppdatera?");
            System.out.println("1. Title");
            System.out.println("2. ISBN");
            System.out.println("3. Year Published");
            System.out.println("4. Total copies");
            System.out.println("5. Available copies");
            System.out.println("0. Return");

            int choice = Integer.parseInt(scanner.nextLine());

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                switch (choice) {
                    case 1: {
                        System.out.println("Skriv in den nya titeln:");
                        String bookTitle = scanner.nextLine();
                        PreparedStatement stmt = conn.prepareStatement("UPDATE books SET title = ? WHERE id = ?");
                        stmt.setString(1, bookTitle);
                        stmt.setInt(2, bookId);
                        stmt.executeUpdate();
                        break;
                    }
                    case 2: {
                        System.out.println("Skriv in det nya ISBN-värdet:");
                        String bookIsbn = scanner.nextLine();
                        PreparedStatement stmt = conn.prepareStatement("UPDATE books SET isbn = ? WHERE id = ?");
                        stmt.setString(1, bookIsbn);
                        stmt.setInt(2, bookId);
                        stmt.executeUpdate();
                        break;
                    }
                    case 3: {
                        System.out.println("Skriv in det nya publicerings-året:");
                        int bookYearPublished = Integer.parseInt(scanner.nextLine());
                        PreparedStatement stmt = conn.prepareStatement("UPDATE books SET year_published = ? WHERE id = ?");
                        stmt.setInt(1, bookYearPublished);
                        stmt.setInt(2, bookId);
                        stmt.executeUpdate();
                        break;
                    }
                    case 4: {
                        System.out.println("Skriv in det nya totala antalet kopior:");
                        int bookTotalCopies = Integer.parseInt(scanner.nextLine());
                        PreparedStatement stmt = conn.prepareStatement("UPDATE books SET total_copies = ? WHERE id = ?");
                        stmt.setInt(1, bookTotalCopies);
                        stmt.setInt(2, bookId);
                        stmt.executeUpdate();
                        break;
                    }
                    case 5: {
                        System.out.println("Skriv in det nya antalet tillgängliga kopior:");
                        int bookAvailableCopies = Integer.parseInt(scanner.nextLine());
                        PreparedStatement stmt = conn.prepareStatement("UPDATE books SET available_copies = ? WHERE id = ?");
                        stmt.setInt(1, bookAvailableCopies);
                        stmt.setInt(2, bookId);
                        stmt.executeUpdate();
                        break;
                    }
                    case 0: {
                        active = false;
                        break;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}