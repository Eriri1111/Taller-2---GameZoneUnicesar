package Dao;

import Model.Client;
import Model.Seller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handles the persistence of {@link Client} and {@link Seller} objects to
 * and from plain-text files. Clients and sellers are stored in two
 * separate files, since they represent different collections, but both
 * are managed by this single persistence class for the "people" module.
 * It implements {@link PersonDAO} so that the service layer can depend on
 * the interface instead of on this concrete, file-based implementation.
 */
public class PersonRepository implements PersonDAO {

    private static final String SEPARATOR = "\\|";

    private final String clientsFilePath;
    private final String sellersFilePath;

    /**
     * Creates a repository that reads from and writes to the given files.
     *
     * @param clientsFilePath path of the file used to store clients
     * @param sellersFilePath path of the file used to store sellers
     */
    public PersonRepository(String clientsFilePath, String sellersFilePath) {
        this.clientsFilePath = clientsFilePath;
        this.sellersFilePath = sellersFilePath;
    }

    /**
     * Loads every client stored in the clients file.
     *
     * @return the list of clients read from disk
     */
    @Override
    public List<Client> loadClients() {
        List<Client> clients = new ArrayList<>();
        Path path = Path.of(clientsFilePath);

        if (!Files.exists(path)) {
            return clients;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(clientsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.split(SEPARATOR, -1);
                Client client = new Client(fields[0], fields[1], fields[2], fields[3]);
                if (fields.length > 4 && !fields[4].isBlank()) {
                    client.setPurchaseHistory(new ArrayList<>(Arrays.asList(fields[4].split(","))));
                }
                clients.add(client);
            }
        } catch (IOException e) {
            System.out.println("Error reading clients file: " + e.getMessage());
        }

        return clients;
    }

    /**
     * Persists the given list of clients, overwriting the previous
     * content of the clients file.
     *
     * @param clients the list of clients to save
     */
    @Override
    public void saveClients(List<Client> clients) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(clientsFilePath))) {
            for (Client client : clients) {
                String history = String.join(",", client.getPurchaseHistory());
                writer.println(String.join("|",
                        client.getId(),
                        client.getName(),
                        client.getPhone(),
                        client.getEmail(),
                        history));
            }
        } catch (IOException e) {
            System.out.println("Error writing clients file: " + e.getMessage());
        }
    }

    /**
     * Loads every seller stored in the sellers file.
     *
     * @return the list of sellers read from disk
     */
    @Override
    public List<Seller> loadSellers() {
        List<Seller> sellers = new ArrayList<>();
        Path path = Path.of(sellersFilePath);

        if (!Files.exists(path)) {
            return sellers;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(sellersFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.split(SEPARATOR, -1);
                sellers.add(new Seller(fields[0], fields[1], fields[2], fields[3], fields[4]));
            }
        } catch (IOException e) {
            System.out.println("Error reading sellers file: " + e.getMessage());
        }

        return sellers;
    }

    /**
     * Persists the given list of sellers, overwriting the previous
     * content of the sellers file.
     *
     * @param sellers the list of sellers to save
     */
    @Override
    public void saveSellers(List<Seller> sellers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(sellersFilePath))) {
            for (Seller seller : sellers) {
                writer.println(String.join("|",
                        seller.getId(),
                        seller.getName(),
                        seller.getPhone(),
                        seller.getEmployeeCode(),
                        seller.getShift()));
            }
        } catch (IOException e) {
            System.out.println("Error writing sellers file: " + e.getMessage());
        }
    }
}
