package hu.domain.event;

import java.util.Date;

public class Event {

    private int id;
    private String eventName;
    private String description;
    private Date date;
    private Date eventDate;

    public Event(String eventName, String description, Date date, Date eventDate) {
        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.eventDate = eventDate;
    }

    public Event(int id, String eventName, String description, Date date, Date eventDate) {
        this.id = id;
        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.eventDate = eventDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}
