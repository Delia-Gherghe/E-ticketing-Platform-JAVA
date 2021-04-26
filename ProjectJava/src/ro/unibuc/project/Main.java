package ro.unibuc.project;

import ro.unibuc.project.clients.*;
import ro.unibuc.project.common.Location;
import ro.unibuc.project.events.*;
import ro.unibuc.project.ioservices.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PlatformService service = new PlatformService();
        Logger logger = Logger.getInstance();

        Reader reader = Reader.getInstance();
        List<Location> locations = reader.readLocationsCSV();
        List<Client> clients = reader.readClientsCSV();
        List<OnlineEvent> onlineEvents = reader.readOnlineEventsCSV();
        List<PhysicalEvent> physicalEvents = reader.readPhysicalEventsCSV();

        Scanner scanner = new Scanner(System.in);
        showMenu();
        int option = scanner.nextInt();
        while (true){

            switch (option) {
                case 0:
                    Writer writer = Writer.getInstance();
                    writer.writeLocationsCSV(locations);
                    writer.writeClientsCSV(clients);
                    writer.writeOnlineEventsCSV(onlineEvents);
                    writer.writePhysicalEventsCSV(physicalEvents);
                    logger.close();
                    return;
                case 1:
                    service.display(locations);
                    logger.write("Show locations");
                    break;
                case 2:
                    service.display(clients);
                    logger.write("Show clients");
                    break;
                case 3:
                    service.display(onlineEvents);
                    logger.write("Show Online Events");
                    break;
                case 4:
                    service.display(physicalEvents);
                    logger.write("Show Physical Events");
                    break;
                case 5:
                    locations.add(service.generateLocation());
                    logger.write("Add location");
                    break;
                case 6:
                    clients.add(service.generatePerson());
                    logger.write("Add client");
                    break;
                case 7:
                    onlineEvents.add((OnlineEvent) service.generateEvent(true));
                    logger.write("Add Online Event");
                    break;
                case 8:
                    physicalEvents.add((PhysicalEvent) service.generateEvent(false));
                    logger.write("Add Physical Event");
                    break;
                case 9:
                    service.display(service.filter(new ClientAgeFilter(), clients, 18L));
                    logger.write("Show overage clients");
                    break;
                case 10:
                    System.out.println("Enter event type (concert, movie, sport, fashion, theatre, gaming, art)");
                    String type = scanner.next();
                    service.display(service.filter(new EventTypeFilter(), service.combineEvents(onlineEvents, physicalEvents), type));
                    logger.write("Filter events by type");
                    break;
                case 11:
                    service.listClients(clients);
                    System.out.println("Select client index:");
                    int index = scanner.nextInt();
                    System.out.println(service.age(clients.get(index)));
                    logger.write("Show age of client");
                    break;
                case 12:
                    service.listClients(clients);
                    System.out.println("Select client index:");
                    int ind = scanner.nextInt();
                    System.out.println(service.totalMoneySpent(clients.get(ind)));
                    logger.write("Calculate total amount of money spent by client");
                    break;
                case 13:
                    service.sortClientsAge(clients);
                    service.display(clients);
                    logger.write("Sort clients by age descending");
                    break;
                case 14:
                    service.sortClientsNrTickets(clients);
                    service.display(clients);
                    logger.write("Sort clients by number of tickets bought descending");
                    break;
                case 15:
                    service.sortClientsNrTicketsAge(clients);
                    service.display(clients);
                    logger.write("Sort clients by number of tickets descending and by age descending");
                    break;
                case 16:
                    service.listClients(clients);
                    System.out.println("Select client index:");
                    int clientIndex = scanner.nextInt();
                    Event event = service.chooseEvent(onlineEvents, physicalEvents);
                    System.out.println("Select ticket type (GENERAL, EARLY, VIP):");
                    String ticketType = scanner.next();
                    service.buyTicket(clients.get(clientIndex), event, ticketType);
                    logger.write("Buy ticket selecting client and event");
                    break;
                case 17:
                    Event chosenEvent = service.chooseEvent(onlineEvents, physicalEvents);
                    System.out.println(service.remainingDays(chosenEvent));
                    logger.write("Days left until event");
                    break;
                case 18:
                    service.listLocations(locations);
                    System.out.println("Select location index:");
                    int locationIndex = scanner.nextInt();
                    service.remove(locations, locationIndex);
                    logger.write("Remove location");
                    break;
                case 19:
                    service.listClients(clients);
                    System.out.println("Select client index:");
                    int clIndex = scanner.nextInt();
                    service.remove(clients, clIndex);
                    logger.write("Remove client");
                    break;
                case 20:
                    service.listOnlineEvents(onlineEvents);
                    System.out.println("Select event index:");
                    int oeIndex = scanner.nextInt();
                    service.remove(onlineEvents, oeIndex);
                    logger.write("Remove Online Event");
                    break;
                case 21:
                    service.listPhysicalEvents(physicalEvents);
                    System.out.println("Select event index:");
                    int peIndex = scanner.nextInt();
                    service.remove(physicalEvents, peIndex);
                    logger.write("Remove Physical Event");
                    break;
                default:
                    System.out.println("Incorrect option! Number must be between 0 and 21");

            }

            showMenu();
            option = scanner.nextInt();

        }

    }

    private static void showMenu(){
        System.out.println("Menu:\n0) Exit\n1) Show locations\n2) Show clients\n3) Show Online Events\n" +
                "4) Show Physical Events\n5) Add location\n6) Add client\n7) Add Online Event\n" +
                "8) Add Physical Event\n9) Show overage clients\n10) Filter events by type\n11) Show age of client\n"
                + "12) Calculate total amount of money spent by client\n13) Sort clients by age descending\n" +
                "14) Sort clients by number of tickets bought descending\n15) Sort clients by number of tickets" +
                " descending and by age descending\n16) Buy ticket selecting client and event\n"
                + "17) Days left until event\n18) Remove location\n19) Remove client\n20) Remove online event\n" +
                "21) Remove physical event\n");
    }
}
