package hu.domain.account;

public class Account {
    private int id;
    private String name;
    private int phoneNumber;
    private String email;
    private String responsibility;
    private int cost;

    public Account(String name, int phoneNumber, String email, String responsibility, int cost) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.responsibility = responsibility;
        this.cost = cost;
    }

    public Account(int id, String name, int phoneNumber, String email, String responsibility, int cost) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.responsibility = responsibility;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
