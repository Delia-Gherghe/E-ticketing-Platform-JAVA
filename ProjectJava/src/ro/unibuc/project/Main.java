package ro.unibuc.project;

import ro.unibuc.project.clients.*;
import ro.unibuc.project.events.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        PlatformService service = new PlatformService();
        Event[] events = service.initEvents(5);
        System.out.println("Available events:\n------------------------------------------------");
        service.display(events);
        System.out.println();

        Client[] clients = service.initClients(7);
        System.out.println("Registered clients:\n------------------------------------------------");
        service.display(clients);
        System.out.println();

        for(Client client : clients){
            service.buyMultipleTickets(client, events);
        }

        System.out.println("Clients and their tickets:\n------------------------------------------------\n");
        service.display(clients);
        System.out.println();

        System.out.println(clients[1].getName() + " " + clients[1].getSurname() + " and his/her tickets:\n------------------------------------------------\n");
        System.out.println(clients[1]);
        Arrays.sort(clients[1].getTickets());
        System.out.println("\n" + clients[1].getName() + " " + clients[1].getSurname() + " after his/her tickets have been sorted by date:\n------------------------------------------------\n");
        System.out.println(clients[1]);


        service.sortClientsNrTicketsAge(clients);
        System.out.println("\nClients sorted by tickets and age:\n------------------------------------------------\n");
        service.display(clients);


        System.out.println("\nEvents filtered by type \"concert\":\n------------------------------------------------\n");
        service.display(service.filter(new EventTypeFilter(), events, "concert"));

        System.out.println("\nOverage clients:\n------------------------------------------------\n");
        Client[] overageClients = service.filter(new ClientAgeFilter(), clients, 18L);
        service.sortClientsAge(overageClients);
        service.display(overageClients);

        System.out.println("\nNumber of days until " + events[0].getName() + ":\n------------------------------------------------");
        System.out.println(events[0].remainingDays());
    }
}
