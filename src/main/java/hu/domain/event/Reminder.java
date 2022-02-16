package hu.domain.event;

import hu.domain.account.Account;

import java.util.Date;
import java.util.List;

public class Reminder extends Event {

    private List<Integer> affectedSpaces;

    public Reminder(String eventName, String description, Date date, Date dateEvent, List<Integer> affectedSpaces) {
        super(eventName, description, date, dateEvent);
        this.affectedSpaces = affectedSpaces;
    }

    public Reminder(int id, String eventName, String description, Date date, Date eventDate, List<Integer> affectedSpaces) {
        super(id, eventName, description, date, eventDate);
        this.affectedSpaces = affectedSpaces;
    }
}
