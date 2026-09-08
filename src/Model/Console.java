package com.gamezone.model;

/**
 * Represents a gaming console sold by the store. In addition to the common
 * attributes inherited from {@link Product}, a console is characterized by
 * its brand, model and generation.
 */
public class Console extends Product {

    private String brand;
    private String model;
    private String generation;

    /**
     * Creates a new console.
     *
     * @param id         unique identifier of the product
     * @param title      title (commercial name) of the console
     * @param price      unit price
     * @param quantity   quantity available in inventory
     * @param brand      manufacturer brand (e.g. Sony, Microsoft, Nintendo)
     * @param model      specific model of the console
     * @param generation hardware generation (e.g. 9th generation)
     */
    public Console(String id, String title, double price, int quantity,
                    String brand, String model, String generation) {
        super(id, title, price, quantity);
        this.brand = brand;
        this.model = model;
        this.generation = generation;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    @Override
    public String getDescription() {
        return String.format(
                "Console: %s | Brand: %s | Model: %s | Generation: %s | Price: %.2f | Stock: %d",
                getTitle(), brand, model, generation, getPrice(), getQuantity());
    }
}
