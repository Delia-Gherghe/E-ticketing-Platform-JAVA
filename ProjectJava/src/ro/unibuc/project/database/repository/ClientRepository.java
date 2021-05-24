package ro.unibuc.project.database.repository;

import ro.unibuc.project.clients.Client;
import ro.unibuc.project.common.Date;
import ro.unibuc.project.common.Location;
import ro.unibuc.project.database.config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository {
    public Client insert(Client client){
        LocationRepository locationRepository = new LocationRepository();
        Location address = locationRepository.insert(client.getAddress());
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "INSERT into clients(name, surname, year, month, day, address_id)" +
                    " VALUES(?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getSurname());
            preparedStatement.setInt(3, client.getBirthday().getYear());
            preparedStatement.setInt(4, client.getBirthday().getMonth());
            preparedStatement.setInt(5, client.getBirthday().getDay());
            preparedStatement.setInt(6, address.getId());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                client.setId(resultSet.getInt(1));
            }
            resultSet.close();
            return client;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while inserting the client: " + client);
        }
    }

    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * FROM clients";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            List<Integer> addresses_id = new ArrayList<>();
            while (resultSet.next()) {
                addresses_id.add(resultSet.getInt(7));
                clients.add(mapToClient(resultSet));
            }

            resultSet.close();
            LocationRepository locationRepository = new LocationRepository();
            TicketRepository ticketRepository = new TicketRepository();
            for (int i = 0; i < clients.size(); i++){
                clients.get(i).setAddress(locationRepository.findById(addresses_id.get(i)));
                clients.get(i).setTickets(ticketRepository.findByClientId(clients.get(i).getId()));
            }
            return clients;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to get all clients");
        }
    }

    public void updateSurname(int id, String surname){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "UPDATE clients SET surname=? where id=" + id;
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, surname);

            preparedStatement.executeUpdate();


        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to update client with id " + id);
        }
    }

    public void deleteById(int id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "DELETE FROM clients where id=" + id;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);


        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to delete client with id " + id);
        }
    }

    public Client findById(int id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * from clients where id=" + id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Client client = null;
            Integer address_id = null;
            if (resultSet.next()){
                address_id = resultSet.getInt(7);
                client = mapToClient(resultSet);
            }

            resultSet.close();
            LocationRepository locationRepository = new LocationRepository();
            if (client != null){
                client.setAddress(locationRepository.findById(address_id));
                TicketRepository ticketRepository = new TicketRepository();
                client.setTickets(ticketRepository.findByClientId(client.getId()));
            }

            return client;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to find client with id " + id);
        }
    }

    private Client mapToClient(ResultSet resultSet) throws SQLException {
        Date birthday = new Date(resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6));
        Client client = new Client(resultSet.getString(2), resultSet.getString(3), birthday,  null);
        client.setId(resultSet.getInt(1));
        return client;
    }
}
