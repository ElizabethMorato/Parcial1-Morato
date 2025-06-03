package org.example.config;


import java.sql.*;

public class DatabaseConfig {
    public static final String URL = "jdbc:h2:~/tiendademueble;AUTO_SERVER=TRUE";
    public static final String USER = "sa";
    public static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
