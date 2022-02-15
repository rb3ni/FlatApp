package hu.repository;

import hu.domain.account.Account;
import hu.domain.account.ExternalService;
import hu.domain.account.Habitant;
import hu.domain.space.Space;

import java.sql.*;

import static hu.repository.DatabaseConfigFlatApp.*;

public class SpaceRepository {

    Connection connection;

    public SpaceRepository() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createSpaceTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS space (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "floor INT NOT NULL, " +
                "door INT NOT NULL, " +
                "number_of_rooms INT, " +
                "area INT NOT NULL, " +
                "has_balcony BOOLEAN, " +
                "cost INT NOT NULL, " +
                "description TEXT, " +
                "space_type ENUM('FLAT_A', 'FLAT_B', 'FLAT_C', 'PARKING_SLOT', 'RENTAL_SPACE') NOT NULL, " +
                "block_id INT, " +
                "FOREIGN KEY (block_id) REFERENCES block(id));";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCreateTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String createNewSpace(Space space) {
        String infoBack = "Space can not be created";
        String insertAccountStatement = "INSERT INTO space VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertAccountStatement)) {

            preparedStatement.setInt(1, space.getId());               //AUTO_INCREMENT??
            preparedStatement.setInt(2, space.getFloor());
            preparedStatement.setInt(3, space.getDoor());
            preparedStatement.setDouble(4, space.getFlatType().getNumber_of_rooms());
            preparedStatement.setInt(5, space.getFlatType().getArea());
            preparedStatement.setBoolean(6, space.getFlatType().isHasBalcony());
            preparedStatement.setInt(7, space.getFlatType().getCost());
            preparedStatement.setString(7, space.getFlatType().getDescription()); // Lehet tableban VARCHAR legyen akkor
            preparedStatement.setString(8, space.getFlatType().name());
            preparedStatement.setInt(9, space.getBlock().getId());

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
