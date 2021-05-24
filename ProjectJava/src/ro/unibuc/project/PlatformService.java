package ro.unibuc.project;

import ro.unibuc.project.clients.*;
import ro.unibuc.project.common.Date;
import ro.unibuc.project.database.repository.OnlineEventRepository;
import ro.unibuc.project.database.repository.PhysicalEventRepository;
import ro.unibuc.project.database.repository.TicketRepository;
import ro.unibuc.project.events.*;
import ro.unibuc.project.common.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class PlatformService {

    private static String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";

    public Location generateLocation(){
        String[] cities = {"Bucharest", "Rome", "Monte Carlo", "Moscow", "Miami", "Paris", "London", "Madrid",
                "New York", "Belgrade", "Melbourne", "Beijing", "Tokyo", "Indian Wells", "Cincinnati",
                "Shanghai", "Montreal", "Toronto", "Rotterdam", "Sofia", "Hamburg", "Budapest", "Brasilia",
                "Dubai", "Seoul", "Istanbul", "Sydney", "Los Angeles", "Buenos Aires", "Mexico City"};
        String[] countries = {"Romania", "Italy", "Monaco", "Russia", "USA", "France", "UK", "Spain", "USA", "Serbia",
                "Australia", "China", "Japan", "USA", "USA", "China", "Canada", "Canada", "Netherlands",
                "Bulgaria", "Germany", "Hungary", "Brazil", "UAE", "South Korea", "Turkey",
                "Australia", "USA", "Argentina", "Mexico"};
        Random rand = new Random();
        int streetLength = rand.nextInt(8) + 1;
        StringBuilder street = new StringBuilder();
        street.append(upperAlphabet.charAt(rand.nextInt(upperAlphabet.length())));
        for (int i = 0; i < streetLength; i++){
            street.append(lowerAlphabet.charAt(rand.nextInt(lowerAlphabet.length())));
        }
        int streetNumber = rand.nextInt(100);
        int place = rand.nextInt(cities.length);
        return new Location(countries[place], cities[place], street.toString(), streetNumber);

    }

    public Date generateDate(boolean isDateTime){
        Random rand = new Random();
        int dateMonth = rand.nextInt(12) + 1;
        Integer[] longMonths = {1, 3, 5, 7, 8, 10, 12};
        int dateDay;
        if (dateMonth == 2){
            dateDay = rand.nextInt(28) + 1;
        }
        else if (Arrays.asList(longMonths).contains(dateMonth)){
            dateDay = rand.nextInt(31) + 1;
        }
        else{
            dateDay = rand.nextInt(30) + 1;
        }

        int dateYear;

        if (isDateTime){
            int dateHour = rand.nextInt(14) + 10;
            int dateMinutes = rand.nextInt(6) * 10;
            dateYear = rand.nextInt(3) + 2022;
            return new DateTime(dateYear, dateMonth, dateDay, dateHour, dateMinutes);
        }

        dateYear = rand.nextInt(40) + 1970;

        return new Date(dateYear, dateMonth, dateDay);
    }

    public Client generatePerson(){
        String[] firstNames = {"Nazar", "Gwenllian", "Georgette", "Tahmid", "Izabel", "Arwa", "Osanne", "Štefa",
                "Dilwyn", "Yên", "Vihaan", "Rivka", "Tielo", "Heike", "Pauline", "Novak", "Jannik",
                "Andy", "Serena", "Andrey", "Irvine", "Oliver", "Chinweuba", "Arnt", "Krister",
                "Hayati", "Mina", "Mbali", "Priskilla", "Lucanus"};
        String[] lastNames = {"Riddle", "Williams", "Browning", "Huerta", "Greene", "Braun", "Vega", "Phillips",
                "Rublev", "Yates", "Valenzuela", "Herrera", "Vaughn", "Frey", "Djokovic", "Rowland",
                "Gallegos", "Estrada", "Sinner", "Zamora", "Robertson", "Murray", "Bender", "Harding",
                "Blackburn", "Beck", "Watkins", "Brewer", "Walters", "Kirk"};
        Random rand = new Random();
        int posFirstName = rand.nextInt(firstNames.length);
        int posLastName = rand.nextInt(lastNames.length);
        return new Client(firstNames[posFirstName], lastNames[posLastName], generateDate(false), generateLocation());
    }

    public String generateLink(Event e){
        Random rand = new Random();
        int idLength = rand.nextInt(5) + 3;
        StringBuilder linkId = new StringBuilder();
        for (int i = 0; i < idLength; i++){
            boolean isUpper = rand.nextBoolean();
            if (isUpper){
                linkId.append(upperAlphabet.charAt(rand.nextInt(upperAlphabet.length())));
            }
            else {
                linkId.append(lowerAlphabet.charAt(rand.nextInt(lowerAlphabet.length())));
            }
        }
        String formatName = e.getName().toLowerCase().replace(" ", "");
        return "www." + formatName + "/" + linkId + "/" + rand.nextInt(10000) + ".com";

    }

    public Event generateEvent(boolean isOnline){
        String[] eventTypes = {"concert", "movie", "sport", "fashion", "theatre", "gaming", "art"};
        Random rand = new Random();
        String eventType = eventTypes[rand.nextInt(eventTypes.length)];
        double price = (rand.nextInt(210) + 3) * 10 + (double)Math.round(rand.nextDouble() * 10)/10;
        if (isOnline){
            String eventName = eventType.substring(0, 1).toUpperCase() + eventType.substring(1)
                    + " online " + (rand.nextInt(100000) + 10);
            OnlineEvent newEvent = new OnlineEvent(eventName, rand.nextInt(10000) + 1000, eventType,
                    (DateTime)generateDate(true), price, "");
            newEvent.setLink(generateLink(newEvent));
            return newEvent;
        }
        else {
            Location location = generateLocation();
            String eventName = eventType.substring(0, 1).toUpperCase() + eventType.substring(1) + " " + location.getCity() +
                    " " + (rand.nextInt(100000) + 10);
            return new PhysicalEvent(eventName, rand.nextInt(10000) + 1000, eventType,
                    (DateTime)generateDate(true), price, location);
        }
    }

    public String generateRandomTicketType(){
        String[] eventTypes = {"VIP", "GENERAL", "EARLY"};
        Random rand = new Random();
        return eventTypes[rand.nextInt(eventTypes.length)];
    }

    public List<Client> initClients(int n){
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < n; i++){
            clients.add(generatePerson());
        }
        return clients;
    }

    public List<Event> initEvents(int n){
        List<Event> events = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < n; i++){
            events.add(generateEvent(rand.nextBoolean()));
        }
        return events;
    }

    public void sortClientsAge(List<Client> clients){
        clients.sort(new ClientAgeComparator());
    }

    public void sortClientsNrTickets(List<Client> clients){
        clients.sort(new ClientNrTicketsComparator());
    }

    public void sortClientsNrTicketsAge(List<Client> clients){
        clients.sort(new ClientAgeComparator());
        clients.sort(new ClientNrTicketsComparator());
    }

    public List<Event> filter(Filterable<String, Event> filterable, List<Event> events, String value) {
        return filterable.filter(events, value);
    }

    public List<Client> filter(Filterable<Long, Client> filterable, List<Client> clients, Long value) {
        return filterable.filter(clients, value);
    }

    public <T> void display(List<T> items){
        for (T item : items){
            System.out.println(item);
            System.out.println();
        }
    }

    public <T> void remove(List<T> items, int index){
        items.remove(index);
    }

    public void buyMultipleTickets(Client client, List<Event> events){
        ArrayList<Integer> eventIndex = new ArrayList<>();
        for (int i = 0; i < events.size(); i++){
            eventIndex.add(i);
        }
        Collections.shuffle(eventIndex);
        Random rand = new Random();
        for (int i = 0; i < rand.nextInt(events.size()); i++){
            buyTicket(client, events.get(eventIndex.get(i)), generateRandomTicketType());
        }

    }

    public void buyTicket(Client c, Event e, String ticketType){
        Ticket newTicket = new Ticket(ticketType, e, c.getName().charAt(0) + String.valueOf(c.getSurname().charAt(0)));
        TicketRepository ticketRepository = new TicketRepository();
        ticketRepository.insert(c.getId(), newTicket);
        if (e instanceof OnlineEvent){
            OnlineEventRepository onlineEventRepository = new OnlineEventRepository();
            onlineEventRepository.updateMaxPeople(e.getId());
        } else {
            PhysicalEventRepository physicalEventRepository = new PhysicalEventRepository();
            physicalEventRepository.updateMaxPeople(e.getId());
        }
        //c.getTickets().add(newTicket);
    }

    public double totalMoneySpent(Client c){
        double amount = 0;
        for (Ticket ticket : c.getTickets()){
            amount += ticket.computePrice();
        }

        return amount;
    }

    public long age(Client c){
        LocalDate birthday = LocalDate.of(c.getBirthday().getYear(), c.getBirthday().getMonth(), c.getBirthday().getDay());
        LocalDate currentDate = LocalDate.now();
        return ChronoUnit.YEARS.between(birthday, currentDate);
    }

    public List<Event> combineEvents(List<OnlineEvent> onlineEvents, List<PhysicalEvent> physicalEvents){
        List<Event> allEvents = new ArrayList<>();
        allEvents.addAll(onlineEvents);
        allEvents.addAll(physicalEvents);
        return allEvents;
    }

    public void listLocations(List<Location> locations){
        for (Location location : locations){
            System.out.println(location.getId() + ") " + location.getCountry() + " " + location.getCity() + " " +
                    location.getStreetName() + " " + location.getStreetNr());
        }
    }

    public void listClients(List<Client> clients){
        for (Client client : clients){
            System.out.println(client.getId() + ") " + client.getName() + " " + client.getSurname() + " " + client.getBirthday());
        }
    }

    public void listOnlineEvents(List<OnlineEvent> onlineEvents){
        for (OnlineEvent onlineEvent : onlineEvents){
            System.out.println(onlineEvent.getId() + ") " + onlineEvent.getName() + " (" +
                    onlineEvent.getType() + ") " + onlineEvent.getDateTime());
        }
    }

    public void listPhysicalEvents(List<PhysicalEvent> physicalEvents){
        for (PhysicalEvent pe : physicalEvents){
            System.out.println(pe.getId() + ") " + pe.getName() + " (" + pe.getType() + ") " + pe.getDateTime() + " " +
                    pe.getLocation().getCity() + ", " + pe.getLocation().getCountry());
        }
    }


    public Client choseClient(List<Client> clients){
        Scanner scanner = new Scanner(System.in);
        showClients(clients);
        System.out.println("Select client index:");
        int clientIndex = scanner.nextInt();
        return clients.get(clientIndex);
    }

    private void showClients(List<Client> clients){
        for (int i = 0; i < clients.size(); i++){
            Client client = clients.get(i);
            System.out.println(i + ") " + client.getName() + " " + client.getSurname() + " " + client.getBirthday());
        }
    }

    private void showOnlineEvents(List<OnlineEvent> onlineEvents){
        for (int i = 0; i < onlineEvents.size(); i++){
            OnlineEvent onlineEvent = onlineEvents.get(i);
            System.out.println(i + ") " + onlineEvent.getName() + " (" +
                    onlineEvent.getType() + ") " + onlineEvent.getDateTime());
        }
    }

    private void showPhysicalEvents(List<PhysicalEvent> physicalEvents){
        for (int i = 0; i < physicalEvents.size(); i++){
            PhysicalEvent pe = physicalEvents.get(i);
            System.out.println(i + ") " + pe.getName() + " (" + pe.getType() + ") " + pe.getDateTime() + " " +
                    pe.getLocation().getCity() + ", " + pe.getLocation().getCountry());
        }
    }

    public Event chooseEvent(List<OnlineEvent> onlineEvents, List<PhysicalEvent> physicalEvents){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose Online (O)/Physical (P)");
        String eventType = scanner.next();
        Event chosenEvent;
        if (eventType.equals("O")){
            showOnlineEvents(onlineEvents);
            System.out.println("Select event index:");
            int eventIndex = scanner.nextInt();
            chosenEvent = onlineEvents.get(eventIndex);
        } else {
            showPhysicalEvents(physicalEvents);
            System.out.println("Select event index:");
            int eventIndex = scanner.nextInt();
            chosenEvent = physicalEvents.get(eventIndex);
        }
        return chosenEvent;
    }

    public long remainingDays(Event event){
        LocalDate eventDate = LocalDate.of(event.getDateTime().getYear(), event.getDateTime().getMonth(), event.getDateTime().getDay());
        LocalDate currentDate = LocalDate.now();
        return ChronoUnit.DAYS.between(currentDate, eventDate);
    }

}
