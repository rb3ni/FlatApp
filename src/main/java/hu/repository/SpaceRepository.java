package hu.repository;

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
            infoBack = "Space created";
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return infoBack;
    }


    public Space searchSpacesBySpaceId(int id) {
        Space space = null;
        String sql = "SELECT * FROM space s " +
                "WHERE s.id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            int spaceFloor = resultSet.getInt("floor");
            int spaceDoor = resultSet.getInt("door");
            String spaceType = resultSet.getString("space_type");
            Integer blockId = resultSet.getInt("block_id");
            int balance = resultSet.getInt("balance");

            while (resultSet.next()) {
                space = new Space(id, spaceFloor, spaceDoor, null, spaceType, blockId, balance);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return space;
    }


//    public List<SpaceMod> searchSpacesByFloorAndDoor(int floor, int door) {
//        List<SpaceMod> spaceMods = null;
//        String sql = "SELECT * FROM space s " +
//                "JOIN space_type str ON str.space_type = s.space_type " +
//                "WHERE s.floor = ?" +
//                "s.door = ?;";
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            int spaceFloor = resultSet.getInt("floor");
//            int spaceDoor = resultSet.getInt("door");
//            String spaceType = resultSet.getString("space_type");
//            Integer blockId = resultSet.getInt("block_id");
//
//            while (resultSet.next()) {
//                spaceMods.add(new SpaceMod(id, spaceFloor, spaceDoor, null, spaceType, blockId));
//            }
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return null;
//    }

}