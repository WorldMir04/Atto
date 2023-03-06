package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
public static Connection getConnection () {
    try {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/atto_full\",\"postgres\", \"Yashka_04");

    } catch (ClassNotFoundException | SQLException e) {
        throw new RuntimeException(e);
    }
}
}
