package Dao;

import Model.Product;

/**
 * Data Access Object contract for {@link Product} persistence. Declares the
 * operations the service layer relies on to load and save the product
 * inventory, independently of how or where the data is actually stored.
 */
public interface ProductDAO extends Dao<Product> {
}
