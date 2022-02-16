package hu.repository;

import hu.domain.event.Event;
import hu.domain.space.FlatType;
import hu.domain.space.Space;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hu.repository.DatabaseConfigFlatApp.*;

public class EventRepository {

    Connection connection;

    public EventRepository() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createEventTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS event (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "eventName VARCHAR(30) NOT NULL, " +
                "description VARCHAR(255) NOT NULL, " +
                "date DATE NOT NULL);";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCreateTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String createNewEvent(Event event) {
        String infoBack = "Space can not be created";
        String insertAccountStatement = "INSERT INTO space VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertAccountStatement)) {

            preparedStatement.setString(2, event.getEventName());
            preparedStatement.setString(3, event.getDescription());
            preparedStatement.setDate(4, (java.sql.Date) event.getDate());

            preparedStatement.executeUpdate();
            infoBack = "Space created";
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return infoBack;
    }

    public Space searchSpaceById(int id) {
        Space space = null;
        String sql = "SELECT * FROM space s " +
                "JOIN property_table pt ON pt.space_id = s.id " +
                "WHERE s.id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            int spaceFloor = resultSet.getInt("floor");
            int spaceDoor = resultSet.getInt("door");
            FlatType flatTypeOfSpace = FlatType.valueOf(resultSet.getString("space_type"));
            Integer blockId = resultSet.getInt("block_id");
            List<Integer> habitantIdList = getHabitantsId(resultSet);

            return new Space(id, spaceFloor, spaceDoor, habitantIdList, flatTypeOfSpace, blockId);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private List<Integer> getHabitantsId(ResultSet resultSet) {
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
