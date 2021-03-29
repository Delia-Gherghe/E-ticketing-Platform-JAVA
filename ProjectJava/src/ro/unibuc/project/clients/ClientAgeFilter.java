package ro.unibuc.project.clients;

import ro.unibuc.project.common.Filterable;
import ro.unibuc.project.events.Event;

import java.util.Arrays;

public class ClientAgeFilter implements Filterable<Long, Client> {
    @Override
    public Client[] filter(Client[] items, Long value) {
        Client[] filteredClients = new Client[0];
        for (Client client : items) {
            if (client.age() >= value) {
                filteredClients = Arrays.copyOf(filteredClients, filteredClients.length + 1);
                filteredClients[filteredClients.length - 1] = client;
            }
        }
        return filteredClients;
    }
}
