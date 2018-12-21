package mathpuzzles.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Generic interface for database access
 * @param <T> Object of type T
 * @param <K> Database id for the object
 */
public interface Dao<T, K> {

    /**
     * Returns the object of type T from the database based on the key
     * @param key id of object
     * @return The object from the database matching the key otherwise null
     * @throws SQLException if something fails at database level
     */
    T findOne(K key) throws SQLException;
    
    /**
     * Returns a list of all objects of type T from the database
     * @return A list of all objects of type T in the database
     * @throws SQLException if something fails at database level
     */
    List<T> findAll() throws SQLException;
    
    /**
     * Deletes the given object of type T from the database
     * @param key the id of the object to be deleted
     * @throws SQLException if something fails at database level
     */
    void delete(K key) throws SQLException;
    
    /**
     * Save the given object in the database
     * @param object The object to be saved
     * @return The user saved in the database
     * @throws SQLException if something fails at database level
     */
    T save(T object) throws SQLException;
}
