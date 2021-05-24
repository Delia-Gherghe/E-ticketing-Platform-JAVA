package ro.unibuc.project.database.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfiguration {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/eticket";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static Connection databaseConnection;

    private DatabaseConfiguration() {

    }

    public static Connection getDatabaseConnection() {
        try {
            if (databaseConnection == null || databaseConnection.isClosed()) {
                databaseConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return databaseConnection;
    }
}
