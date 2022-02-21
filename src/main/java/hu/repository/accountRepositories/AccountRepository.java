package hu.repository.accountRepositories;

import hu.domain.account.Account;
import hu.domain.account.ExternalService;
import hu.domain.account.Habitant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static hu.repository.DatabaseConfigFlatApp.*;

public class AccountRepository {

    Connection connection;

    public AccountRepository() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createAccountTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS account (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, " +
                "phone_number INT, " +
                "email VARCHAR(50) NOT NULL, " +
                "responsibility VARCHAR(50), " +
                "cost INT, " +
                "is_habitant BOOLEAN NOT NULL, " +
                "occupation VARCHAR(100), " +
                "age INT, " +
                "company_name VARCHAR(30));";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCreateTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String createNewAccount(Account account) {
        String infoBack = "Account can not be created";
        String insertAccountStatement = "INSERT INTO account VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertAccountStatement)) {

            int id = generateId();
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, account.getName());
            preparedStatement.setInt(3, account.getPhoneNumber());
            preparedStatement.setString(4, account.getEmail());
            preparedStatement.setString(5, account.getResponsibility());
            preparedStatement.setInt(6, account.getCost());

            if (account instanceof Habitant) {
                preparedStatement.setBoolean(7, true);
                preparedStatement.setString(8, ((Habitant) account).getOccupation());
                preparedStatement.setInt(9, ((Habitant) account).getAge());
                preparedStatement.setString(10, null);
            } else {
                preparedStatement.setBoolean(7, false);
                preparedStatement.setString(8, null);
                preparedStatement.setNull(9, Types.INTEGER);
                preparedStatement.setString(10, ((ExternalService) account).getCompanyName());
            }
            preparedStatement.executeUpdate();
            infoBack = "Account " + id + " created";
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return infoBack;
    }

    private int generateId() {
        Random random = new Random();
        int id = 100000 + random.nextInt(899999);

        if (searchAccountById(id) != null) {
            while (searchAccountById(id) != null) {
                id = 100000 + random.nextInt(899999);
            }
        }
        return id;
    }

    public Account searchAccountById(int id) {
        Account account = null;
        String sql = "SELECT * FROM account a\n" +
                "WHERE a.id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getBoolean("is_habitant")) {
                    account = new Habitant(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("phone_number"),
                            resultSet.getString("email"),
                            resultSet.getString("responsibility"),
                            resultSet.getInt("cost"),
                            resultSet.getInt("age"),
                            resultSet.getString("occupation"));
                } else {
                    account = new ExternalService(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("phone_number"),
                            resultSet.getString("email"),
                            resultSet.getString("responsibility"),
                            resultSet.getInt("cost"),
                            resultSet.getString("company_name"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return account;
    }

    public Account searchAccountByEmail(String email) {
        Account account = null;
        String sql = "SELECT * FROM account a\n" +
                "WHERE a.email LIKE ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getBoolean("is_habitant")) {
                    account = new Habitant(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("phone_number"),
                            resultSet.getString("email"),
                            resultSet.getString("responsibility"),
                            resultSet.getInt("cost"),
                            resultSet.getInt("age"),
                            resultSet.getString("occupation"));
                } else {
                    account = new ExternalService(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("phone_number"),
                            resultSet.getString("email"),
                            resultSet.getString("responsibility"),
                            resultSet.getInt("cost"),
                            resultSet.getString("company_name"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return account;
    }

    public List<Integer> accountIdList() {
        List<Integer> idList = new ArrayList<>();
        String sql = "SELECT * FROM account";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                idList.add(resultSet.getInt("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return idList;
    }

    public void overwriteAccountIdByName(String name, int newId) {
        String infoBack = "overwrite failed!";

        String overwriteStatement = "UPDATE account " +
                "SET id = ? WHERE name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(overwriteStatement)) {
            preparedStatement.setInt(1, newId);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            infoBack = "id overwrited to: " + newId;
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Account> searchAccountsBySpace(int floor, int door) {
        List<Account> accountList = new ArrayList<>();
        String sql = "SELECT * FROM space s\n" +
                "JOIN property_table pt ON pt.space_id=s.id " +
                "JOIN account a ON pt.account_id=a.id " +
                "WHERE floor = ?" +
                "door = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, floor);
            preparedStatement.setInt(2, door);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getBoolean("a.is_habitant")) {
                    Account account = new Habitant(
                            resultSet.getInt("a.id"),
                            resultSet.getString("a.name"),
                            resultSet.getInt("a.phone_number"),
                            resultSet.getString("a.email"),
                            resultSet.getString("a.responsibility"),
                            resultSet.getInt("a.cost"),
                            resultSet.getInt("a.age"),
                            resultSet.getString("a.occupation"));
                    accountList.add(account);
                } else {
                    Account account = new ExternalService(
                            resultSet.getInt("a.id"),
                            resultSet.getString("a.name"),
                            resultSet.getInt("a.phone_number"),
                            resultSet.getString("a.email"),
                            resultSet.getString("a.responsibility"),
                            resultSet.getInt("a.cost"),
                            resultSet.getString("a.company_name"));
                    accountList.add(account);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return accountList;
    }

    public List<ExternalService> listAllExternalService() {
        List<ExternalService> accountList = new ArrayList<>();
        String sql = "SELECT * FROM account a\n" +
                "WHERE a.is_habitant = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 0);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ExternalService externalService = new ExternalService(
                        resultSet.getInt("a.id"),
                        resultSet.getString("a.name"),
                        resultSet.getInt("a.phone_number"),
                        resultSet.getString("a.email"),
                        resultSet.getString("a.responsibility"),
                        resultSet.getInt("a.cost"),
                        resultSet.getString("a.company_name"));
                accountList.add(externalService);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return accountList;
    }
}
