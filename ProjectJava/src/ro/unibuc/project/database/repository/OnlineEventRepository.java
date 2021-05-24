package ro.unibuc.project.database.repository;

import ro.unibuc.project.common.DateTime;
import ro.unibuc.project.database.config.DatabaseConfiguration;
import ro.unibuc.project.events.OnlineEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OnlineEventRepository {
    public void insert(OnlineEvent onlineEvent){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "INSERT into online_events(name, max_people, type, year, month, day, hour, minutes, min_price, link)" +
                    " VALUES(?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, onlineEvent.getName());
            preparedStatement.setInt(2, onlineEvent.getMaxPeople());
            preparedStatement.setString(3, onlineEvent.getType());
            preparedStatement.setInt(4, onlineEvent.getDateTime().getYear());
            preparedStatement.setInt(5, onlineEvent.getDateTime().getMonth());
            preparedStatement.setInt(6, onlineEvent.getDateTime().getDay());
            preparedStatement.setInt(7, onlineEvent.getDateTime().getHour());
            preparedStatement.setInt(8, onlineEvent.getDateTime().getMinutes());
            preparedStatement.setDouble(9, onlineEvent.getMinPrice());
            preparedStatement.setString(10, onlineEvent.getLink());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                onlineEvent.setId(resultSet.getInt(1));
            }
            resultSet.close();

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while inserting the online event: " + onlineEvent);
        }
    }

    public List<OnlineEvent> findAll() {
        List<OnlineEvent> onlineEvents = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * FROM online_events";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                onlineEvents.add(mapToOnlineEvent(resultSet));
            }

            resultSet.close();
            return onlineEvents;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to get all online events");
        }
    }

    public OnlineEvent findById(int id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * from online_events where id=" + id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            OnlineEvent onlineEvent = null;
            if (resultSet.next()){
                onlineEvent = mapToOnlineEvent(resultSet);
            }

            resultSet.close();
            return onlineEvent;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to find online event with id " + id);
        }
    }

    public void updateMaxPeople(int id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "UPDATE online_events SET max_people = max_people - 1 where id=" + id;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);


        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to update online event with id " + id);
        }
    }

    public void deleteById(int id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "DELETE FROM online_events where id=" + id;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);


        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to delete online event with id " + id);
        }
    }

    private OnlineEvent mapToOnlineEvent(ResultSet resultSet) throws SQLException {
        DateTime dateTime = new DateTime(resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7),
                resultSet.getInt(8), resultSet.getInt(9));
        OnlineEvent onlineEvent = new OnlineEvent(resultSet.getString(2), resultSet.getInt(3),
                resultSet.getString(4), dateTime, resultSet.getDouble(10), resultSet.getString(11));
        onlineEvent.setId(resultSet.getInt(1));
        return onlineEvent;
    }
}
