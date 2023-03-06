package repository;

import dto.Profile;
import entity.Result;
import enums.Role;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class ProfileRepo {
    String url = "jdbc:postgresql://localhost:5432/atto_full";
    String dbUser = "postgres";
    String dbPassword = "Yashka_04";

    public Profile login(String i_phone, String i_password) {
        try {
            Class.forName("org.postgres.Driver");
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String sql = "select * from profile where phone =? and password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, i_phone);
            preparedStatement.setString(2, i_password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String phone = resultSet.getString("phone");
                String password = resultSet.getString("password");
                LocalDate time = LocalDate.from(resultSet.getTimestamp("created_date").toLocalDateTime());
                String status = resultSet.getString("status");
                Role role = Role.valueOf(resultSet.getString("role"));

                Profile profile = new Profile(name, surname, phone, password, time, status, role);
                return profile;

            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }


    private Result checkParam(Profile profile) {
        if (Objects.isNull(profile)) {
            return new Result("Data was required", false);
        }
        String name = profile.getName();

        if (Objects.isNull(name) || name.isBlank()) {
            return new Result("Name was requiret", false);
        }
        String phone = profile.getPhone();
        if (Objects.isNull(phone) || phone.isBlank()) {
            return new Result("Phone number is required", false);
        }
        if (!phone.matches("\\+998\\d{9}")) {
            return new Result("Phone number is invalid", false);
        }
        try {
            Class.forName("org.postgres.Driver");
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String sql = "select * from profile where phone=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, profile.getPhone());
            ResultSet resultSet = preparedStatement.executeQuery();
            int counter = 0;

            while (resultSet.next()) {
                counter += resultSet.getInt(1);

            }

            if (counter > 0) {
                return new Result("This number already exists", false);

            }
            return new Result("Checked ", true);


        } catch (ClassNotFoundException | SQLException e) {
            return new Result("Error", false);

        }

    }

}
