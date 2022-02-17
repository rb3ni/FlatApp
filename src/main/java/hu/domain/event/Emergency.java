package hu.domain.event;

import hu.domain.account.Account;
import hu.domain.space.Space;
import hu.domain.space.SpaceMod;

import java.util.Date;
import java.util.List;

public class Emergency extends Event {

    private Account account;
    private List<SpaceMod> affectedSpaces;

    public Emergency(String eventName, String description, Date date, Date eventDate, Account account, List<SpaceMod> affectedSpaces) {
        super(eventName, description, date, eventDate);
        this.account = account;
        this.affectedSpaces = affectedSpaces;
    }

    public Emergency(int id, String eventName, String description, Date date, Date eventDate, Account account, List<SpaceMod> affectedSpaces) {
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

    public List<SpaceMod> getAffectedSpaces() {
        return affectedSpaces;
    }

    public void setAffectedSpaces(List<SpaceMod> affectedSpaces) {
        this.affectedSpaces = affectedSpaces;
    }
}
