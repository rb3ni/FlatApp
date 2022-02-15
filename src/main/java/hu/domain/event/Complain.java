package hu.domain.event;

import hu.domain.account.Account;
import hu.domain.account.Habitant;

import java.util.Date;
import java.util.List;

public class Complain extends Event {

    private Habitant habitant;
    private List<Account> recievers;

    public Complain(String eventName, String description, Date date, Habitant habitant, List<Account> recievers) {
        super(eventName, description, date);
        this.habitant = habitant;
        this.recievers = recievers;
    }

    public Complain(int id, String eventName, String description, Date date, Habitant habitant, List<Account> recievers) {
        super(id, eventName, description, date);
        this.habitant = habitant;
        this.recievers = recievers;
    }

    public Habitant getHabitant() {
        return habitant;
    }

    public void setHabitant(Habitant habitant) {
        this.habitant = habitant;
    }

    public List<Account> getRecievers() {
        return recievers;
    }

    public void setRecievers(List<Account> recievers) {
        this.recievers = recievers;
    }
}
