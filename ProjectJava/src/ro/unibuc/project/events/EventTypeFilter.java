package ro.unibuc.project.events;

import ro.unibuc.project.common.Filterable;

import java.util.Arrays;

public class EventTypeFilter implements Filterable<String, Event> {
    @Override
    public Event[] filter(Event[] items, String value) {
        Event[] filteredEvents = new Event[0];
        for (Event event : items) {
            if (event.getType().equals(value)) {
                filteredEvents = Arrays.copyOf(filteredEvents, filteredEvents.length + 1);
                filteredEvents[filteredEvents.length - 1] = event;
            }
        }
        return filteredEvents;
    }
}
