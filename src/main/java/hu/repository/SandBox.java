package hu.repository;

import hu.domain.account.ExternalService;
import hu.domain.account.Habitant;
import hu.domain.space.Space;
import hu.repository.SpaceRepositories.SpaceRepository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SandBox {
    Connection connection;

    public static void main(String[] args) {
        SandBox sandBox = new SandBox();
        TransactionRepository transactionRepository = new TransactionRepository();
        SpaceRepository spaceRepository = new SpaceRepository();


        //System.out.println(transactionRepository.getDeadline());

        String deadLineS = "2022-01-15";
        String startingDateS = "2022-01-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deadlineLocal = LocalDate.parse(deadLineS, formatter);
        LocalDate startingDateLocal = LocalDate.parse(startingDateS, formatter);
        Date deadline = java.sql.Date.valueOf(deadlineLocal);
        Date startingDate = java.sql.Date.valueOf(startingDateLocal);

        java.util.Date currentDate;
        LocalDate localDate = LocalDate.now();
        currentDate = java.sql.Date.valueOf(localDate);

//        current: 2022-02-17
//        deadline: 2022-01-15
//        System.out.println(deadline);
//        if (deadline.compareTo(currentDate) < 0) {
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(deadline);
//            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
//            java.util.Date newDeadline = calendar.getTime();
//
//            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//            String formattedDate = format1.format(newDeadline);
//            LocalDate newDeadlineLocal = LocalDate.parse(formattedDate, formatter);
//            Date newDeadlineFinal = java.sql.Date.valueOf(newDeadlineLocal);
//
//            System.out.println(newDeadlineFinal);
//
//
//        }

        System.out.println(spaceRepository.searchSpacesBySpaceId(1));
        System.out.println(sandBox.balanceUpdate());


    }

    //    public void getDate() {
//        Date date = null;
//        String sql = "SELECT * FROM block WHERE id = ?;";
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setInt(1, 1);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                System.out.println(resultSet.getInt("id"));
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }


    public String balanceUpdate() {
        String plane = null;
        String sql = "SELECT * FROM account WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 111111);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                plane = resultSet.getString("name");

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return plane;
    }
}



