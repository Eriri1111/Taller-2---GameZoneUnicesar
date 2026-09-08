package Dao;

import Model.Seller;

import java.util.List;

/**
 * Data Access Object contract for {@link Seller} persistence. Declares the
 * operations the service layer relies on to load and save the registered
 * sellers, independently of how or where the data is actually stored.
 */
public interface SellerDAO {

    /**
     * Loads every seller currently stored.
     *
     * @return the list of stored sellers
     */
    List<Seller> loadSellers();

    /**
     * Persists the given collection of sellers, overwriting whatever was
     * previously stored.
     *
     * @param sellers the full collection of sellers to persist
     */
    void saveSellers(List<Seller> sellers);
}
