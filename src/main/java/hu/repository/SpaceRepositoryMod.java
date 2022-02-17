package hu.repository;

import hu.domain.space.SpaceMod;

import java.sql.*;
import java.util.List;

import static hu.repository.DatabaseConfigFlatApp.*;

public class SpaceRepositoryMod {

    Connection connection;

    public SpaceRepositoryMod() {
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
                "FOREIGN KEY (space_type) REFERENCES space_type_repository(space_type)" +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCreateTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String createNewSpace(SpaceMod spaceMod) {
        String infoBack = "Space can not be created";
        String insertAccountStatement = "INSERT INTO space " +
                "(floor, door, space_type)" +
                 " VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertAccountStatement)) {

            preparedStatement.setInt(1, spaceMod.getFloor());
            preparedStatement.setInt(2, spaceMod.getDoor());
            preparedStatement.setString(3, spaceMod.getSpaceType());

            preparedStatement.executeUpdate();
            infoBack = "Space created";
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return infoBack;
    }

    public SpaceMod searchSpacesById(int id) {
        SpaceMod spaceMods = null;
        String sql = "SELECT * FROM space s " +
                "WHERE s.id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            int spaceFloor = resultSet.getInt("floor");
            int spaceDoor = resultSet.getInt("door");
            String spaceType = resultSet.getString("space_type");
            Integer blockId = resultSet.getInt("block_id");

            while (resultSet.next()) {
                spaceMods = new SpaceMod(id, spaceFloor, spaceDoor, null, spaceType, blockId);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public SpaceMod searchSpacesByFloorAndDoor(int floor, int door) {
        SpaceMod spaceMods = null;
        String sql = "SELECT * FROM space s " +
                "WHERE s.floor = ? AND s.door = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, floor);
            preparedStatement.setInt(2, door);
            ResultSet resultSet = preparedStatement.executeQuery();

            int spaceId = resultSet.getInt("id");
            int spaceFloor = resultSet.getInt("floor");
            int spaceDoor = resultSet.getInt("door");
            String spaceType = resultSet.getString("space_type");
            Integer blockId = resultSet.getInt("block_id");

            while (resultSet.next()) {
                spaceMods = new SpaceMod(spaceId, spaceFloor, spaceDoor, null, spaceType, blockId);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }





}