package repository;

import db.DataBase;
import entity.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CardRepo {
//public Result createCard(String number , String exp_date){
//}

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
