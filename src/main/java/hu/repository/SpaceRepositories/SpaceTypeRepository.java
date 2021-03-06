package hu.repository.SpaceRepositories;

import hu.domain.account.Account;
import hu.domain.event.Complain;
import hu.domain.space.Space;
import hu.domain.space.SpaceType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hu.repository.DatabaseConfigFlatApp.*;

public class SpaceTypeRepository {

    Connection connection;

    public SpaceTypeRepository() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void createSpaceTypeRepository() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS space_type (" +
                "space_type VARCHAR(30) PRIMARY KEY, " +
                "number_of_rooms INT, " +
                "area INT NOT NULL, " +
                "has_balcony BOOLEAN, " +
                "cost INT NOT NULL, " +
                "description TEXT, " +
                "block_id INT " +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCreateTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String createNewSpaceType(SpaceType spaceType) {
        String infoBack = "Space-type can not be created";
        String insertSpaceTypeStatement = "INSERT INTO space_type VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSpaceTypeStatement)) {

            preparedStatement.setString(1, spaceType.getSpaceType());
            preparedStatement.setDouble(2, spaceType.getNumberOfRooms());
            preparedStatement.setInt(3, spaceType.getArea());
            preparedStatement.setBoolean(4, spaceType.hasHasBalcony());
            preparedStatement.setInt(5, spaceType.getCost());
            preparedStatement.setString(6, spaceType.getDescription());
            preparedStatement.setInt(7, spaceType.getBlockId());
            preparedStatement.executeUpdate();
            infoBack = "Space-type " + spaceType.getSpaceType() + " created";
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return infoBack;
    }


}

