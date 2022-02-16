package hu.domain.event;

import java.util.Date;
import java.util.List;

public class Emergency extends Event {

    private Integer accountId;
    private List<Integer> affectedSpaces;

    public Emergency(String eventName, String description, Date date, Date eventDate, Integer accountId, List<Integer> affectedSpaces) {
        super(eventName, description, date, eventDate);
        this.accountId = accountId;
        this.affectedSpaces = affectedSpaces;
    }

    public Emergency(int id, String eventName, String description, Date date, Date eventDate, Integer accountId, List<Integer> affectedSpaces) {
        super(id, eventName, description, date, eventDate);
        this.accountId = accountId;
        this.affectedSpaces = affectedSpaces;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public List<Integer> getAffectedSpaces() {
        return affectedSpaces;
    }

    public void setAffectedSpaces(List<Integer> affectedSpaces) {
        this.affectedSpaces = affectedSpaces;
    }
}
