package model;

/**
 * Represents a single product line within a {@link Sale}: a snapshot of the
 * product that was sold, the quantity purchased and the unit price at the
 * moment of the sale. Keeping a snapshot instead of a live reference to the
 * product protects the historical sale record from later price changes.
 */
public class SaleItem {

    private String productId;
    private String productTitle;
    private double unitPrice;
    private int quantity;
    private String category;

    /**
     * Creates a new sale item with no category information. Kept for
     * backward compatibility with existing callers; the category defaults
     * to {@code "UNKNOWN"}, meaning this item will simply be ignored by
     * category-based promotions.
     *
     * @param productId    identifier of the purchased product
     * @param productTitle title of the purchased product at the time of sale
     * @param unitPrice    unit price of the product at the time of sale
     * @param quantity     quantity of units purchased
     */
    public SaleItem(String productId, String productTitle, double unitPrice, int quantity) {
        this(productId, productTitle, unitPrice, quantity, "UNKNOWN");
    }

    /**
     * Creates a new sale item, recording the category of the purchased
     * product ("VIDEOGAME" or "CONSOLE") so that category-based
     * promotions can later evaluate this line.
     *
     * @param productId    identifier of the purchased product
     * @param productTitle title of the purchased product at the time of sale
     * @param unitPrice    unit price of the product at the time of sale
     * @param quantity     quantity of units purchased
     * @param category     category of the purchased product at the time of sale
     */
    public SaleItem(String productId, String productTitle, double unitPrice, int quantity, String category) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.category = category;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Computes the subtotal of this line (unit price multiplied by quantity).
     *
     * @return the subtotal of this sale item
     */
    public double getSubtotal() {
        return unitPrice * quantity;
    }
}
