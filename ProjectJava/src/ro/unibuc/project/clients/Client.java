package ro.unibuc.project.clients;

import ro.unibuc.project.common.Date;
import ro.unibuc.project.common.Location;
import ro.unibuc.project.events.Event;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class Client implements Comparable<Client>{

    private String name;
    private String surname;
    private Date birthday;
    private Ticket[] tickets;
    private Location address;

    public Client(String name, String surname, Date birthday, Location address) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.tickets = new Ticket[0];
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Ticket[] getTickets() {
        return tickets;
    }

    public void setTickets(Ticket[] tickets) {
        this.tickets = tickets;
    }

    public Location getAddress() {
        return address;
    }

    public void setAddress(Location address) {
        this.address = address;
    }

    public void buyTicket(Event e, String ticketType){
        tickets = Arrays.copyOf(tickets, tickets.length + 1);
        tickets[tickets.length - 1] = new Ticket(ticketType, e, String.valueOf(name.charAt(0)) + String.valueOf(surname.charAt(0)));
    }

    public double totalMoneySpent(){
        double amount = 0;
        for (int i = 0; i < tickets.length; i++){
            amount += tickets[i].computePrice();
        }

        return amount;
    }

    public long age(){
        LocalDate bday = LocalDate.of(birthday.getYear(), birthday.getMonth(), birthday.getDay());
        LocalDate currentDate = LocalDate.now();
        return ChronoUnit.YEARS.between(bday, currentDate);
    }

    @Override
    public String toString() {
        String showClient = name + " " + surname + " " + birthday + "\nAddress: " + address + "\n";
        showClient += "Tickets bought: ";
        if (tickets.length == 0){
            showClient += "No tickets bought yet";
        }
        else {
            showClient += "\n----------------------------------------\n";
            for (Ticket ticket : tickets) {
                showClient += ticket.toString() + "\n";
            }
        }
        return showClient;
    }

    @Override
    public int compareTo(Client o) {
        if (this.surname.equals(o.surname)){
            return this.name.compareTo(o.name);
        }
        else {
            return this.surname.compareTo(o.surname);
        }
    }
}
