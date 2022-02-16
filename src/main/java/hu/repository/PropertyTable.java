package hu.repository;

import java.sql.*;

import static hu.repository.DatabaseConfigFlatApp.*;

public class PropertyTable {

    Connection connection;

    public PropertyTable() {
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
                infoBack = "Transaction saved";

            } catch (
                    SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return infoBack;
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
}

