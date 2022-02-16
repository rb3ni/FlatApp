package hu.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

    public void createEventTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS event_table (" +
                "id INT PRIMARY KEY, " +
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



}
