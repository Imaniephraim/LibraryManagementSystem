package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowingHistoryDAO {
    public static List<BorrowingHistory> getBorrowingHistory(int userId) throws SQLException {
        List<BorrowingHistory> borrowingHistory = new ArrayList<>();
        String query = "SELECT * FROM borrowing_history WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BorrowingHistory history = new BorrowingHistory();
                history.setHistoryId(rs.getInt("history_id"));
                history.setBookId(rs.getInt("book_id"));
                history.setUserId(rs.getInt("user_id"));
                history.setBorrowedDate(rs.getDate("borrowed_date"));
                history.setReturnedDate(rs.getDate("returned_date"));
                borrowingHistory.add(history);
            }
        }
        return borrowingHistory;
    }

    public static void addBorrowingHistory(int bookId, int userId) throws SQLException {
        String query = "INSERT INTO borrowing_history (book_id, user_id, borrowed_date) VALUES (?, ?, CURRENT_DATE";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }
    public static void updateReturnDate(int historyId) throws SQLException{
        String query = "UPDATE borrowing_history SET returned_date = CURRENT_DATE where history_id = ?";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, historyId);
            stmt.executeUpdate();
        }
    }

    public static void deleteBorrowingHistory(int historyId) throws SQLException{
        String query = "DELETE FROM borrowing_history WHERE history_id = ?";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, historyId);
            stmt.executeUpdate();
        }
    }
}
























