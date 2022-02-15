package hu.domain.account;

import hu.domain.account.Account;

public class Habitant extends Account {

    private int age;
    private String occupation;

    public Habitant(String name, int phoneNumber, String email, String responsibility, int cost, int age, String occupation) {
        super(name, phoneNumber, email, responsibility, cost);
        this.age = age;
        this.occupation = occupation;
    }

    public Habitant(int id, String name, int phoneNumber, String email, String responsibility, int cost, int age, String occupation) {
        super(id, name, phoneNumber, email, responsibility, cost);
        this.age = age;
        this.occupation = occupation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return "Habitant{" +
                "name: " + getName() +
                '}';
    }
}
