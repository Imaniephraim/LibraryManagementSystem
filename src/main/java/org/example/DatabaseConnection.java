package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Library_Management";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "ephraIms@ccount";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
