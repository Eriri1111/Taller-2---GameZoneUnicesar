package Dao;

import com.gamezone.model.Console;
import com.gamezone.model.Product;
import com.gamezone.model.Videogame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the persistence of {@link Product} objects (videogames and
 * consoles) to and from a plain-text file. This class is the only place
 * in the system responsible for reading and writing product data, keeping
 * file-access logic completely outside the model layer. It implements
 * {@link ProductDAO} so that the service layer can depend on the
 * interface instead of on this concrete, file-based implementation.
 */
public class ProductRepository implements ProductDAO {

    private static final String SEPARATOR = "\\|";
    private final String filePath;

    /**
     * Creates a repository that reads from and writes to the given file.
     *
     * @param filePath path of the file used to store products
     */
    public ProductRepository(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads every product stored in the file. If the file does not exist
     * yet, an empty list is returned instead of failing.
     *
     * @return the list of products read from disk
     */
    @Override
    public List<Product> loadAll() {
        List<Product> products = new ArrayList<>();
        Path path = Path.of(filePath);

        if (!Files.exists(path)) {
            return products;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                Product product = parseLine(line);
                if (product != null) {
                    products.add(product);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading products file: " + e.getMessage());
        }

        return products;
    }

    /**
     * Persists the given list of products, overwriting the previous
     * content of the file.
     *
     * @param products the list of products to save
     */
    @Override
    public void saveAll(List<Product> products) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Product product : products) {
                writer.println(toLine(product));
            }
        } catch (IOException e) {
            System.out.println("Error writing products file: " + e.getMessage());
        }
    }

    private Product parseLine(String line) {
        String[] fields = line.split(SEPARATOR, -1);
        String type = fields[0];

        try {
            if ("VIDEOGAME".equals(type)) {
                return new Videogame(
                        fields[1],
                        fields[2],
                        Double.parseDouble(fields[3]),
                        Integer.parseInt(fields[4]),
                        fields[5],
                        fields[6],
                        fields[7]
                );
            } else if ("CONSOLE".equals(type)) {
                return new Console(
                        fields[1],
                        fields[2],
                        Double.parseDouble(fields[3]),
                        Integer.parseInt(fields[4]),
                        fields[5],
                        fields[6],
                        fields[7]
                );
            }
        } catch (RuntimeException e) {
            System.out.println("Skipping malformed product line: " + line);
        }
        return null;
    }

    private String toLine(Product product) {
        if (product instanceof Videogame videogame) {
            return String.join("|",
                    "VIDEOGAME",
                    videogame.getId(),
                    videogame.getTitle(),
                    String.valueOf(videogame.getPrice()),
                    String.valueOf(videogame.getQuantity()),
                    videogame.getPlatform(),
                    videogame.getGenre(),
                    videogame.getAgeRating());
        } else if (product instanceof Console console) {
            return String.join("|",
                    "CONSOLE",
                    console.getId(),
                    console.getTitle(),
                    String.valueOf(console.getPrice()),
                    String.valueOf(console.getQuantity()),
                    console.getBrand(),
                    console.getModel(),
                    console.getGeneration());
        }
        throw new IllegalArgumentException("Unknown product type: " + product.getClass());
    }
}
