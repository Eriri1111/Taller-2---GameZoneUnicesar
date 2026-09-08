package Model;

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

    /**
     * Creates a new sale item.
     *
     * @param productId    identifier of the purchased product
     * @param productTitle title of the purchased product at the time of sale
     * @param unitPrice    unit price of the product at the time of sale
     * @param quantity     quantity of units purchased
     */
    public SaleItem(String productId, String productTitle, double unitPrice, int quantity) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
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

    /**
     * Computes the subtotal of this line (unit price multiplied by quantity).
     *
     * @return the subtotal of this sale item
     */
    public double getSubtotal() {
        return unitPrice * quantity;
    }
}
