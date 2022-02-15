package hu.domain.event;

import hu.domain.account.Account;

import java.util.Date;
import java.util.List;

public class Reminder extends Event {

    private List<Account> affectedFlats;

    public Reminder(String eventName, String description, Date date, List<Account> affectedFlats) {
        super(eventName, description, date);
        this.affectedFlats = affectedFlats;
    }

    public Reminder(int id, String eventName, String description, Date date, List<Account> affectedFlats) {
        super(id, eventName, description, date);
        this.affectedFlats = affectedFlats;
    }
}
