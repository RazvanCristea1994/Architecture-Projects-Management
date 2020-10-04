package repository;

import model.Entity;

import java.security.KeyException;
import java.util.List;

public interface  IRepository<T extends Entity> {

    void create(T entty);

    T read(int idEntity);

    List<T> read();

    void update(T entity) throws KeyException;

    void delete(int idEntity) throws KeyException;
}
