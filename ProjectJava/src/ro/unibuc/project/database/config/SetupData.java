package ro.unibuc.project.database.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupData {
    public void setup(){
        createLocationTable();
        createOnlineEventTable();
        createPhysicalEventTable();
        createClientTable();
        createTicketTable();
    }
    private void createLocationTable() {
        String query = "CREATE TABLE IF NOT EXISTS locations (\n" +
                "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    country VARCHAR(255),\n" +
                "    city VARCHAR(255),\n" +
                "    street_name VARCHAR(255),\n" +
                "    street_nr INT\n" +
                ")";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createOnlineEventTable(){
        String query = "CREATE TABLE IF NOT EXISTS online_events (\n" +
                "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    name VARCHAR(255),\n" +
                "    max_people INT,\n" +
                "    type VARCHAR(255),\n" +
                "    year INT,\n" +
                "    month INT,\n" +
                "    day INT,\n" +
                "    hour INT,\n" +
                "    minutes INT,\n" +
                "    min_price DOUBLE,\n" +
                "    link VARCHAR(255)\n" +
                ")";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createPhysicalEventTable(){
        String query = "CREATE TABLE IF NOT EXISTS physical_events (\n" +
                "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    name VARCHAR(255),\n" +
                "    max_people INT,\n" +
                "    type VARCHAR(255),\n" +
                "    year INT,\n" +
                "    month INT,\n" +
                "    day INT,\n" +
                "    hour INT,\n" +
                "    minutes INT,\n" +
                "    min_price DOUBLE,\n" +
                "    location_id INT NOT NULL,\n" +
                "    FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE CASCADE\n" +
                ")";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createClientTable(){
        String query = "CREATE TABLE IF NOT EXISTS clients (\n" +
                "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    name VARCHAR(255),\n" +
                "    surname VARCHAR(255),\n" +
                "    year INT,\n" +
                "    month INT,\n" +
                "    day INT,\n" +
                "    address_id INT NOT NULL,\n" +
                "    FOREIGN KEY (address_id) REFERENCES locations(id) ON DELETE CASCADE\n" +
                ")";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTicketTable(){
        String query = "CREATE TABLE IF NOT EXISTS tickets (\n" +
                "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    code VARCHAR(255),\n" +
                "    type VARCHAR(255),\n" +
                "    client_id INT,\n" +
                "    event_type VARCHAR(255),\n" +
                "    event_id INT,\n" +
                "    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE\n" +
                ")";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
