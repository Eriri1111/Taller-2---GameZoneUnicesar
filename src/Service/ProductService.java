/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Dao.ProductRepository;
import Model.Product;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class ProductService {

    private final ProductRepository repository;
    private final List<Product> products;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
        this.products = repository.loadProducts();
    }

    public void registerProduct(Product product) {
        if (findProductById(product.getId()) != null) {
            throw new IllegalArgumentException("A product with ID " + product.getId() + " already exists.");
        }
        products.add(product);
        repository.saveProducts(products);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Product findProductById(String id) {
        for (Product product : products) {
            if (product.getId().equalsIgnoreCase(id)) {
                return product;
            }
        }
        return null;
    }

    public void updateStock(String id, int quantity) {
        Product product = findProductById(id);
        if (product == null) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }
        if (product.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getTitle());
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);
        repository.saveProducts(products);
    }

}
