package ro.unibuc.project.clients;

import ro.unibuc.project.common.DateTime;
import ro.unibuc.project.events.Event;

import java.time.LocalDateTime;
import java.util.Random;

public class Ticket implements Comparable<Ticket>{

    private String id;
    private String ticketType;
    private Event event;

    public Ticket(String ticketType, Event event, String idSuffix) {
        event.boughtTicket();
        this.ticketType = ticketType;
        this.event = event;
        this.id = computeId() + idSuffix;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public double computePrice(){
        switch (this.ticketType.toUpperCase()) {
            case "GENERAL":
                return event.getMinPrice();
            case "EARLY":
                return (double)Math.round(event.getMinPrice() * 0.9 * 100)/100;
            case "VIP":
                return (double)Math.round(event.getMinPrice() * 1.5 * 100)/100;
            default:
                return (double)Math.round(event.getMinPrice() * 1.2 * 100)/100;
        }
    }

    public String computeId(){
        String eventName = event.getName().replace(" ", "");
        Random rand = new Random();
        return eventName.substring(0, eventName.length()/2).toUpperCase() + ticketType.toUpperCase() +
                String.valueOf(rand.nextInt(1000));
    }

    @Override
    public String toString() {
        return "Ticket: " + id + " " + ticketType + "\n" + "Event: " + event.getName() +
                "\nDate: " + event.getDateTime() + "\nPrice: " + computePrice() + " $" +
                "\n----------------------------------------";
    }

    @Override
    public int compareTo(Ticket o) {
        DateTime tdt = this.getEvent().getDateTime();
        DateTime odt = o.getEvent().getDateTime();
        LocalDateTime thisDate = LocalDateTime.of(tdt.getYear(), tdt.getMonth(), tdt.getDay(), tdt.getHour(), tdt.getMinutes());
        LocalDateTime otherDate = LocalDateTime.of(odt.getYear(), odt.getMonth(), odt.getDay(), odt.getHour(), odt.getMinutes());
        if (thisDate.isEqual(otherDate)){
            return 0;
        }
        else if (thisDate.isAfter(otherDate)){
            return 1;
        }
        else {
            return -1;
        }
    }
}
