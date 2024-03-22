package org.example;

import com.sun.source.tree.TryTree;

import javax.xml.crypto.Data;
import java.awt.dnd.DropTarget;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public static boolean authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT + FROM users WHERE username = ? AND password =?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        }
    }
    public static List<User> getAllUser() throws SQLException{
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection conn = DatabaseConnection.getConnection();PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user =  new User();
                user.setUserId(rs.getInt("user_id"));
                user.setPassword(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        }
        return users;
    }
    public static void addUser(User user) throws SQLException{
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try(Connection conn = DatabaseConnection.getConnection();PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.executeUpdate();
        }
    }

    public static void updateUser(User user) throws SQLException{
        String query = "Update users SET username = ?, password = ?, role = ? WHERE user_id =?";

        try(Connection conn = DatabaseConnection.getConnection();PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getUserId());
            stmt.executeUpdate();
        }
    }
    public static void deleteUser(int userId) throws SQLException{
        String query = "DELETE FROM users WHERE user_id = ?";
        try(Connection conn = DatabaseConnection.getConnection();PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

}






















