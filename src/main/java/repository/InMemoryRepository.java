package repository;

import model.Entity;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<T extends Entity> implements IRepository<T> {

    private Map<Integer, T> storage = new HashMap<>();

    @Override
    public void create(T entity){
        storage.put(entity.getId(), entity);
    }

    @Override
    public T read(int idEntity) {
        return storage.get(idEntity);
    }

    @Override
    public List<T> read() {

        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(T entity) throws KeyException {
        if (!storage.containsKey(entity.getId())) {
            throw new KeyException("The entry ID does not exist!");
        }
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(int idEntity) throws KeyException {
        if (!storage.containsKey(idEntity)) {
            throw new KeyException("The entry ID does not exist!");
        }
        storage.remove(idEntity);
    }
}
