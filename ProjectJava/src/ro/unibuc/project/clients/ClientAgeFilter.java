package ro.unibuc.project.clients;

import ro.unibuc.project.PlatformService;
import ro.unibuc.project.common.Filterable;

import java.util.ArrayList;
import java.util.List;

public class ClientAgeFilter implements Filterable<Long, Client> {
    @Override
    public List<Client> filter(List<Client> items, Long value) {
        List<Client> filteredClients = new ArrayList<>();
        PlatformService service = new PlatformService();
        for (Client client : items) {
            if (service.age(client) >= value) {
                filteredClients.add(client);
            }
        }
        return filteredClients;
    }
}
