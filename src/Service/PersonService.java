package com.gamezone.service;

import com.gamezone.model.Client;
import com.gamezone.model.Seller;
import Dao.PersonDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Contains the business rules related to people: client registration,
 * client and seller listing, and lookup by identifier. This is the only
 * layer authorized to invoke {@link PersonDAO}. The service depends on the
 * DAO interface rather than on any concrete, file-based implementation.
 */
public class PersonService {

    private final PersonDAO personDAO;
    private final List<Client> clients;
    private final List<Seller> sellers;

    /**
     * Creates the service and loads the current people data from disk.
     *
     * @param personDAO DAO used to persist clients and sellers
     */
    public PersonService(PersonDAO personDAO) {
        this.personDAO = personDAO;
        this.clients = new ArrayList<>(personDAO.loadClients());
        this.sellers = new ArrayList<>(personDAO.loadSellers());
    }

    /**
     * Registers a new client and persists the change. Sellers are not
     * registered through the user interface, since they are already
     * hired and preloaded when the store starts operating.
     *
     * @param id    unique identification of the client
     * @param name  full name of the client
     * @param phone contact phone number
     * @param email email address of the client
     * @return the newly created client
     */
    public Client registerClient(String id, String name, String phone, String email) {
        Client client = new Client(id, name, phone, email);
        clients.add(client);
        persistClients();
        return client;
    }

    /**
     * Returns every registered client.
     *
     * @return a list of all clients
     */
    public List<Client> listClients() {
        return new ArrayList<>(clients);
    }

    /**
     * Returns every registered seller.
     *
     * @return a list of all sellers
     */
    public List<Seller> listSellers() {
        return new ArrayList<>(sellers);
    }

    /**
     * Finds a client by their identifier.
     *
     * @param clientId identifier of the client to search for
     * @return an {@link Optional} containing the client if found
     */
    public Optional<Client> findClientById(String clientId) {
        return clients.stream().filter(c -> c.getId().equals(clientId)).findFirst();
    }

    /**
     * Finds a seller by their identifier.
     *
     * @param sellerId identifier of the seller to search for
     * @return an {@link Optional} containing the seller if found
     */
    public Optional<Seller> findSellerById(String sellerId) {
        return sellers.stream().filter(s -> s.getId().equals(sellerId)).findFirst();
    }

    /**
     * Registers a sale identifier in a client's purchase history and
     * persists the change.
     *
     * @param clientId identifier of the client
     * @param saleId   identifier of the sale to add to the history
     */
    public void addPurchaseToClient(String clientId, String saleId) {
        findClientById(clientId).ifPresent(client -> {
            client.addPurchase(saleId);
            persistClients();
        });
    }

    /**
     * Persists the current in-memory list of clients to disk.
     */
    public void persistClients() {
        personDAO.saveClients(clients);
    }

    /**
     * Persists the current in-memory list of sellers to disk.
     */
    public void persistSellers() {
        personDAO.saveSellers(sellers);
    }
}
