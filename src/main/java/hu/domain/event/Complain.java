package hu.domain.event;

import java.util.Date;
import java.util.List;

public class Complain extends Event {

    private Integer accountId;
    private List<Integer> recievers;

    public Complain(String eventName, String description, Date date, Date eventDate, Integer accountId, List<Integer> recievers) {
        super(eventName, description, date, eventDate);
        this.accountId = accountId;
        this.recievers = recievers;
    }

    public Complain(int id, String eventName, String description, Date date, Date eventDate, Integer accountId, List<Integer> recievers) {
        super(id, eventName, description, date, eventDate);
        this.accountId = accountId;
        this.recievers = recievers;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public List<Integer> getRecievers() {
        return recievers;
    }

    public void setRecievers(List<Integer> recievers) {
        this.recievers = recievers;
    }
}
