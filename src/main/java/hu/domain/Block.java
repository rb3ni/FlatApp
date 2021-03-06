package hu.domain;

import hu.domain.space.Space;
import hu.repository.BlockRepository;
import hu.repository.TransactionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Block {

    BlockRepository blockRepository = new BlockRepository();
    TransactionRepository transactionRepository = new TransactionRepository();

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
    private Date startingDate;

    public Block(String city, int postalCode, String street, int houseNumber, String description, int numberOfFlats, int numberOfFloors, List<Integer> spaces, List<Integer> accounts, Date paymentDeadline, Date startingDate) {
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
        this.startingDate = startingDate;
    }

    public Block(int id, String city, int postalCode, String street, int houseNumber, String description, int numberOfFlats, int numberOfFloors, List<Integer> spaces, List<Integer> accounts, Date paymentDeadline, Date startingDate) {
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
        this.startingDate = startingDate;
    }


    public void updateTransactions(String path) {

        java.sql.Date previousDeadline = transactionRepository.getDeadline();
        java.sql.Date currentDate;
        LocalDate localDate = LocalDate.now();
        currentDate = java.sql.Date.valueOf(localDate);
        java.sql.Date updatedDate = deadlineChecker(currentDate);

        if (!previousDeadline.equals(updatedDate)) {
            blockRepository.deadlineSetter(updatedDate,1);
            blockRepository.newMonthCostUpdate(1);
        }
            //"src/main/resources/Transactions22_02.csv"
            transactionRepository.readTransactions(path);
    }
    //TODO reminderek k??ld??se - m??sik method
    //TODO elmaradottak ??sszeszed??se


    private java.sql.Date deadlineChecker(java.sql.Date currentDate) {

        java.sql.Date deadline = transactionRepository.getDeadline();

        if (deadline.compareTo(currentDate) < 0) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(deadline);
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
            java.util.Date newDeadline = calendar.getTime();

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = format1.format(newDeadline);
            LocalDate newDeadlineLocal = LocalDate.parse(formattedDate, formatter);
            deadline = java.sql.Date.valueOf(newDeadlineLocal);
        }

        return deadline;
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

    public List<Integer> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<Integer> spaces) {
        this.spaces = spaces;
    }

    public List<Integer> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Integer> accounts) {
        this.accounts = accounts;
    }

    public Date getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setPaymentDeadline(Date paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    public Date getStartingDate() {
        return startingDate;
    }
}
