package hu.domain.event;

import hu.domain.account.Account;

import java.util.Date;
import java.util.List;

public class Complain extends Event {

    private Account account;
    private List<Account> receivers;

    public Complain(String eventName, String description, Date date, Date eventDate, Account account, List<Account> receivers) {
        super(eventName, description, date, eventDate);
        this.account = account;
        this.receivers = receivers;
    }

    public Complain(int id, String eventName, String description, Date date, Date eventDate, Account account, List<Account> receivers) {
        super(id, eventName, description, date, eventDate);
        this.account = account;
        this.receivers = receivers;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Account> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<Account> receivers) {
        this.receivers = receivers;
    }
}
