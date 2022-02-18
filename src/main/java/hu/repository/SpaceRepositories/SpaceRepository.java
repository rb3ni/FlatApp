package hu.repository.SpaceRepositories;

import hu.domain.space.Space;

import java.sql.*;
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
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "floor INT NOT NULL, " +
                "door INT NOT NULL, " +
                "space_type VARCHAR(30), " +
                "balance INT NOT NULL, " +
                "FOREIGN KEY (space_type) REFERENCES space_type(space_type)" +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCreateTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String createNewSpace(Space space) {
        String infoBack = "Space can not be created";
        String insertAccountStatement = "INSERT INTO space " +
                "(floor, door, space_type, balance)" +
                " VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertAccountStatement)) {

            preparedStatement.setInt(1, space.getFloor());
            preparedStatement.setInt(2, space.getDoor());
            preparedStatement.setString(3, space.getSpaceType());
            preparedStatement.setInt(4, space.getBalance());

            preparedStatement.executeUpdate();
            infoBack = space.getSpaceType() + " Space at " + space.getFloor() + " floor, " + space.getDoor() + " door created";
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return infoBack;
    }

    public Space searchSpacesBySpaceId(int id) {
        Space space = null;
        String sql = "SELECT * FROM space s " +
                "JOIN space_type st ON st.space_type = s.space_type " +
                "WHERE s.id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
            int spaceFloor = resultSet.getInt("floor");
            int spaceDoor = resultSet.getInt("door");
            String spaceType = resultSet.getString("space_type");
            Integer blockId = resultSet.getInt("block_id");
            int balance = resultSet.getInt("balance");
                space = new Space(id, spaceFloor, spaceDoor, null, spaceType, blockId, balance); // TODO ez miért van whileban?
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return space;
    }

    public Space searchSpacesByFloorAndDoor(int floor, int door) {
        Space space = null;
        String sql = "SELECT * FROM space s " +
                "JOIN space_type st ON st.space_type = s.space_type " +
                "WHERE s.floor = ? AND s.door = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, floor);
            preparedStatement.setInt(2, door);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int spaceId = resultSet.getInt("id");
            int spaceFloor = resultSet.getInt("floor");
            int spaceDoor = resultSet.getInt("door");
            String spaceType = resultSet.getString("space_type");
            Integer blockId = resultSet.getInt("block_id");
            int balance = resultSet.getInt("balance");

            do {
                space = new Space(spaceId, spaceFloor, spaceDoor, null, spaceType, blockId, balance);
            } while (resultSet.next());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return space;
    }

    public List<Space> searchSpacesByAccountNameAndEmail(String nameForSpaces, String emailForSpaces) {
        //TODO
        return null;
    }
}