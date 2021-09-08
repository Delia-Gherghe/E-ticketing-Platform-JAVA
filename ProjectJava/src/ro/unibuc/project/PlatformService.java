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
        if (items.size() == 0){
            System.out.println("No such items registered!");
        } else {
            for (T item : items) {
                System.out.println(item);
                System.out.println();
            }
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
            System.out.println(location.getId() + ") St." + location.getStreetName() + " No." + location.getStreetNr()
                    + " " + location.getCity() + ", " + location.getCountry());
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

    private int chooseValidIndex(int upperBound){
        Scanner scanner = new Scanner(System.in);
        int index;
        while (true){
            index = scanner.nextInt();
            if (index >= 0 && index <= upperBound){
                break;
            }
            System.out.println("Index must be between 0 and " + upperBound + ". Please try again!");
        }
        return index;
    }

    public Client choseClient(List<Client> clients){
        showClients(clients);
        System.out.println("Select client index:");
        int clientIndex = chooseValidIndex(clients.size() - 1);
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
        Event chosenEvent;
        System.out.println("Choose Online (O)/Physical (P)");
        String eventType;

        while (true) {
            eventType = scanner.next();
            if (eventType.equals("O")) {
                showOnlineEvents(onlineEvents);
                System.out.println("Select event index:");
                int eventIndex = chooseValidIndex(onlineEvents.size() - 1);
                chosenEvent = onlineEvents.get(eventIndex);
                break;
            }

            if (eventType.equals("P")){
                showPhysicalEvents(physicalEvents);
                System.out.println("Select event index:");
                int eventIndex = chooseValidIndex(physicalEvents.size() - 1) ;
                chosenEvent = physicalEvents.get(eventIndex);
                break;
            }

            System.out.println("Incorrect choice! Please type O or P!");
        }
        return chosenEvent;
    }

    public long remainingDays(Event event){
        LocalDate eventDate = LocalDate.of(event.getDateTime().getYear(), event.getDateTime().getMonth(), event.getDateTime().getDay());
        LocalDate currentDate = LocalDate.now();
        return ChronoUnit.DAYS.between(currentDate, eventDate);
    }

    public Location enterLocation(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter country:");
        String country = scanner.nextLine();
        System.out.print("Enter city:");
        String city = scanner.nextLine();
        System.out.print("Enter street name:");
        String streetName = scanner.nextLine();
        System.out.print("Enter street number:");
        int streetNr = scanner.nextInt();
        return new Location(country, city, streetName, streetNr);
    }


    public OnlineEvent enterOnlineEvent(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name:");
        String name = scanner.nextLine();
        System.out.print("Enter maximum capacity:");
        int capacity = scanner.nextInt();
        System.out.print("Enter type (concert, movie, sport, fashion, theatre, gaming, art):");
        String type = validEventType();
        System.out.print("Enter date (DD/MM/YYYY):");
        String[] date = validDate().split("/");
        System.out.print("Enter time (HH:MM):");
        String[] time = validTime().split(":");
        DateTime dateTime = new DateTime(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0])
                , Integer.parseInt(time[0]), Integer.parseInt(time[1]));
        System.out.print("Enter base price:");
        double basePrice = scanner.nextDouble();
        System.out.print("Enter link:");
        String link = scanner.next();
        return new OnlineEvent(name, capacity, type, dateTime, basePrice, link);
    }

    public PhysicalEvent enterPhysicalEvent(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name:");
        String name = scanner.nextLine();
        System.out.print("Enter maximum capacity:");
        int capacity = scanner.nextInt();
        System.out.print("Enter type (concert, movie, sport, fashion, theatre, gaming, art):");
        String type = validEventType();
        System.out.print("Enter date (DD/MM/YYYY):");
        String[] date = validDate().split("/");
        System.out.print("Enter time (HH:MM):");
        String[] time = validTime().split(":");
        DateTime dateTime = new DateTime(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0])
                , Integer.parseInt(time[0]), Integer.parseInt(time[1]));
        System.out.print("Enter base price:");
        double basePrice = scanner.nextDouble();
        System.out.println("Enter location:");
        Location location = enterLocation();
        return new PhysicalEvent(name, capacity, type, dateTime, basePrice, location);
    }

    public Client enterClient(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name:");
        String name = scanner.nextLine();
        System.out.print("Enter surname:");
        String surname = scanner.nextLine();
        System.out.print("Enter date of birth (DD/MM/YYYY):");
        String[] birthday = validDate().split("/");
        Date date = new Date(Integer.parseInt(birthday[2]), Integer.parseInt(birthday[1]), Integer.parseInt(birthday[0]));
        System.out.println("Enter address:");
        Location location = enterLocation();
        return new Client(name, surname, date, location);
    }

    public String validEventType(){
        Scanner scanner = new Scanner(System.in);
        String type;
        while (true){
            type = scanner.next();
            if (type.equals("concert") || type.equals("movie") || type.equals("sport") || type.equals("fashion") ||
            type.equals("theatre") || type.equals("gaming") || type.equals("art")){
                break;
            }
            System.out.println("Invalid choice! Please select one of the given options!");
        }
        return type;
    }

    public String validDate(){
        Scanner scanner = new Scanner(System.in);
        String date;
        while (true){
            date = scanner.next();
            if (date.length() == 10 && Character.isDigit(date.charAt(0)) && Character.isDigit(date.charAt(1)) &&
                    Character.isDigit(date.charAt(3)) && Character.isDigit(date.charAt(4)) && Character.isDigit(date.charAt(6))
            && Character.isDigit(date.charAt(7)) && Character.isDigit(date.charAt(8)) && Character.isDigit(date.charAt(9)) &&
            date.charAt(2) == '/' && date.charAt(5) == '/'){
                break;
            }
            System.out.println("Invalid date format! Please try again!");
        }
        return date;
    }

    public String validTime(){
        Scanner scanner = new Scanner(System.in);
        String time;
        while (true){
            time = scanner.next();
            if (time.length() == 5 && Character.isDigit(time.charAt(0)) && Character.isDigit(time.charAt(1)) &&
                    Character.isDigit(time.charAt(3)) && Character.isDigit(time.charAt(4)) && time.charAt(2) == ':'){
                break;
            }
            System.out.println("Invalid time format! Please try again!");
        }
        return time;
    }

}
