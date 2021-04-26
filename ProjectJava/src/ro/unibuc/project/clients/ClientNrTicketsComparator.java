package ro.unibuc.project.clients;

import java.util.Comparator;

public class ClientNrTicketsComparator implements Comparator<Client> {
    @Override
    public int compare(Client o1, Client o2) {
        return o2.getTickets().size() - o1.getTickets().size();
    }
}
