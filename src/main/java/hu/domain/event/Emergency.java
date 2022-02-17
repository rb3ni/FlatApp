package hu.domain.event;

import hu.domain.account.Account;
import hu.domain.space.Space;

import java.util.Date;
import java.util.List;

public class Emergency extends Event {

    private Account account;
    private List<Space> affectedSpaces;

    public Emergency(String eventName, String description, Date date, Date eventDate, Account account, List<Space> affectedSpaces) {
        super(eventName, description, date, eventDate);
        this.account = account;
        this.affectedSpaces = affectedSpaces;
    }

    public Emergency(int id, String eventName, String description, Date date, Date eventDate, Account account, List<Space> affectedSpaces) {
        super(id, eventName, description, date, eventDate);
        this.account = account;
        this.affectedSpaces = affectedSpaces;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Space> getAffectedSpaces() {
        return affectedSpaces;
    }

    public void setAffectedSpaces(List<Space> affectedSpaces) {
        this.affectedSpaces = affectedSpaces;
    }
}
