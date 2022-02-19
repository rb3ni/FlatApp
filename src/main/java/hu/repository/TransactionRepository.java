package hu.repository;

import hu.domain.space.Space;
import hu.repository.accountRepositories.AccountRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static hu.repository.DatabaseConfigFlatApp.*;

public class TransactionRepository {

    Connection connection;
    Connection connection1;

    public TransactionRepository() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            this.connection1 = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTransactionTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "account_number INT NOT NULL, " +   //befizető számlaszáma
                "name VARCHAR(50) NOT NULL, " +
                "date DATE NOT NULL, " +
                "time TIME NOT NULL, " +
                "amount INT NOT NULL, " +
                "description VARCHAR(50), " +
                "transaction_number VARCHAR(50), " +
                "account_id INT, " +
                "FOREIGN KEY (account_id) REFERENCES account(id) " +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCreateTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void readTransactions(String path) {
        List<Integer> unassertedTransactionIds = new ArrayList<>();

        Path transactionPath = Path.of(path);
        try (BufferedReader bufferedReader = Files.newBufferedReader(transactionPath)) {
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] transactionData = line.split(",");
                System.out.println(createNewTransaction(transactionData));

                ////////////////////////
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public String createNewTransaction(String[] transactionData) {
        String infoBack = "Issue with creating new transaction";
        int accountNumber = Integer.parseInt(transactionData[0]);
        Date date = (java.sql.Date) dateFormatter(transactionData[2]);
        Time time = timeFormatter(transactionData[2]);
        String transactionNumber = transactionData[5];

        if (isNotDuplicate(transactionNumber)) {
            System.out.println(transactionCreateHelper(transactionData));
            infoBack = "Transaction " + transactionData[5] + " saved";
        } else {
            infoBack = "Transaction " + transactionData[5] + " already saved";
        }

        return infoBack;
    }

    private String transactionCreateHelper(String[] transactionData) {

        String insertTransactionStatement = "INSERT INTO transactions " +
                "(account_number, name, date, time, amount, description, transaction_number, account_id)" +
                " VALUES (?,?,?,?,?,?,?,?)";
        String infoBack = "Issue with insertion";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertTransactionStatement)) {

            preparedStatement.setInt(1, Integer.parseInt(transactionData[0]));
            preparedStatement.setString(2, transactionData[1]);
            preparedStatement.setDate(3, (java.sql.Date) dateFormatter(transactionData[2]));
            preparedStatement.setTime(4, timeFormatter(transactionData[2]));
            preparedStatement.setInt(5, Integer.parseInt(transactionData[3]));
            preparedStatement.setString(6, transactionData[4]);
            preparedStatement.setString(7, transactionData[5]);
            Integer accountId = assignAccount(transactionData[4], Integer.parseInt(transactionData[3]));
            if (accountId != null) {
                preparedStatement.setInt(8, accountId);
            } else {
                preparedStatement.setNull(8, Types.INTEGER);
            }

            infoBack = "Transaction created";
            preparedStatement.executeUpdate();

        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return infoBack;
    }

    private boolean isNotDuplicate(String transactionNumber) {
        boolean isNotDuplicate = true;
        String sqlCheck = "SELECT * FROM transactions WHERE transaction_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCheck)) {
            preparedStatement.setString(1, transactionNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isNotDuplicate = false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isNotDuplicate;
    }

    private java.util.Date dateFormatter(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dt = LocalDateTime.parse(date, formatter);

        return java.sql.Date.valueOf(dt.toLocalDate());
    }

    private java.sql.Time timeFormatter(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dt = LocalDateTime.parse(date, formatter);

        return java.sql.Time.valueOf(dt.toLocalTime());
    }

    private Integer assignAccount(String transactionText, int cost) {

        AccountRepository accountRepository = new AccountRepository();
        List<Integer> idList = accountRepository.accountIdList();
        Integer accountIdFound = null;

        //TODO név alapú keresés is

        for (Integer integer : idList) {
            if (transactionText.contains(integer.toString())) {
                accountIdFound = integer;
                balanceUpdate(accountIdFound, cost);
                //TODO itt száll el
                break;
            }
        }
        return accountIdFound;
    }

    public List<String> unassignedTransactions() {

        List<String> transactionNumbers = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("account_id") == null) {
                    transactionNumbers.add(
                            resultSet.getString("transaction_number"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return transactionNumbers;
    }

    public Date getDeadline() {
        Date date = null;
        String sql = "SELECT * FROM block WHERE id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                date = resultSet.getDate("payment_deadline");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return date;
    }

    private void balanceUpdate(int accountId, int cost) {

        List<Integer> spaceIds = new ArrayList<>();
        List<Integer> balances = new ArrayList<>();
        List<Integer> costs = new ArrayList<>();

        String sql = "SELECT pt.account_id AS account_id, s.id AS space_id, st.cost, s.balance " +
                "FROM property_table pt " +
                "JOIN space s ON s.id = pt.space_id " +
                "JOIN space_type st ON st.space_type = s.space_type " +
                "WHERE pt.account_id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                spaceIds.add(resultSet.getInt("space_id"));
                balances.add(resultSet.getInt("balance"));
                costs.add(resultSet.getInt("cost"));
            }

            balanceUpdateDecider(spaceIds, balances, costs, cost);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void balanceUpdateDecider(List<Integer> spaceIds, List<Integer> balances, List<Integer> costs, int cost) {

        int remainingCost = cost;
        int costToAdd = 0;
        boolean overPayment = false;
        while (remainingCost != 0) {

            for (int i = 0; i < spaceIds.size(); i++) {
                if (balances.get(i) <= 0 && !overPayment) {
                    if (costs.get(i) < remainingCost) {
                        costToAdd = costs.get(i);
                    } else {
                        costToAdd = remainingCost;
                    }
                    balanceSetter(spaceIds.get(i), costToAdd, balances.get(i));
                    remainingCost -= costToAdd;
                }
            }
            overPayment = true;
        }
    }

    private void balanceSetter(int spaceId, int cost, int balance) {
        String overwriteStatement = "UPDATE space " +
                "SET balance = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(overwriteStatement)) {
            preparedStatement.setInt(1, balance + cost);
            preparedStatement.setInt(2, spaceId);
            preparedStatement.executeUpdate();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }

// TODO public assignAccountManuallyByTransactionNumber

}


