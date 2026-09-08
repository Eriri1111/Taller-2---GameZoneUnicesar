package Dao;

import com.gamezone.model.Sale;
import com.gamezone.model.SaleItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the persistence of {@link Sale} objects to and from a plain-text
 * file. Each sale line stores its header fields followed by a
 * semicolon-separated list of its {@link SaleItem} lines. It implements
 * {@link SaleDAO} so that the service layer can depend on the interface
 * instead of on this concrete, file-based implementation.
 */
public class SaleRepository implements SaleDAO {

    private static final String FIELD_SEPARATOR = "\\|";
    private static final String ITEM_SEPARATOR = ";";
    private static final String ITEM_FIELD_SEPARATOR = ":";

    private final String filePath;

    /**
     * Creates a repository that reads from and writes to the given file.
     *
     * @param filePath path of the file used to store sales
     */
    public SaleRepository(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads every sale stored in the file.
     *
     * @return the list of sales read from disk
     */
    @Override
    public List<Sale> loadAll() {
        List<Sale> sales = new ArrayList<>();
        Path path = Path.of(filePath);

        if (!Files.exists(path)) {
            return sales;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                Sale sale = parseLine(line);
                if (sale != null) {
                    sales.add(sale);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading sales file: " + e.getMessage());
        }

        return sales;
    }

    /**
     * Persists the given list of sales, overwriting the previous content
     * of the file.
     *
     * @param sales the list of sales to save
     */
    @Override
    public void saveAll(List<Sale> sales) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Sale sale : sales) {
                writer.println(toLine(sale));
            }
        } catch (IOException e) {
            System.out.println("Error writing sales file: " + e.getMessage());
        }
    }

    private Sale parseLine(String line) {
        try {
            String[] fields = line.split(FIELD_SEPARATOR, -1);
            Sale sale = new Sale(fields[0], LocalDate.parse(fields[1]), fields[2], fields[3]);

            if (fields.length > 4 && !fields[4].isBlank()) {
                String[] rawItems = fields[4].split(ITEM_SEPARATOR);
                for (String rawItem : rawItems) {
                    String[] itemFields = rawItem.split(ITEM_FIELD_SEPARATOR, -1);
                    sale.addItem(new SaleItem(
                            itemFields[0],
                            itemFields[1],
                            Double.parseDouble(itemFields[2]),
                            Integer.parseInt(itemFields[3])
                    ));
                }
            }
            return sale;
        } catch (RuntimeException e) {
            System.out.println("Skipping malformed sale line: " + line);
            return null;
        }
    }

    private String toLine(Sale sale) {
        StringBuilder itemsBuilder = new StringBuilder();
        List<SaleItem> items = sale.getItems();
        for (int i = 0; i < items.size(); i++) {
            SaleItem item = items.get(i);
            itemsBuilder.append(item.getProductId()).append(ITEM_FIELD_SEPARATOR)
                    .append(item.getProductTitle()).append(ITEM_FIELD_SEPARATOR)
                    .append(item.getUnitPrice()).append(ITEM_FIELD_SEPARATOR)
                    .append(item.getQuantity());
            if (i < items.size() - 1) {
                itemsBuilder.append(ITEM_SEPARATOR);
            }
        }

        return String.join("|",
                sale.getId(),
                sale.getDate().toString(),
                sale.getClientId(),
                sale.getSellerId(),
                itemsBuilder.toString());
    }
}
