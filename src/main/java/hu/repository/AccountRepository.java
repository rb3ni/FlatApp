package hu.repository;

import hu.domain.account.Account;
import hu.domain.account.ExternalService;
import hu.domain.account.Habitant;

import java.sql.*;

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
                "id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(50) NOT NULL, " +
                "phone_number INT, " +
                "email VARCHAR(50) NOT NULL, " +
                "responsibility VARCHAR(50), " +
                "cost INT, " +
                "is_habitant BOOLEAN NOT NULL, " +
                "occupation VARCHAR(30), " +
                "age INT, " +
                "company_name VARCHAR(30)" +
                //foreign key-ek
                ");";
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

            preparedStatement.setInt(1, account.getId());               //AUTO_INCREMENT??
            preparedStatement.setString(2, account.getName());
            preparedStatement.setInt(3, account.getPhoneNumber());
            preparedStatement.setString(4, account.getEmail());
            preparedStatement.setString(5, account.getResponsibility());
            preparedStatement.setInt(6, account.getCost());

            if (account.getClass().getSimpleName().equals("Habitant")) {
                preparedStatement.setBoolean(7, true);
                preparedStatement.setString(8, ((Habitant) account).getOccupation());
                preparedStatement.setInt(9, ((Habitant) account).getAge());
                preparedStatement.setString(10, null);
            } else {
                preparedStatement.setBoolean(7, false);
                preparedStatement.setString(8, null);
                preparedStatement.setInt(9, -1);
                preparedStatement.setString(10, ((ExternalService) account).getCompanyName());
            }
            preparedStatement.executeUpdate();
            infoBack = "Account created";
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return infoBack;
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
                    account = new Habitant(resultSet.getString("name"),
                            resultSet.getInt("phone_number"),
                            resultSet.getString("email"),
                            resultSet.getString("responsibility"),
                            resultSet.getInt("cost"),
                            resultSet.getInt("age"),
                            resultSet.getString("occupation"));
                } else {
                    account = new ExternalService(resultSet.getString("name"),
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


}
