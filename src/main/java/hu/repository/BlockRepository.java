package hu.repository;

import hu.domain.Block;

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
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "city VARCHAR(50) NOT NULL, " +
                "postal_code INT NOT NULL, " +
                "street VARCHAR(50) NOT NULL, " +
                "house_number VARCHAR(20) NOT NULL, " +
                "description TEXT, " +
                "number_of_spaces INT NOT NULL, " +
                "number_of_floors INT NOT NULL, " +
                "payment_deadline Date NOT NULL, " +
                "starting_date Date NOT NULL" +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCreateTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String createNewBlock(Block block) {
        String infoBack = "Block can not be created";
        String insertAccountStatement = "INSERT INTO block" +
                "(city, postal_code, street, house_number, description, number_of_spaces, number_of_floors, " +
                "payment_deadline, starting_date) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertAccountStatement)) {

            preparedStatement.setString(1, block.getCity());
            preparedStatement.setInt(2, block.getPostalCode());
            preparedStatement.setString(3, block.getStreet());
            preparedStatement.setInt(4, block.getHouseNumber());
            preparedStatement.setString(5, block.getDescription());
            preparedStatement.setInt(6, block.getNumberOfFlats());
            preparedStatement.setInt(7, block.getNumberOfFloors());
            preparedStatement.setDate(8, (java.sql.Date) block.getPaymentDeadline());
            preparedStatement.setDate(9, (java.sql.Date) block.getStartingDate());


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

            resultSet.next();
            String city = resultSet.getString("city");
            int postalCode = resultSet.getInt("postal_code");
            String street = resultSet.getString("street");
            int houseNumber = resultSet.getInt("house_number");
            String description = resultSet.getString("description");
            int numberOfSpaces = resultSet.getInt("number_of_spaces");
            int numberOfFloors = resultSet.getInt("number_of_floors");
            Date paymentDeadLine = resultSet.getDate("payment_deadline");
            Date startingDate = resultSet.getDate("payment_deadline");
            List<Integer> spaces = spaceIdList(resultSet);
            List<Integer> accounts = getAccountsId(resultSet); //Ez már lehet nem fut le így. illetve nem vagyok biztos benne egyáltalán kell mint attributum.

            return new Block(id, city, postalCode, street, houseNumber, description, numberOfSpaces,
                    numberOfFloors, spaces, accounts, paymentDeadLine, startingDate);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return block;
    }

    private List<Integer> spaceIdList(ResultSet resultSet) {
        List<Integer> spaceIdList = new ArrayList<>();
        try {
            do {
                spaceIdList.add(resultSet.getInt("s.id"));
            } while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spaceIdList;
    }

    private List<Integer> getAccountsId(ResultSet resultSet) {
        List<Integer> idList = new ArrayList<>();
        try {
            do {
                idList.add(resultSet.getInt("pt.account_id"));
            } while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idList;
    }

    public void deadlineSetter(Date newDeadline, int block_id) {
        String overwriteStatement = "UPDATE block " +
                "SET payment_deadline = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(overwriteStatement)) {
            preparedStatement.setDate(1, newDeadline);
            preparedStatement.setInt(2, block_id);
            preparedStatement.executeUpdate();
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("New payment deadline is: " + newDeadline);
    }

    public void newMonthCostUpdate(int block_id) {
        List<Integer> spaceIds = new ArrayList<>();
        List<Integer> balances = new ArrayList<>();
        List<Integer> costs = new ArrayList<>();
        String search = "SELECT s.id AS space_id, balance, cost  " +
                "FROM space s " +
                "JOIN space_type st ON st.space_type = s.space_type " +
                "WHERE st.block_id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(search)) {
            preparedStatement.setInt(1, block_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                spaceIds.add(resultSet.getInt("space_id"));
                balances.add(resultSet.getInt("balance"));
                costs.add(resultSet.getInt("cost"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String update = "UPDATE space " +
                "SET balance = ? WHERE id = ?";

        try (PreparedStatement preparedStatement1 = connection.prepareStatement(update)) {
            for (int i = 0; i < spaceIds.size(); i++) {
                preparedStatement1.setInt(1, (balances.get(i) - costs.get(i)));
                preparedStatement1.setInt(2, spaceIds.get(i));
                preparedStatement1.addBatch();
            }

            preparedStatement1.executeBatch();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}

