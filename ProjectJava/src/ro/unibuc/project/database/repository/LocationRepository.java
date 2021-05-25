package ro.unibuc.project.database.repository;

import ro.unibuc.project.common.Location;
import ro.unibuc.project.database.config.DatabaseConfiguration;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LocationRepository {

    public Location insert(Location location){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "INSERT into locations(country, city, street_name, street_nr) VALUES(?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, location.getCountry());
            preparedStatement.setString(2, location.getCity());
            preparedStatement.setString(3, location.getStreetName());
            preparedStatement.setInt(4, location.getStreetNr());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                location.setId(resultSet.getInt(1));
            }
            resultSet.close();
            return location;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while inserting the location: " + location + exception);
        }
    }

    public List<Location> findAll() {
        List<Location> locations = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * FROM locations";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                locations.add(mapToLocation(resultSet));
            }

            resultSet.close();
            return locations;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to get all locations" + exception);
        }
    }

    public void deleteById(int id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "DELETE FROM locations where id=" + id;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);


        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to delete location with id " + id + exception);
        }
    }

    public void update(int id, Location location){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "UPDATE locations SET country=?, city=?, street_name=?, street_nr=? where id=" + id;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, location.getCountry());
            preparedStatement.setString(2, location.getCity());
            preparedStatement.setString(3, location.getStreetName());
            preparedStatement.setInt(4, location.getStreetNr());

            preparedStatement.executeUpdate();


        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to update location with id " + id + exception);
        }
    }

    public Location findById(int id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * from locations where id=" + id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Location location = null;
            if (resultSet.next()){
                location = mapToLocation(resultSet);
            }

            resultSet.close();
            return location;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to find location with id " + id + exception);
        }
    }

    private Location mapToLocation(ResultSet resultSet) throws SQLException {
        Location location = new Location(resultSet.getString(2), resultSet.getString(3),
                resultSet.getString(4), resultSet.getInt(5));
        location.setId(resultSet.getInt(1));
        return location;
    }
}
