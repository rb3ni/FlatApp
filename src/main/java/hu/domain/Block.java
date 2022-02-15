package hu.domain;

import hu.domain.account.Account;
import hu.domain.space.Space;

import java.util.Date;
import java.util.List;

public class Block {

    private int id;
    private String city;
    private int postalCode;
    private String street;
    private int houseNumber;
    private String description;
    private int numberOfFlats;
    private int numberOfFloors;
    private List<Integer> spaces;
    private List<Integer> accounts;
    private Date paymentDeadline;

    public Block(int id, String city, int postalCode, String street, int houseNumber, String description,
                 int numberOfFlats, int numberOfFloors, List<Integer> spaces, List<Integer> accounts,
                 Date paymentDeadline) {
        this.id = id;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.description = description;
        this.numberOfFlats = numberOfFlats;
        this.numberOfFloors = numberOfFloors;
        this.spaces = spaces;
        this.accounts = accounts;
        this.paymentDeadline = paymentDeadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfFlats() {
        return numberOfFlats;
    }

    public void setNumberOfFlats(int numberOfFlats) {
        this.numberOfFlats = numberOfFlats;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public List<Space> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<Space> spaces) {
        this.spaces = spaces;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Date getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setPaymentDeadline(Date paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }
}
