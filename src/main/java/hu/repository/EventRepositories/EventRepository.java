package hu.repository.EventRepositories;

import hu.domain.account.Account;
import hu.domain.event.Complain;
import hu.domain.event.Emergency;
import hu.domain.event.Event;
import hu.domain.event.Reminder;
import hu.domain.space.Space;
import hu.repository.SpaceRepositories.SpaceRepository;
import hu.repository.accountRepositories.AccountRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hu.repository.DatabaseConfigFlatApp.*;

public class EventRepository {

    Connection connection;
    EventTableRepository eventTableRepository;
    AccountRepository accountRepository;
    SpaceRepository spaceRepository;

    public EventRepository() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            this.eventTableRepository = new EventTableRepository();
            this.accountRepository = new AccountRepository();
            this.spaceRepository = new SpaceRepository();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createEventTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS event (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "sender INT, " +
                "event_name VARCHAR(50) NOT NULL, " +
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
        String insertEventStatement = "INSERT INTO event " +
                "(sender, event_name, description, date, event_date) " +
                "VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertEventStatement,
                Statement.RETURN_GENERATED_KEYS)) {

            if (!(event instanceof Reminder)) {
                if (event instanceof Complain) {
                    preparedStatement.setInt(1, ((Complain) event).getAccount().getId());
                } else if (event instanceof Emergency) {
                    preparedStatement.setInt(1, ((Emergency) event).getAccount().getId());
                } else {
                    preparedStatement.setNull(1,Types.INTEGER);
                }
            } else {
                preparedStatement.setNull(1,Types.INTEGER); // TODO A sok IF-t átírom majd logikailag jó.
            }
            preparedStatement.setString(2, event.getEventName());
            preparedStatement.setString(3, event.getDescription());
            preparedStatement.setDate(4, (java.sql.Date) event.getDate());
            preparedStatement.setDate(5, (java.sql.Date) event.getEventDate());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            if (event instanceof Reminder || event instanceof Emergency || event instanceof Complain) {
                eventTableRepository.createNewEventConnectionTable(event, generatedKey);
            }
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

            resultSet.next();
            String eventName = resultSet.getString("event_name");
            Account sender = accountRepository.searchAccountById(resultSet.getInt("sender"));
            String description = resultSet.getString("description");
            Date date = resultSet.getDate("date");
            Date eventDate = resultSet.getDate("event_date");
            List<Account> accountList = new ArrayList<>();
            List<Space> spaceList = new ArrayList<>();

            if (resultSet.getInt("et.account_id") == 0 && resultSet.getInt("sender") == 0) {
                do {
                    Space space = spaceRepository.searchSpacesBySpaceId(resultSet.getInt("et_space_id"));
                    spaceList.add(space);
                    return new Reminder(resultSet.getInt("id"), eventName, description, date, eventDate,
                            spaceList);
                } while (resultSet.next());
            } else if (resultSet.getInt("et.space_id") == 0) {
                do {
                    Account account = accountRepository.searchAccountById(resultSet.getInt("et.account_id"));
                    accountList.add(account);
                    return new Complain(resultSet.getInt("id"), eventName, description, date,
                            eventDate, sender, accountList);
                } while (resultSet.next());
            } else if (resultSet.getInt("et.account_id") == 0 && resultSet.getInt("sender") != 0) {
                do {
                    Space space = spaceRepository.searchSpacesBySpaceId(resultSet.getInt("et_space_id"));
                    spaceList.add(space);
                    return new Emergency(resultSet.getInt("id"), eventName, description, date, eventDate,
                            sender, spaceList);
                } while (resultSet.next());
            }
            return new Event(id, eventName, description, date, eventDate);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
