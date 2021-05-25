package ro.unibuc.project.database.repository;

import ro.unibuc.project.common.DateTime;
import ro.unibuc.project.common.Location;
import ro.unibuc.project.database.config.DatabaseConfiguration;
import ro.unibuc.project.events.PhysicalEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhysicalEventRepository {
    public PhysicalEvent insert(PhysicalEvent physicalEvent){
        LocationRepository locationRepository = new LocationRepository();
        Location location = locationRepository.insert(physicalEvent.getLocation());
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "INSERT into physical_events(name, max_people, type, year, month, day, hour, minutes, min_price, location_id)" +
                    " VALUES(?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, physicalEvent.getName());
            preparedStatement.setInt(2, physicalEvent.getMaxPeople());
            preparedStatement.setString(3, physicalEvent.getType());
            preparedStatement.setInt(4, physicalEvent.getDateTime().getYear());
            preparedStatement.setInt(5, physicalEvent.getDateTime().getMonth());
            preparedStatement.setInt(6, physicalEvent.getDateTime().getDay());
            preparedStatement.setInt(7, physicalEvent.getDateTime().getHour());
            preparedStatement.setInt(8, physicalEvent.getDateTime().getMinutes());
            preparedStatement.setDouble(9, physicalEvent.getMinPrice());
            preparedStatement.setInt(10, location.getId());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                physicalEvent.setId(resultSet.getInt(1));
            }
            resultSet.close();
            return physicalEvent;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while inserting the physical event: " + physicalEvent + exception);
        }
    }

    public List<PhysicalEvent> findAll() {
        List<PhysicalEvent> physicalEvents = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * FROM physical_events";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            List<Integer> locations_id = new ArrayList<>();
            while (resultSet.next()) {
                locations_id.add(resultSet.getInt(11));
                physicalEvents.add(mapToPhysicalEvent(resultSet));
            }

            resultSet.close();
            LocationRepository locationRepository = new LocationRepository();
            for (int i = 0; i < physicalEvents.size(); i++){
                physicalEvents.get(i).setLocation(locationRepository.findById(locations_id.get(i)));
            }
            return physicalEvents;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to get all physical events" + exception);
        }
    }

    public PhysicalEvent findById(int id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * from physical_events where id=" + id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            PhysicalEvent physicalEvent = null;
            Integer location_id = null;
            if (resultSet.next()){
                location_id = resultSet.getInt(11);
                physicalEvent = mapToPhysicalEvent(resultSet);
            }

            resultSet.close();

            if (physicalEvent != null){
                LocationRepository locationRepository = new LocationRepository();
                physicalEvent.setLocation(locationRepository.findById(location_id));
            }
            return physicalEvent;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to find physical event with id " + id + exception);
        }
    }

    public void updateMaxPeople(int id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "UPDATE physical_events SET max_people = max_people - 1 where id=" + id;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);


        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to update physical event with id " + id + exception);
        }
    }

    public void deleteById(int id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "DELETE FROM physical_events where id=" + id;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);


        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to delete physical event with id " + id + exception);
        }
    }

    private PhysicalEvent mapToPhysicalEvent(ResultSet resultSet) throws SQLException {
        DateTime dateTime = new DateTime(resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7),
                resultSet.getInt(8), resultSet.getInt(9));
        PhysicalEvent physicalEvent = new PhysicalEvent(resultSet.getString(2), resultSet.getInt(3),
                resultSet.getString(4), dateTime, resultSet.getDouble(10), null);
        physicalEvent.setId(resultSet.getInt(1));
        return physicalEvent;
    }
}
