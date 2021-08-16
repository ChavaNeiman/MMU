package main.java.com.company.dao;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IDao<ID extends java.io.Serializable,T>{
    /**
     * Saves a given entity.
     * @param entity  - given entity.
     */
    void save(T entity) throws IOException;

    /**
     * Deletes a given entity.
     * @param entity - given entity.
     * Throws: java.lang.IllegalArgumentException - in case the given entity is null.
     */
    void delete(T entity) throws IOException, IllegalArgumentException;

    /**
     * Retrieves an entity by its id.
     * @param id - must not be null.
     * @return the entity with the given id or null if none found.
     * Throws: java.lang.IllegalArgumentException - if id is null.
     */
    T find(ID id) throws FileNotFoundException, IllegalArgumentException;
}
