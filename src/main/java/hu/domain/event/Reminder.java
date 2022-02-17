package hu.domain.event;

import hu.domain.account.Account;
import hu.domain.space.Space;
import hu.domain.space.SpaceMod;

import java.util.Date;
import java.util.List;

public class Reminder extends Event {

    private List<SpaceMod> affectedSpaces;

    public Reminder(String eventName, String description, Date date, Date dateEvent, List<SpaceMod> affectedSpaces) {
        super(eventName, description, date, dateEvent);
        this.affectedSpaces = affectedSpaces;
    }

    public Reminder(int id, String eventName, String description, Date date, Date eventDate, List<SpaceMod> affectedSpaces) {
        super(id, eventName, description, date, eventDate);
        this.affectedSpaces = affectedSpaces;
    }

    public List<SpaceMod> getAffectedSpaces() {
        return affectedSpaces;
    }

    public void setAffectedSpaces(List<SpaceMod> affectedSpaces) {
        this.affectedSpaces = affectedSpaces;
    }
}
