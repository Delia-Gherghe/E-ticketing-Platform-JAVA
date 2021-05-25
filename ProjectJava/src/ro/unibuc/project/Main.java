package ro.unibuc.project;

import ro.unibuc.project.clients.*;
import ro.unibuc.project.common.Location;
import ro.unibuc.project.database.config.SetupData;
import ro.unibuc.project.database.repository.*;
import ro.unibuc.project.events.*;
import ro.unibuc.project.ioservices.*;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class Main {
    public static void main(String[] args) {

        SetupData setupData = new SetupData();
        setupData.setup();
        PlatformService service = new PlatformService();
        Logger logger = Logger.getInstance();

        /*
        Reader reader = Reader.getInstance();
        List<Location> locations = reader.readLocationsCSV();
        List<Client> clients = reader.readClientsCSV();
        List<OnlineEvent> onlineEvents = reader.readOnlineEventsCSV();
        List<PhysicalEvent> physicalEvents = reader.readPhysicalEventsCSV();

         */


        LocationRepository locationRepository = new LocationRepository();
        OnlineEventRepository onlineEventRepository = new OnlineEventRepository();
        PhysicalEventRepository physicalEventRepository = new PhysicalEventRepository();
        ClientRepository clientRepository = new ClientRepository();


        Scanner scanner = new Scanner(System.in);
        showMenu();
        int option = scanner.nextInt();
        while (true){

            switch (option) {
                case 0:
                    /*
                    Writer writer = Writer.getInstance();
                    writer.writeLocationsCSV(locations);
                    writer.writeClientsCSV(clients);
                    writer.writeOnlineEventsCSV(onlineEvents);
                    writer.writePhysicalEventsCSV(physicalEvents);

                     */
                    logger.close();
                    return;
                case 1:
                    //service.display(locations);
                    service.display(locationRepository.findAll());
                    logger.write("Show locations");
                    break;
                case 2:
                    //service.display(clients);
                    service.display(clientRepository.findAll());
                    logger.write("Show clients");
                    break;
                case 3:
                    //service.display(onlineEvents);
                    service.display(onlineEventRepository.findAll());
                    logger.write("Show Online Events");
                    break;
                case 4:
                    //service.display(physicalEvents);
                    service.display(physicalEventRepository.findAll());
                    logger.write("Show Physical Events");
                    break;
                case 5:
                    locationRepository.insert(service.enterLocation());
                    logger.write("Add location");
                    break;
                case 6:
                    clientRepository.insert(service.enterClient());
                    logger.write("Add client");
                    break;
                case 7:
                    onlineEventRepository.insert(service.enterOnlineEvent());
                    logger.write("Add Online Event");
                    break;
                case 8:
                    physicalEventRepository.insert(service.enterPhysicalEvent());
                    logger.write("Add Physical Event");
                    break;
                case 9:
                    service.display(service.filter(new ClientAgeFilter(), clientRepository.findAll(), 18L));
                    logger.write("Show overage clients");
                    break;
                case 10:
                    System.out.println("Enter event type (concert, movie, sport, fashion, theatre, gaming, art)");
                    String type = scanner.next();
                    //service.display(service.filter(new EventTypeFilter(), service.combineEvents(onlineEvents, physicalEvents), type));
                    service.display(service.filter(new EventTypeFilter(), service.combineEvents(onlineEventRepository.findAll(),
                            physicalEventRepository.findAll()), type));
                    logger.write("Filter events by type");
                    break;
                case 11:
                    //System.out.println(service.age(service.choseClient(clients);
                    System.out.println(service.age(service.choseClient(clientRepository.findAll())));
                    logger.write("Show age of client");
                    break;
                case 12:
                    //System.out.println(service.totalMoneySpent(service.choseClient(clients)) + " $");
                    System.out.println(service.totalMoneySpent(service.choseClient(clientRepository.findAll())) + " $");
                    logger.write("Calculate total amount of money spent by client");
                    break;
                case 13:
                    List<Client> clientsAge = clientRepository.findAll();
                    service.sortClientsAge(clientsAge);
                    service.display(clientsAge);
                    logger.write("Sort clients by age descending");
                    break;
                case 14:
                    List<Client> clientsNrTickets = clientRepository.findAll();
                    service.sortClientsNrTickets(clientsNrTickets);
                    service.display(clientsNrTickets);
                    logger.write("Sort clients by number of tickets bought descending");
                    break;
                case 15:
                    List<Client> clientsTicketsAge = clientRepository.findAll();
                    service.sortClientsNrTicketsAge(clientsTicketsAge);
                    service.display(clientsTicketsAge);
                    logger.write("Sort clients by number of tickets descending and by age descending");
                    break;
                case 16:
                    //Client client = service.choseClient(clients);
                    Client client = service.choseClient(clientRepository.findAll());
                    //Event event = service.chooseEvent(onlineEvents, physicalEvents);
                    Event event = service.chooseEvent(onlineEventRepository.findAll(), physicalEventRepository.findAll());
                    System.out.println("Select ticket type (GENERAL, EARLY, VIP):");
                    String ticketType = scanner.next();
                    service.buyTicket(client, event, ticketType);
                    logger.write("Buy ticket selecting client and event");
                    break;
                case 17:
                    //Event chosenEvent = service.chooseEvent(onlineEvents, physicalEvents);
                    Event chosenEvent = service.chooseEvent(onlineEventRepository.findAll(), physicalEventRepository.findAll());
                    System.out.println(service.remainingDays(chosenEvent));
                    logger.write("Days left until event");
                    break;
                case 18:
                    //service.listLocations(locations);
                    service.listLocations(locationRepository.findAll());
                    System.out.println("Select location index:");
                    int locationIndex = scanner.nextInt();
                    //service.remove(locations, locationIndex);
                    locationRepository.deleteById(locationIndex);
                    logger.write("Remove location");
                    break;
                case 19:
                    //service.listClients(clients);
                    service.listClients(clientRepository.findAll());
                    System.out.println("Select client index:");
                    int clIndex = scanner.nextInt();
                    //service.remove(clients, clIndex);
                    clientRepository.deleteById(clIndex);
                    logger.write("Remove client");
                    break;
                case 20:
                    //service.listOnlineEvents(onlineEvents);
                    service.listOnlineEvents(onlineEventRepository.findAll());
                    System.out.println("Select event index:");
                    int oeIndex = scanner.nextInt();
                    //service.remove(onlineEvents, oeIndex);
                    onlineEventRepository.deleteById(oeIndex);
                    logger.write("Remove Online Event");
                    break;
                case 21:
                    //service.listPhysicalEvents(physicalEvents);
                    service.listPhysicalEvents(physicalEventRepository.findAll());
                    System.out.println("Select event index:");
                    int peIndex = scanner.nextInt();
                    //service.remove(physicalEvents, peIndex);
                    physicalEventRepository.deleteById(peIndex);
                    logger.write("Remove Physical Event");
                    break;
                case 22:
                    Client updatedClient = service.choseClient(clientRepository.findAll());
                    locationRepository.update(updatedClient.getAddress().getId(), service.generateLocation());
                    logger.write("Update client address");
                    break;
                case 23:
                    Client changeClient = service.choseClient(clientRepository.findAll());
                    System.out.println("Enter new surname:");
                    String surname = scanner.next();
                    clientRepository.updateSurname(changeClient.getId(), surname);
                    logger.write("Change client surname");
                    break;
                case 24:
                    locationRepository.insert(service.generateLocation());
                    logger.write("Insert generated location");
                    break;
                case 25:
                    clientRepository.insert(service.generatePerson());
                    logger.write("Insert generated client");
                    break;
                case 26:
                    onlineEventRepository.insert((OnlineEvent) service.generateEvent(true));
                    logger.write("Insert generated online event");
                    break;
                case 27:
                    physicalEventRepository.insert((PhysicalEvent) service.generateEvent(false));
                    logger.write("Insert generated physical event");
                    break;
                default:
                    System.out.println("Incorrect option! Number must be between 0 and 27");

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
                "21) Remove physical event\n22) Update client address\n23) Change client surname\n" +
                "24) Insert generated location\n25) Insert generated client\n26) Insert generated online event\n" +
                "27) Insert generated physical event\n");
    }
}
