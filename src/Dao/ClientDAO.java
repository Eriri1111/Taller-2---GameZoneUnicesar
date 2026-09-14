package Dao;

import Model.Client;

import java.util.List;

/**
 * Data Access Object contract for {@link Client} persistence. Declares the
 * operations the service layer relies on to load and save the registered
 * clients, independently of how or where the data is actually stored.
 */
public interface ClientDAO {

    /**
     * Loads every client currently stored.
     *
     * @return the list of stored clients
     */
    List<Client> loadClients();

    /**
     * Persists the given collection of clients, overwriting whatever was
     * previously stored.
     *
     * @param clients the full collection of clients to persist
     */
    void saveClients(List<Client> clients);
}
