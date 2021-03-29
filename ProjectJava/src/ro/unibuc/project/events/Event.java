package ro.unibuc.project.events;

import ro.unibuc.project.common.DateTime;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Event {

    protected String name;
    protected int maxPeople;
    protected String type;
    protected DateTime dateTime;
    protected double minPrice;

    public Event(String name, int maxPeople, String type, DateTime dateTime, double minPrice) {
        this.name = name;
        this.maxPeople = maxPeople;
        this.type = type;
        this.dateTime = dateTime;
        this.minPrice = minPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public long remainingDays(){
        LocalDate eventDate = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
        LocalDate currentDate = LocalDate.now();
        return ChronoUnit.DAYS.between(currentDate, eventDate);
    }

    public void boughtTicket(){
        if (maxPeople > 0){
            maxPeople--;
        } else {
            System.out.println("No more tickets available for the event " + name);
        }
    }

    @Override
    public String toString() {
        return name + " (" + type + ")\n" + "Remaining tickets: " + maxPeople +
                "\nDate and Time: " + dateTime.toString() + "\nBase Price: " + minPrice + " $";
    }
}
