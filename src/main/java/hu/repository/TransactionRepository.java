package hu.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static hu.repository.DatabaseConfigFlatApp.*;

public class TransactionRepository {

    Connection connection;

    public TransactionRepository() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTransactionTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "account_number INT NOT NULL, " +                    //befizető számlaszáma
                "name VARCHAR(50) NOT NULL, " +
                "date DATE NOT NULL, " +
                "time TIME NOT NULL, " +
                "amount INT NOT NULL, " +
                "description VARCHAR(50), " +
                "transaction_number VARCHAR(50) " +
                //foreign key-ek
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCreateTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void readTransactions(String path) {
        Path transactionPath = Path.of(path);
        try (BufferedReader bufferedReader = Files.newBufferedReader(transactionPath)) {
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] transactionData = line.split(",");
                System.out.println(createNewTransaction(transactionData));
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public String createNewTransaction(String[] transactionData) {
        String infoBack = "Transaction save failed";
        String insertTransactionStatement = "INSERT INTO transactions " +
                "(account_number, name, date, time, amount, description, transaction_number)" +
                " VALUES (?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertTransactionStatement)) {

            int accountNumber = Integer.parseInt(transactionData[0]);
            java.sql.Date date = (java.sql.Date) dateFormatter(transactionData[2]);
            java.sql.Time time = timeFormatter(transactionData[2]);
            String transactionNumber = transactionData[5];

            if (isDuplicate(accountNumber, date, time, transactionNumber)) {

                preparedStatement.setInt(1, accountNumber);
                preparedStatement.setString(2, transactionData[1]);
                preparedStatement.setDate(3, date);
                preparedStatement.setTime(4, time);
                preparedStatement.setInt(5, Integer.parseInt(transactionData[3]));
                preparedStatement.setString(6, transactionData[4]);
                preparedStatement.setString(7, transactionNumber);
                infoBack = "Transaction saved";
                preparedStatement.executeUpdate();
            } else {
                infoBack = "Transaction already saved";
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return infoBack;
    }

    private boolean isDuplicate(int accountNumber, Date date, Time time, String transactionNumber) {
        String sqlCheck = "SELECT * FROM transactions WHERE account_number = ? AND date = ? AND time = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCheck)) {
            preparedStatement.setInt(1, accountNumber);
            preparedStatement.setDate(2, date);
            preparedStatement.setTime(3, time);
            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                return !(transactionNumber.equals(resultSet.getString("transaction_number")));
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
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


}
