package org.example.dao;

import java.util.List;

public interface GenericaDAO <T,K>{
    void crear(T entity);
    T leer(K id);
    List<T> listarTodos();
    void actualizar(T entity);
    void eliminar(K id);
}
