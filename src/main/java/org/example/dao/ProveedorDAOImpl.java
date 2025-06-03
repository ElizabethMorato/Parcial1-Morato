package org.example.dao;

import org.example.model.Proveedor;
import org.example.util.DatabaseUtil;
import org.example.util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProveedorDAOImpl implements ProveedorDAO {
    private static final Logger logger = LoggerUtil.getLogger();

    @Override
    public void crear(Proveedor proveedor) {
        String sql = "INSERT INTO proveedores (nombre, direccion, telefono, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getDireccion());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setString(4, proveedor.getEmail());
            stmt.executeUpdate();
            logger.log(Level.INFO, "Proveedor creado con éxito: " + proveedor.getNombre());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear el proveedor: " + proveedor.getNombre(), e);
        }
    }

    @Override
    public Proveedor leer(Integer id) {
        String sql = "SELECT * FROM proveedores WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt("id"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setEmail(rs.getString("email"));
                logger.log(Level.INFO, "Proveedor leído con éxito: " + proveedor.getNombre());
                return proveedor;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al leer el proveedor con ID: " + id, ex);
        }
        return null;
    }

    @Override
    public List<Proveedor> listarTodos() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt("id"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setEmail(rs.getString("email"));
                proveedores.add(proveedor);
            }
            logger.log(Level.INFO, "La lista de proveedores se realizo correctamente");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al listar proveedores", ex);
        }
        return proveedores;
    }

    @Override
    public void actualizar(Proveedor proveedor) {
        String sql = "UPDATE proveedores SET nombre = ?, direccion = ?, telefono = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getDireccion());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setString(4, proveedor.getEmail());
            stmt.setInt(5, proveedor.getId());
            stmt.executeUpdate();
            logger.log(Level.INFO, "Proveedor actualizado correctamente: " + proveedor.getNombre());
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al actualizar el proveedor: " + proveedor.getNombre(), ex);
        }
    }

    @Override
    public void eliminar(Integer id) {
        String sql = "DELETE FROM proveedores WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.log(Level.INFO, "Proveedor eliminado correctamente con ID: " + id);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al eliminar el proveedor con ID: " + id, ex);
        }
    }
}

