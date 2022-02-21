package hu.repository;

import hu.domain.space.Space;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hu.repository.DatabaseConfigFlatApp.*;

public class PropertyTableRepository {

    Connection connection;

    public PropertyTableRepository() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createPropertyTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS property_table (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "space_id INT, " +
                "account_id INT, " +
                "FOREIGN KEY (space_id) REFERENCES space(id), " +
                "FOREIGN KEY (account_id) REFERENCES account(id) " +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCreateTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String assignHabitantAndSpace(int habitantId, int spaceId) {

        String infoBack = "Issue with insertion";
        if (isNotDuplicate(habitantId, spaceId)) {
            String assertionStatement = "INSERT INTO property_table " +
                    "(space_id, account_id)" +
                    " VALUES (?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(assertionStatement)) {

                preparedStatement.setInt(1, spaceId);
                preparedStatement.setInt(2, habitantId);
                preparedStatement.executeUpdate();
                infoBack = "Assertion saved";

            } catch (
                    SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return infoBack;
    }


    public List<Space> searchSpacesByHabitantId(int id) {
        List<Space> spaces = new ArrayList<>();
        String sql = "SELECT * FROM property_table pt " +
                "JOIN space s ON s.id = pt.space_id " +
                "WHERE pt.account_id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
            int spaceFloor = resultSet.getInt("floor");
            int spaceDoor = resultSet.getInt("door");
            String spaceType = resultSet.getString("space_type");
            int balance = resultSet.getInt("balance");

                spaces.add(new Space(id, spaceFloor, spaceDoor,null, spaceType, 1, balance));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return spaces;
    }

    private boolean isNotDuplicate(int habitantId, int spaceId) {
        boolean isNotDuplicate = true;
        String sqlCheck = "SELECT * FROM property_table WHERE space_id = ? AND account_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCheck)) {
            preparedStatement.setInt(1, spaceId);
            preparedStatement.setInt(2, habitantId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isNotDuplicate = false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isNotDuplicate;
    }

    public List<Integer> searchHabitantsBySpaceId(int id) {
        List<Integer> habitantIds = new ArrayList<>();
        String sql = "SELECT * FROM property_table pt " +
                "JOIN space s ON s.id = pt.space_id " +
                "WHERE s.id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                habitantIds.add(resultSet.getInt("pt.account_id"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return habitantIds;
    }

}

