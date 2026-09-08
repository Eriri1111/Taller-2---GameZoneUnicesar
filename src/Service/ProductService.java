/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Dao.ProductRepository;
import Model.Product;
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
    
    
}
