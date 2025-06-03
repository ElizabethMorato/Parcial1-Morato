package org.example;

import org.example.dao.MuebleDAO;
import org.example.dao.MuebleDAOImpl;
import org.example.dao.ProveedorDAO;
import org.example.dao.ProveedorDAOImpl;
import org.example.model.Mueble;
import org.example.model.Proveedor;
import org.example.util.DatabaseUtil;
import org.example.util.LoggerUtil;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    private static final Logger logger = LoggerUtil.getLogger();

    public static void main(String[] args) {
        LoggerUtil.setupLogger();
        DatabaseUtil.initDatabase();

        try (Connection connection = DatabaseUtil.getConnection()) {
            ProveedorDAO proveedorDAO = new ProveedorDAOImpl();
            MuebleDAO muebleDAO = new MuebleDAOImpl();


            Proveedor proveedor = new Proveedor();
            proveedor.setNombre("Proveedor Casillas");
            proveedor.setDireccion("Manuel A. Saez 2250");
            proveedor.setTelefono("2615418101");
            proveedor.setEmail("casillasmuebles@gmail.com");
            proveedorDAO.crear(proveedor);
            logger.info("El proveedor se creo correctamente: {}"+ proveedor.getNombre());


            Mueble mueble1 = new Mueble();
            mueble1.setNombre("Silla Moderna");
            mueble1.setDescripcion("Silla de diseño moderno");
            mueble1.setPrecio(35.899);
            mueble1.setCantidadStock(10);
            mueble1.setProveedorId(1);
            mueble1.setCategoria("Sala");
            muebleDAO.crear(mueble1);
            logger.info("El mueble se creo correctamente: "+ mueble1.getNombre());

            Mueble mueble2 = new Mueble();
            mueble2.setNombre("Mesa de comedor");
            mueble2.setDescripcion("Mesa de color blanca con capacidad para 8 sillas");
            mueble2.setPrecio(299.99);
            mueble2.setCantidadStock(5);
            mueble2.setProveedorId(1);
            mueble2.setCategoria("Comedor");
            muebleDAO.crear(mueble2);
            logger.info("Mueble creado correctamente: "+ mueble2.getNombre());

            // Listar todos los proveedores y muebles
            logger.info("Lista de Proveedores:");
            proveedorDAO.listarTodos().forEach(p -> logger.info("ID: {}, Nombre: {}"+ p.getId() + p.getNombre()));

            logger.info("Lista de Muebles:");
            muebleDAO.listarTodos().forEach(m -> logger.info("ID: {}, Nombre: {}, Precio: {}"+ m.getId()+ m.getNombre()+ m.getPrecio()));

        } catch (SQLException ex) {
            logger.info("Se produce un error en la conexion de la base de datos" + ex);
        }
    }
}