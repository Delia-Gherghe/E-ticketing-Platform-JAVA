package ro.unibuc.project.database.repository;

import ro.unibuc.project.clients.Ticket;
import ro.unibuc.project.database.config.DatabaseConfiguration;
import ro.unibuc.project.events.OnlineEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TicketRepository {
    public Ticket insert(int client_id, Ticket ticket){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "INSERT into tickets(code, type, client_id, event_type, event_id)" +
                    " VALUES(?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, ticket.getCode());
            preparedStatement.setString(2, ticket.getTicketType());
            preparedStatement.setInt(3, client_id);
            if (ticket.getEvent() instanceof OnlineEvent){
                preparedStatement.setString(4, "online");
            } else {
                preparedStatement.setString(4, "physical");
            }
            preparedStatement.setInt(5, ticket.getEvent().getId());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                ticket.setId(resultSet.getInt(1));
            }
            resultSet.close();
            return ticket;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while inserting the ticket: " + ticket + exception);
        }
    }

    public Set<Ticket> findByClientId(int id) {
        List<Ticket> ticketList = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * FROM tickets where client_id=" + id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            List<String> eventTypes = new ArrayList<>();
            List<Integer> events_id = new ArrayList<>();
            while (resultSet.next()) {
                eventTypes.add(resultSet.getString(5));
                events_id.add(resultSet.getInt(6));
                ticketList.add(mapToTicket(resultSet));
            }

            resultSet.close();
            for (int i = 0; i < ticketList.size(); i++){
                if (eventTypes.get(i).equals("online")){
                    OnlineEventRepository onlineEventRepository = new OnlineEventRepository();
                    ticketList.get(i).setEvent(onlineEventRepository.findById(events_id.get(i)));
                } else{
                    PhysicalEventRepository physicalEventRepository = new PhysicalEventRepository();
                    ticketList.get(i).setEvent(physicalEventRepository.findById(events_id.get(i)));
                }
            }
            return new TreeSet<>(ticketList);

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to get all tickets from client with id " + id + exception);
        }
    }


    private Ticket mapToTicket(ResultSet resultSet) throws SQLException {
        Ticket ticket = new Ticket(resultSet.getString(2), resultSet.getString(3), null);
        ticket.setId(resultSet.getInt(1));
        return ticket;
    }
}
