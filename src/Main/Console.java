/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

/**
 *
 * @author Usuario
 */
public class Console extends Product{
    private String brand;
    private String model;
    private String generation;

    public Console(String id, String title, double price, int stockQuantity, String brand, String model, String generation) {
        super(id, title, price, stockQuantity);
        this.brand = brand;
        this.model = model;
        this.generation = generation;
    }

    @Override
    public String getDescription() {
       return String.format("Console [ID: %s] %s | Brand: %s | Model: %s | Gen: %s | Price: $%.2f | Stock: %d",
                getId(), getTitle(), brand, model, generation, getPrice(), getStockQuantity());
    }  

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getGeneration() {
        return generation;
    }
    
}
