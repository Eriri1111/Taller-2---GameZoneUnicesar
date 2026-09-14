package Dao;

import model.BulkPurchaseDiscount;
import model.CategoryDiscount;
import model.PercentageDiscount;
import model.Promotion;

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
 * Handles the persistence of {@link Promotion} objects (percentage,
 * category and bulk-purchase discounts) to and from a plain-text,
 * pipe-separated file (data/promotions.csv). A leading discriminator
 * column ("PERCENTAGE", "CATEGORY" or "BULK") identifies the concrete
 * type of each stored promotion so it can be reconstructed on load. It
 * implements {@link PromotionDAO} so that the service layer can depend on
 * the interface instead of on this concrete, file-based implementation.
 */
public class PromotionRepository implements PromotionDAO {

    private static final String SEPARATOR = "\\|";

    private final String filePath;

    /**
     * Creates a repository that reads from and writes to the given file.
     *
     * @param filePath path of the file used to store promotions
     */
    public PromotionRepository(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads every promotion stored in the file. If the file does not
     * exist yet, an empty list is returned instead of failing.
     *
     * @return the list of promotions read from disk
     */
    @Override
    public List<Promotion> loadAll() {
        List<Promotion> promotions = new ArrayList<>();
        Path path = Path.of(filePath);

        if (!Files.exists(path)) {
            return promotions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                Promotion promotion = parseLine(line);
                if (promotion != null) {
                    promotions.add(promotion);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading promotions file: " + e.getMessage());
        }

        return promotions;
    }

    /**
     * Persists the given list of promotions, overwriting the previous
     * content of the file.
     *
     * @param promotions the list of promotions to save
     */
    @Override
    public void saveAll(List<Promotion> promotions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Promotion promotion : promotions) {
                writer.println(toLine(promotion));
            }
        } catch (IOException e) {
            System.out.println("Error writing promotions file: " + e.getMessage());
        }
    }

    private Promotion parseLine(String line) {
        String[] fields = line.split(SEPARATOR, -1);
        String type = fields[0];

        try {
            switch (type) {
                case "PERCENTAGE" -> {
                    return new PercentageDiscount(
                            fields[1],
                            fields[2],
                            LocalDate.parse(fields[3]),
                            LocalDate.parse(fields[4]),
                            Double.parseDouble(fields[5]));
                }
                case "CATEGORY" -> {
                    return new CategoryDiscount(
                            fields[1],
                            fields[2],
                            LocalDate.parse(fields[3]),
                            LocalDate.parse(fields[4]),
                            Double.parseDouble(fields[5]),
                            fields[6]);
                }
                case "BULK" -> {
                    return new BulkPurchaseDiscount(
                            fields[1],
                            fields[2],
                            LocalDate.parse(fields[3]),
                            LocalDate.parse(fields[4]),
                            Integer.parseInt(fields[5]),
                            Double.parseDouble(fields[6]));
                }
                default -> {
                    System.out.println("Skipping promotion line with unknown type: " + type);
                    return null;
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Skipping malformed promotion line: " + line);
            return null;
        }
    }

    private String toLine(Promotion promotion) {
        if (promotion instanceof PercentageDiscount percentageDiscount) {
            return String.join("|",
                    "PERCENTAGE",
                    percentageDiscount.getId(),
                    percentageDiscount.getName(),
                    percentageDiscount.getStartDate().toString(),
                    percentageDiscount.getEndDate().toString(),
                    String.valueOf(percentageDiscount.getPercentage()));
        } else if (promotion instanceof CategoryDiscount categoryDiscount) {
            return String.join("|",
                    "CATEGORY",
                    categoryDiscount.getId(),
                    categoryDiscount.getName(),
                    categoryDiscount.getStartDate().toString(),
                    categoryDiscount.getEndDate().toString(),
                    String.valueOf(categoryDiscount.getPercentage()),
                    categoryDiscount.getTargetCategory());
        } else if (promotion instanceof BulkPurchaseDiscount bulkPurchaseDiscount) {
            return String.join("|",
                    "BULK",
                    bulkPurchaseDiscount.getId(),
                    bulkPurchaseDiscount.getName(),
                    bulkPurchaseDiscount.getStartDate().toString(),
                    bulkPurchaseDiscount.getEndDate().toString(),
                    String.valueOf(bulkPurchaseDiscount.getMinimumQuantity()),
                    String.valueOf(bulkPurchaseDiscount.getPercentage()));
        }
        throw new IllegalArgumentException("Unknown promotion type: " + promotion.getClass());
    }
}
