package Model;

/**
 * Abstract base class that represents any product sold by the store.
 * Holds the attributes common to every product type (identifier, title,
 * price and available quantity), while delegating the composition of a
 * full description to each concrete subclass, since the relevant details
 * differ between product types.
 */
public abstract class Product {

    private String id;
    private String title;
    private double price;
    private int quantity;

    /**
     * Creates a new product.
     *
     * @param id       unique identifier of the product
     * @param title    title of the product
     * @param price    unit price of the product
     * @param quantity quantity currently available in inventory
     */
    protected Product(String id, String title, double price, int quantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Increases or decreases the available stock by the given amount.
     * A negative amount reduces the stock (e.g. after a sale).
     *
     * @param delta amount to add to the current stock (can be negative)
     */
    public void adjustStock(int delta) {
        this.quantity += delta;
    }

    /**
     * Builds a complete, human-readable description of the product that
     * integrates its particular characteristics. Each subclass must provide
     * its own implementation, since the relevant characteristics depend on
     * the specific type of product.
     *
     * @return a full description of the product
     */
    public abstract String getDescription();

    public int getStockQuantity() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setStockQuantity(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
