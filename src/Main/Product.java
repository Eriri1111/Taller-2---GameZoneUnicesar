/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

/**
 *
 * @author Usuario
 */
public abstract class Product {
    private String id;
    private String title;
    private double price;
    private int stockQuantity;

    public Product(String id, String title, double price, int stockQuantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
        public abstract String getDescription();

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
        
        
}
