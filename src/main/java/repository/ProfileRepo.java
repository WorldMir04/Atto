package repository;

import dto.Profile;
import enums.Role;

import java.sql.*;
import java.time.LocalDate;

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


}
