package hu.domain.event;

import hu.domain.space.Space;

import java.util.Date;
import java.util.List;

public class Reminder extends Event {

    private List<Space> affectedSpaces;

    public Reminder(String eventName, String description, Date date, Date dateEvent, List<Space> affectedSpaces) {
        super(eventName, description, date, dateEvent);
        this.affectedSpaces = affectedSpaces;
    }

    public Reminder(int id, String eventName, String description, Date date, Date eventDate, List<Space> affectedSpaces) {
        super(id, eventName, description, date, eventDate);
        this.affectedSpaces = affectedSpaces;
    }

    public List<Space> getAffectedSpaces() {
        return affectedSpaces;
    }

    public void setAffectedSpaces(List<Space> affectedSpaces) {
        this.affectedSpaces = affectedSpaces;
    }
}
