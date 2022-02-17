package hu.repository;

import hu.domain.account.Account;
import hu.domain.event.Complain;
import hu.domain.event.Emergency;
import hu.domain.event.Event;
import hu.domain.event.Reminder;
import hu.domain.space.Space;

import java.sql.*;

import static hu.repository.DatabaseConfigFlatApp.*;

public class EventTableRepository {

    Connection connection;

    public EventTableRepository() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createEventConnectionTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS event_table (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "space_id INT, " +
                "account_id INT, " +
                "event_id INT, " +
                "FOREIGN KEY (space_id) REFERENCES space(id), " +
                "FOREIGN KEY (account_id) REFERENCES account(id), " +
                "FOREIGN KEY (event_id) REFERENCES event(id));";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCreateTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String createNewEventConnectionTable(Event event) {
        String infoBack = "Event_table can not be created";
        String insertEventStatement = "INSERT INTO event_table VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertEventStatement)) {

            if (event instanceof Complain) {
                for (Account receiver : ((Complain) event).getReceivers()) {
                    preparedStatement.setInt(4, event.getId());
                    preparedStatement.setInt(3, receiver.getId());
                    preparedStatement.addBatch();
                }
            } else if (event instanceof Emergency) {
                for (Space space : ((Emergency) event).getAffectedSpaces()) {
                    preparedStatement.setInt(4, event.getId());
                    preparedStatement.setInt(3, space.getId());
                    preparedStatement.addBatch();
                }
            } else {
                for (Space space : ((Reminder) event).getAffectedSpaces()) {
                    preparedStatement.setInt(4, event.getId());
                    preparedStatement.setInt(3, space.getId());
                    preparedStatement.addBatch();
                }
            }
            preparedStatement.executeBatch();
            infoBack = "Event_table created";
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return infoBack;
    }
}
