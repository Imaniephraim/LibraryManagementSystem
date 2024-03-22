package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public static List<Book> getAllBooks() throws SQLException{
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()){
            while(rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setAvailable(rs.getBoolean("available"));
                book.setBorrowedBy(rs.getInt("borrowed_by"));
                book.setBorrowedDate(rs.getDate("borrowed_date"));
                books.add(book);
            }
        }
        return books;
    }

    public static void addBooks(Book book)throws SQLException {
        String query = "INSERT INTO BOOKS (title, author) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.executeUpdate();
        }
    }

    public static void borrowBook (int bookId, int userId)throws SQLException{
        String query = "UPDATE books SET available = false, borrowed_by =?,borrowed_date = CURRENT_DATE WHERE book_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        }
    }
    public static void deleteBook(int bookId) throws SQLException{
        String query = "DELETE FROM books WHERE book_id = ?";
        try(Connection conn = DatabaseConnection.getConnection();PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        }
    }
}



























