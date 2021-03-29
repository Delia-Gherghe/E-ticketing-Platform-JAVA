package ro.unibuc.project.clients;

import java.time.LocalDate;
import java.util.Comparator;

public class ClientAgeComparator implements Comparator<Client> {
    @Override
    public int compare(Client o1, Client o2) {
        LocalDate o1d = LocalDate.of(o1.getBirthday().getYear(), o1.getBirthday().getMonth(), o1.getBirthday().getDay());
        LocalDate o2d = LocalDate.of(o2.getBirthday().getYear(), o2.getBirthday().getMonth(), o2.getBirthday().getDay());
        return o1d.compareTo(o2d);
    }
}
