package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sale transaction registered in the store. A sale is
 * associated with the client who made the purchase, the seller who
 * attended it, a date and a set of {@link SaleItem} lines. A sale is
 * responsible for calculating its own total, since the total is a
 * property intrinsic to the sale itself and depends only on the items
 * it contains.
 */
public class Sale {

    private final String id;
    private final LocalDate date;
    private final String clientId;
    private final String sellerId;
    private final List<SaleItem> items;

    /**
     * Creates a new sale with no items. Items must be added afterwards
     * through {@link #addItem(SaleItem)} before the sale is persisted.
     *
     * @param id       unique identifier of the sale
     * @param date     date the sale was made
     * @param clientId identifier of the client who made the purchase
     * @param sellerId identifier of the seller who attended the sale
     */
    public Sale(String id, LocalDate date, String clientId, String sellerId) {
        this.id = id;
        this.date = date;
        this.clientId = clientId;
        this.sellerId = sellerId;
        this.items = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public List<SaleItem> getItems() {
        return items;
    }

    /**
     * Adds a product line to this sale.
     *
     * @param item the sale item to add
     */
    public void addItem(SaleItem item) {
        this.items.add(item);
    }

    /**
     * Calculates the total value of the sale by summing the subtotal of
     * every item it contains.
     *
     * @return the total amount of the sale
     */
    public double calculateTotal() {
        double total = 0.0;
        for (SaleItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }
}