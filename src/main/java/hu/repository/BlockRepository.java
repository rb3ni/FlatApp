package hu.repository;

import hu.domain.account.Account;
import hu.domain.account.ExternalService;
import hu.domain.account.Habitant;

import java.sql.*;

import static hu.repository.DatabaseConfigFlatApp.*;

public class BlockRepository {

    Connection connection;

    public BlockRepository() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createBlockTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS block (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "city VARCHAR(50) NOT NULL, " +
                "postal_code INT, " +
                "street VARCHAR(50) NOT NULL, " +
                "house_number VARCHAR(50), " +
                "description TEXT INT, " +
                "number_of_spaces BOOLEAN NOT NULL, " +
                "number_of_floors VARCHAR(30), " +
                "number_of_spaces INT, " +
                "number_of_accounts VARCHAR(30));";
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
