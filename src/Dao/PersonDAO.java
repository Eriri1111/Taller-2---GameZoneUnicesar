package Dao;

import Model.Seller;
import java.util.List;

/**
 * Data Access Object contract for the people module. Combines
 * {@link ClientDAO} and {@link SellerDAO}, since both clients and sellers
 * are handled by a single persistence class in this module, while still
 * exposing each responsibility through its own focused interface.
 */
public interface PersonDAO extends ClientDAO, SellerDAO {

    public void saveSellers(List<Seller> sellers);
}
