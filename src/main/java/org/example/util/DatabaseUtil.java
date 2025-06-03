package org.example.util;

import org.example.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseUtil {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.URL,
                DatabaseConfig.USER,
                DatabaseConfig.PASSWORD);
    }
    public static void closeResources(Connection conn, Statement stmt) {
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void initDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String createProveedoresTable = "CREATE TABLE IF NOT EXISTS proveedores (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nombre VARCHAR(100) NOT NULL, " +
                    "direccion VARCHAR(255), " +
                    "telefono VARCHAR(20), " +
                    "email VARCHAR(100))";

            String createMueblesTable = "CREATE TABLE IF NOT EXISTS muebles (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nombre VARCHAR(100) NOT NULL, " +
                    "descripcion TEXT, " +
                    "precio DECIMAL(10,2) NOT NULL, " +
                    "cantidad_stock INT NOT NULL, " +
                    "proveedor_id INT, " +
                    "categoria VARCHAR(50), " +
                    "FOREIGN KEY (proveedor_id) REFERENCES proveedores(id))";

            stmt.execute(createProveedoresTable);
            stmt.execute(createMueblesTable);

            System.out.println("La base de datos esta inicializada correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
