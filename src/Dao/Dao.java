package Dao;

import java.util.List;

/**
 * Generic Data Access Object contract. Defines the minimal set of
 * operations every persistence class in the system must provide: loading
 * every stored record and persisting the full collection back to disk.
 * Specific DAO interfaces extend this contract for each entity type,
 * allowing the service layer to depend on abstractions instead of on
 * concrete file-based implementations.
 *
 * @param <T> the type of entity managed by this DAO
 */
public interface Dao<T> {

    /**
     * Loads every record currently stored.
     *
     * @return the list of stored entities
     */
    List<T> loadAll();

    /**
     * Persists the given collection, overwriting whatever was previously
     * stored.
     *
     * @param entities the full collection of entities to persist
     */
    void saveAll(List<T> entities);
}
