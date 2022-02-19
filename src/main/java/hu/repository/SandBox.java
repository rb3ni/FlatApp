package hu.repository;

import hu.domain.account.ExternalService;
import hu.domain.account.Habitant;
import hu.domain.space.Space;
import hu.repository.SpaceRepositories.SpaceRepository;
import hu.repository.accountRepositories.AccountRepository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static hu.repository.DatabaseConfigFlatApp.*;

public class SandBox {
    Connection connection;

    public SandBox() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SandBox sandBox = new SandBox();
        TransactionRepository transactionRepository = new TransactionRepository();
        SpaceRepository spaceRepository = new SpaceRepository();
        AccountRepository accountRepository = new AccountRepository();
        SandBoxBen sandBoxBen = new SandBoxBen();


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

        //System.out.println(spaceRepository.searchSpacesBySpaceId(1));
        //System.out.println(spaceRepository.accountIdListFake());

        //System.out.println(sandBox.accountIdListFake());
        transactionRepository.createTransactionTable();
        transactionRepository.readTransactions("src/main/resources/Transactions22_02.csv");
        //System.out.println(accountRepository.accountIdList());
        //System.out.println(sandBox.isNotDuplicate("7765GJJJH7"));
        //System.out.println(sandBox.isNotDuplicate("TR9876FSAF"));

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


    public List<Integer> accountIdListFake() {
        List<Integer> idList = new ArrayList<>();
        String sql = "SELECT pt.account_id AS account_id, s.id AS space_id, st.cost, s.balance " +
                "FROM property_table pt " +
                "JOIN space s ON s.id = pt.space_id " +
                "JOIN space_type st ON st.space_type = s.space_type " +
                "WHERE pt.account_id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 111111);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                idList.add(resultSet.getInt("space_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return idList;
    }


    private boolean isNotDuplicate(String transactionNumber) {
        boolean isNotDuplicate = true;
        String sqlCheck = "SELECT * FROM transactions WHERE transaction_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCheck)) {
            preparedStatement.setString(1, transactionNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isNotDuplicate = false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isNotDuplicate;

    }
}



