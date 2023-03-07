package repository;

import db.DataBase;
import dto.Card;
import entity.Result;

import javax.swing.plaf.PanelUI;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.time.LocalDateTime.now;

public class CardRepo {
    public Result createCard(String number, String exp_date) {

        try {
            Result result = checkParam(number, exp_date);
            if (!result.isSuccess()) {

                return new Result(result.getMessage(), false);

            }

            Connection connection = DataBase.getConnection();
            String sql = "insert into card(number,exp_date,created_date) values(?,?,?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "number");
            preparedStatement.setString(2, "exp_date");
            preparedStatement.setTimestamp(3, Timestamp.valueOf(now()));
            preparedStatement.execute();
            return new Result("Card successfully created", true);
        } catch (SQLException e) {
            return new Result("error in server", false);
        }
    }

    public Result checkParam(String number, String exp_date) {
        if (Objects.isNull(number) || number.isBlank()) {
            return new Result("Number was required", false);
        }

        if (Objects.isNull(exp_date) || exp_date.isBlank()) {
            return new Result("exp_date was required", false);
        }


        try {
            Connection connection = DataBase.getConnection();
            String sql = "select * from card where number =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "number");
            ResultSet resultSet = preparedStatement.executeQuery();

            int counter = 0;

            while (resultSet.next()) {
                counter += resultSet.getInt(1);
            }
            if (counter > 0) {
                return new Result("This number already exists", false);
            }
            return new Result("Checked success", true);
        } catch (SQLException e) {
            return new Result("Error in server", false);
        }


    }

    public List<Card> getAllCard() {

        try {
            Connection connection = DataBase.getConnection();
            String sql = "select * from card";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Card> cards = new ArrayList<>();

            while (resultSet.next()) {
                String number = resultSet.getString("number");
                String exp_date = resultSet.getString("exp_date");
                Double balance = resultSet.getDouble("Balance");
                String status = resultSet.getString("status");
                String phone = resultSet.getString("phone");
                LocalDate date = LocalDate.from(resultSet.getTimestamp("created_date").toLocalDateTime());
                Card card = new Card(number, exp_date, balance, status, phone, date);
                cards.add(card);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean findCard(String number) {

        try {
            Connection connection = DataBase.getConnection();
            String sql = "select * from card where number =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "number");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }

    public Result updateCard(String oldNumber, String newNumber, String newExpdate) {

        try {
            Connection connection = DataBase.getConnection();
            String sql = "update card set number =?,exp_date=? where number =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newNumber);
            preparedStatement.setString(2, newExpdate);
            preparedStatement.setString(3, oldNumber);
            preparedStatement.execute();
            return new Result("card successflly updated", true);
        } catch (SQLException e) {
            return new Result("Error in server ", false);
        }
    }

    public void deleteCard(String number) {

        try {
            Connection connection = DataBase.getConnection();
            String sql = "delete from card where number =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "number");
            preparedStatement.execute();
            System.out.println("Card deleted");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean findCardAndCheckStatus(String number) {

        try {
            Connection connection = DataBase.getConnection();
            String sql = "select * from card where number=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "status");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String status = resultSet.getString("status");
                if (status.equals("status")) {
                    return true;
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void addCard(String number, String phone) {

        try {
            Connection connection = DataBase.getConnection();
            String sql = "update card set status=?,phone=? where number =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "active");
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, number);
            preparedStatement.execute();
            System.out.println("Card added ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Card> getUserCard(String i_phone) {

        try {
            Connection connection = DataBase.getConnection();
            String sql = "select * from card where number =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "number");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Card> cards = new ArrayList<>();

            while (resultSet.next()) {
                String number = resultSet.getString("number");
                String exp_date = resultSet.getString("exp_date");
                Double balance = resultSet.getDouble("balance");
                String status = resultSet.getString("status");
                String phone = resultSet.getString("phone");
                LocalDate date = LocalDate.from(resultSet.getTimestamp("created_date").toLocalDateTime());

                Card card = new Card(number, exp_date, balance, status, phone, date);
                cards.add(card);
            }
            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteCarduser(String number, String phone) {

        try {

            Connection connection = DataBase.getConnection();
            String sql = "update card set status =? , phone =? where phone=? and number =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            LocalDateTime now = now();
            preparedStatement.setString(1, "active");
            preparedStatement.setString(2, null);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, number);
            preparedStatement.execute();
            System.out.println("Card deleted from user ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
