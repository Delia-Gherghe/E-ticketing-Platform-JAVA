package ro.unibuc.project.events;

import ro.unibuc.project.common.DateTime;

public class OnlineEvent extends Event{

    private String link;

    public OnlineEvent(String name, int maxPeople, String type, DateTime dateTime, double minPrice, String link) {
        super(name, maxPeople, type, dateTime, minPrice);
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return super.toString() + "\nLink: " + link;
    }
}
