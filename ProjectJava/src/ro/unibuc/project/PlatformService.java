package ro.unibuc.project;

import ro.unibuc.project.clients.*;
import ro.unibuc.project.events.*;
import ro.unibuc.project.common.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class PlatformService {

    static String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";

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

    public Event generateEvent(){
        String[] eventTypes = {"concert", "movie", "sport", "fashion", "theatre", "gaming", "art"};
        Random rand = new Random();
        String eventType = eventTypes[rand.nextInt(eventTypes.length)];
        boolean isOnline = rand.nextBoolean();
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

    public Client[] initClients(int n){
        Client[] clients = new Client[n];
        for (int i = 0; i < n; i++){
            clients[i] = generatePerson();
        }
        return clients;
    }

    public Event[] initEvents(int n){
        Event[] events = new Event[n];
        for (int i = 0; i < n; i++){
            events[i] = generateEvent();
        }
        return events;
    }

    public void sortClientsAge(Client[] clients){
        Arrays.sort(clients, new ClientAgeComparator());
    }

    public void sortClientsNrTickets(Client[] clients){
        Arrays.sort(clients, new ClientNrTicketsComparator());
    }

    public void sortClientsNrTicketsAge(Client[] clients){
        Arrays.sort(clients, new ClientAgeComparator());
        Arrays.sort(clients, new ClientNrTicketsComparator());
    }

    public Event[] filter(Filterable<String, Event> filterable, Event[] events, String value) {
        return filterable.filter(events, value);
    }

    public Client[] filter(Filterable<Long, Client> filterable, Client[] clients, Long value) {
        return filterable.filter(clients, value);
    }

    public <T> void display(T[] items){
        for (T item : items){
            System.out.println(item);
            System.out.println();
        }
    }

    public void buyMultipleTickets(Client client, Event[] events){
        ArrayList<Integer> eventIndex = new ArrayList<>();
        for (int i = 0; i < events.length; i++){
            eventIndex.add(i);
        }
        Collections.shuffle(eventIndex);
        Random rand = new Random();
        for (int i = 0; i < rand.nextInt(events.length); i++){
            client.buyTicket(events[eventIndex.get(i)], generateRandomTicketType());
        }

    }
}
