package hu.repository;

import hu.domain.event.Complain;
import hu.domain.event.Emergency;
import hu.domain.event.Event;

import java.sql.*;

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
                "id INT PRIMARY KEY, " +
                "sender INT, " +
                "eventName VARCHAR(30) NOT NULL, " +
                "description VARCHAR(255) NOT NULL, " +
                "date DATE NOT NULL, " +
                "event_date DATE NOT NULL, " +
                "FOREIGN KEY (sender) REFERENCES account(id));";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCreateTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String createNewEvent(Event event) {
        String infoBack = "Event can not be created";
        String insertAccountStatement = "INSERT INTO space VALUES (?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertAccountStatement)) {

            preparedStatement.setInt(1, event.getId());
            preparedStatement.setString(3, event.getEventName());
            preparedStatement.setString(4, event.getDescription());
            preparedStatement.setDate(5, (java.sql.Date) event.getDate());
            preparedStatement.setDate(6, (java.sql.Date) event.getEventDate());

            if (event instanceof Complain) {
                preparedStatement.setInt(2, ((Complain) event).getAccountId());
                // Majd az event_tableRepoból createEvent_table(event);

            } else if (event instanceof Emergency) {
                preparedStatement.setInt(2, ((Emergency) event).getAccountId());
            } else {

            }

            preparedStatement.executeUpdate();
            infoBack = "Event created";
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return infoBack;
    }

    public Event searchEventById(int id) {
        Event event = null;
        String sql = "SELECT * FROM event e " +
                "JOIN event_table et ON et.event_id = e.id " +
                "WHERE e.id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            String eventName = resultSet.getString("event_name");
            String description = resultSet.getString("description");
            Date date = resultSet.getDate("date");
            Date eventDate = resultSet.getDate("event_date");
            int hasSpaceId = resultSet.getInt("et.space_id");
            int hasAccountId = resultSet.getInt("et.account_id");

//            if (isComplain != 0) {
//
//
//            }

            return new Event(id, eventName, description, date, eventDate);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
