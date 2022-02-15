package hu.domain.event;

import hu.domain.account.Account;
import hu.domain.account.Habitant;

import java.util.Date;
import java.util.List;

public class Emergency extends Event {

    private Habitant habitant;
    private List<Account> affectedFlats;

    public Emergency(String eventName, String description, Date date, Habitant habitant, List<Account> affectedFlats) {
        super(eventName, description, date);
        this.habitant = habitant;
        this.affectedFlats = affectedFlats;
    }

    public Emergency(int id, String eventName, String description, Date date, Habitant habitant, List<Account> affectedFlats) {
        super(id, eventName, description, date);
        this.habitant = habitant;
        this.affectedFlats = affectedFlats;
    }

    public Emergency(String eventName, String description, Date date, List<Account> affectedFlats) {
        super(eventName, description, date);
        this.affectedFlats = affectedFlats;
    }

    public Emergency(int id, String eventName, String description, Date date, List<Account> affectedFlats) {
        super(id, eventName, description, date);
        this.affectedFlats = affectedFlats;
    }



}
