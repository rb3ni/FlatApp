package hu.repository;

import hu.domain.space.FlatType;
import hu.domain.space.Space;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                "id INT PRIMARY KEY, " +
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

            preparedStatement.setInt(1, space.getId());
            preparedStatement.setInt(2, space.getFloor());
            preparedStatement.setInt(3, space.getDoor());
            preparedStatement.setDouble(4, space.getFlatType().getNumber_of_rooms());
            preparedStatement.setInt(5, space.getFlatType().getArea());
            preparedStatement.setBoolean(6, space.getFlatType().isHasBalcony());
            preparedStatement.setInt(7, space.getFlatType().getCost());
            preparedStatement.setString(8, space.getFlatType().getDescription()); // Lehet tableban VARCHAR legyen akkor
            preparedStatement.setString(9, space.getFlatType().name());
            preparedStatement.setInt(10, space.getBlockId());

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
            List<Integer> accountIdList = getAccountsId(resultSet);

            return new Space(id, spaceFloor, spaceDoor, accountIdList, flatTypeOfSpace, blockId);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
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
