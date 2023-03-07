package repository;

import db.DataBase;
import entity.Result;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
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

}
