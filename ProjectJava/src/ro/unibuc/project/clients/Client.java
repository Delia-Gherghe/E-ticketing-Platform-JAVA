package ro.unibuc.project.clients;

import ro.unibuc.project.common.Date;
import ro.unibuc.project.common.Location;

import java.util.Set;
import java.util.TreeSet;

public class Client implements Comparable<Client>{

    private int id;
    private String name;
    private String surname;
    private Date birthday;
    private Set<Ticket> tickets;
    private Location address;

    public Client(String name, String surname, Date birthday, Location address) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.tickets = new TreeSet<>();
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Location getAddress() {
        return address;
    }

    public void setAddress(Location address) {
        this.address = address;
    }


    @Override
    public String toString() {
        String showClient = name + " " + surname + " " + birthday + "\nAddress: " + address + "\n";
        showClient += "Tickets bought: ";
        if (tickets.size() == 0){
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
