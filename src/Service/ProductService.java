package com.gamezone.service;

import com.gamezone.model.Console;
import com.gamezone.model.Product;
import com.gamezone.model.Videogame;
import Dao.ProductDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Contains the business rules related to products: registration, listing,
 * lookup and stock management. This is the only layer authorized to invoke
 * {@link ProductDAO}; the user interface never accesses persistence
 * directly. The service depends on the DAO interface rather than on any
 * concrete, file-based implementation.
 */
public class ProductService {

    private final ProductDAO productDAO;
    private final List<Product> products;

    /**
     * Creates the service and loads the current product data from disk.
     *
     * @param productDAO DAO used to persist products
     */
    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
        this.products = new ArrayList<>(productDAO.loadAll());
    }

    /**
     * Registers a new videogame in the inventory and persists the change.
     *
     * @param id        unique identifier of the product
     * @param title     title of the videogame
     * @param price     unit price
     * @param quantity  initial available quantity
     * @param platform  platform the game was developed for
     * @param genre     genre of the videogame
     * @param ageRating recommended age rating
     * @return the newly created videogame
     */
    public Videogame registerVideogame(String id, String title, double price, int quantity,
                                        String platform, String genre, String ageRating) {
        Videogame videogame = new Videogame(id, title, price, quantity, platform, genre, ageRating);
        products.add(videogame);
        persist();
        return videogame;
    }

    /**
     * Registers a new console in the inventory and persists the change.
     *
     * @param id         unique identifier of the product
     * @param title      commercial name of the console
     * @param price      unit price
     * @param quantity   initial available quantity
     * @param brand      manufacturer brand
     * @param model      specific model
     * @param generation hardware generation
     * @return the newly created console
     */
    public Console registerConsole(String id, String title, double price, int quantity,
                                    String brand, String model, String generation) {
        Console console = new Console(id, title, price, quantity, brand, model, generation);
        products.add(console);
        persist();
        return console;
    }

    /**
     * Returns every product currently available in the inventory.
     *
     * @return an unmodifiable-safe list of all products
     */
    public List<Product> listProducts() {
        return new ArrayList<>(products);
    }

    /**
     * Finds a product by its identifier.
     *
     * @param productId identifier of the product to search for
     * @return an {@link Optional} containing the product if found
     */
    public Optional<Product> findById(String productId) {
        return products.stream().filter(p -> p.getId().equals(productId)).findFirst();
    }

    /**
     * Checks whether there is enough stock of a product to sell the given
     * quantity.
     *
     * @param productId        identifier of the product
     * @param requestedQuantity quantity requested for sale
     * @return {@code true} if there is enough stock, {@code false} otherwise
     */
    public boolean hasSufficientStock(String productId, int requestedQuantity) {
        return findById(productId)
                .map(product -> product.getQuantity() >= requestedQuantity)
                .orElse(false);
    }

    /**
     * Decreases the stock of a product by the given quantity and persists
     * the change. This method assumes the caller has already validated
     * that there is enough stock available.
     *
     * @param productId identifier of the product
     * @param quantity  quantity to subtract from the current stock
     */
    public void decreaseStock(String productId, int quantity) {
        findById(productId).ifPresent(product -> {
            product.adjustStock(-quantity);
            persist();
        });
    }

    /**
     * Persists the current in-memory list of products to disk.
     */
    public void persist() {
        productDAO.saveAll(products);
    }
}
