package hu.repository;

import hu.domain.Block;
import hu.domain.account.Account;
import hu.domain.account.ExternalService;
import hu.domain.account.Habitant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                "id INT PRIMARY KEY, " +
                "city VARCHAR(50) NOT NULL, " +
                "postal_code INT NOT NULL, " +
                "street VARCHAR(50) NOT NULL, " +
                "house_number VARCHAR(20) NOT NULL, " +
                "description TEXT, " +
                "number_of_spaces INT NOT NULL, " +
                "number_of_floors INT NOT NULL, " +
                "payment_deadline Date NOT NULL);";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCreateTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String createNewBlock(Block block) {
        String infoBack = "Block can not be created";
        String insertAccountStatement = "INSERT INTO account VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertAccountStatement)) {

            preparedStatement.setInt(1, block.getId());
            preparedStatement.setString(2, block.getCity());
            preparedStatement.setInt(3, block.getPostalCode());
            preparedStatement.setString(4, block.getStreet());
            preparedStatement.setInt(5, block.getHouseNumber());
            preparedStatement.setString(6, block.getDescription());
            preparedStatement.setInt(7, block.getNumberOfFlats());
            preparedStatement.setInt(8, block.getNumberOfFloors());
            preparedStatement.setDate(9, (java.sql.Date) block.getPaymentDeadline());

            preparedStatement.executeUpdate();
            infoBack = "Block created";
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return infoBack;
    }

    public Block searchBlockById(int id) {
        Block block = null;
        String sql = "SELECT * FROM block b " +
                "JOIN space s ON s.block_id=b.id " +
                "JOIN property_table pt ON s.id= pt.space_id " +
                "WHERE b.id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            String city = resultSet.getString("city");
            int postalCode = resultSet.getInt("postal_code");
            String street = resultSet.getString("street");
            int houseNumber = resultSet.getInt("house_number");
            String description = resultSet.getString("description");
            int numberOfSpaces = resultSet.getInt("number_of_spaces");
            int numberOfFloors = resultSet.getInt("number_of_floors");
            Date paymentDeadLine = resultSet.getDate("payment_deadline");
            List<Integer> spaces = spaceIdList(resultSet);
            List<Integer> accounts = getAccountsId(resultSet);

            return new Block(id, city, postalCode, street, houseNumber, description, numberOfSpaces,
                    numberOfFloors, spaces, accounts, paymentDeadLine);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return block;
    }

    private List<Integer> spaceIdList(ResultSet resultSet) {
        List<Integer> spaceIdList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                spaceIdList.add(resultSet.getInt("s.id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spaceIdList;
    }

    private List<Integer> getAccountsId(ResultSet resultSet) {
        List<Integer> idList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                idList.add(resultSet.getInt("pt.account_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idList;
    }
}
