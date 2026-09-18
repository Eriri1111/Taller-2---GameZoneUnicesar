package model;

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

    private String id;
    private LocalDate date;
    private String clientId;
    private String sellerId;
    private List<SaleItem> items;
    private String appliedPromotionName;
    private double discountAmount;

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
        this.appliedPromotionName = null;
        this.discountAmount = 0.0;
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
     * every item it contains. This method intentionally ignores any
     * promotional discount, since it represents the raw sum of the items;
     * {@link #generateReceipt()} is responsible for combining it with the
     * discount to obtain the final amount charged to the client.
     *
     * @return the total amount of the sale, before any discount
     */
    public double calculateTotal() {
        double total = 0.0;
        for (SaleItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public String getAppliedPromotionName() {
        return appliedPromotionName;
    }

    public void setAppliedPromotionName(String appliedPromotionName) {
        this.appliedPromotionName = appliedPromotionName;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * Builds a human-readable receipt for this sale, showing the subtotal
     * (sum of every item), the discount granted by the applied promotion
     * (if any, identified by name), and the final total (subtotal minus
     * discount).
     *
     * @return the formatted receipt text
     */
    public String generateReceipt() {
        double subtotal = calculateTotal();
        double total = subtotal - discountAmount;

        StringBuilder receipt = new StringBuilder();
        receipt.append("Receipt for sale ").append(id).append(System.lineSeparator());
        receipt.append("Date: ").append(date).append(System.lineSeparator());
        for (SaleItem item : items) {
            receipt.append(String.format("  - %s x%d = %.2f%n",
                    item.getProductTitle(), item.getQuantity(), item.getSubtotal()));
        }
        receipt.append(String.format("Subtotal: %.2f%n", subtotal));
        if (appliedPromotionName != null && discountAmount > 0) {
            receipt.append(String.format("Discount applied (%s): -%.2f%n",
                    appliedPromotionName, discountAmount));
        } else {
            receipt.append("Discount applied: none").append(System.lineSeparator());
        }
        receipt.append(String.format("Total: %.2f", total));
        return receipt.toString();
    }
}
