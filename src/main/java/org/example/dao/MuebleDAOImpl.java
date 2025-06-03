package org.example.dao;

import org.example.model.Mueble;
import org.example.util.DatabaseUtil;
import org.example.util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MuebleDAOImpl implements MuebleDAO {
    private static final Logger logger = LoggerUtil.getLogger();

    @Override
    public void crear(Mueble mueble) {
        String sql = "INSERT INTO muebles (nombre, descripcion, precio, cantidad_stock, proveedor_id, categoria) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mueble.getNombre());
            stmt.setString(2, mueble.getDescripcion());
            stmt.setDouble(3, mueble.getPrecio());
            stmt.setInt(4, mueble.getCantidadStock());
            stmt.setInt(5, mueble.getProveedorId());
            stmt.setString(6, mueble.getCategoria());
            stmt.executeUpdate();
            logger.log(Level.INFO, "Se creo con exito el mueble: " + mueble.getNombre());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear el mueble: " + mueble.getNombre(), e);
        }
    }

    @Override
    public Mueble leer(Integer id) {
        String sql = "SELECT * FROM muebles WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Mueble mueble = new Mueble();
                mueble.setId(rs.getInt("id"));
                mueble.setNombre(rs.getString("nombre"));
                mueble.setDescripcion(rs.getString("descripcion"));
                mueble.setPrecio(rs.getDouble("precio"));
                mueble.setCantidadStock(rs.getInt("cantidad_stock"));
                mueble.setProveedorId(rs.getInt("proveedor_id"));
                mueble.setCategoria(rs.getString("categoria"));
                logger.log(Level.INFO, "Mueble leído con éxito: " + mueble.getNombre());
                return mueble;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Hay un error al leer el mueble con ID: " + id, ex);
        }
        return null;
    }

    @Override
    public List<Mueble> listarTodos() {
        List<Mueble> muebles = new ArrayList<>();
        String sql = "SELECT * FROM muebles";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Mueble mueble = new Mueble();
                mueble.setId(rs.getInt("id"));
                mueble.setNombre(rs.getString("nombre"));
                mueble.setDescripcion(rs.getString("descripcion"));
                mueble.setPrecio(rs.getDouble("precio"));
                mueble.setCantidadStock(rs.getInt("cantidad_stock"));
                mueble.setProveedorId(rs.getInt("proveedor_id"));
                mueble.setCategoria(rs.getString("categoria"));
                muebles.add(mueble);
            }
            logger.log(Level.INFO, "La lista de muebles se leyo");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al listar muebles", ex);
        }
        return muebles;
    }

    @Override
    public void actualizar(Mueble mueble) {
        String sql = "UPDATE muebles SET nombre = ?, descripcion = ?, precio = ?, cantidad_stock = ?, proveedor_id = ?, categoria = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mueble.getNombre());
            stmt.setString(2, mueble.getDescripcion());
            stmt.setDouble(3, mueble.getPrecio());
            stmt.setInt(4, mueble.getCantidadStock());
            stmt.setInt(5, mueble.getProveedorId());
            stmt.setString(6, mueble.getCategoria());
            stmt.setInt(7, mueble.getId());
            stmt.executeUpdate();
            logger.log(Level.INFO, "El mueble se ha actualizado: " + mueble.getNombre());
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al actualizar el mueble: " + mueble.getNombre(), ex);
        }
    }

    @Override
    public void eliminar(Integer id) {
        String sql = "DELETE FROM muebles WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.log(Level.INFO, "El mueble se ha eliminado con ID: " + id);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al eliminar el mueble con ID: " + id, ex);
        }
    }
}

