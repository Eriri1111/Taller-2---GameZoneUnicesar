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
    private final List <Product> products;
    
    public ProductService (ProductRepository repository){
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

    private Object findProductById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
