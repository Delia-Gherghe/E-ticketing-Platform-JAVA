package ro.unibuc.project.events;

import ro.unibuc.project.common.Filterable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventTypeFilter implements Filterable<String, Event> {
    @Override
    public List<Event> filter(List<Event> items, String value) {
        List<Event> filteredEvents = new ArrayList<>();
        for (Event event : items) {
            if (event.getType().equals(value)) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }
}
