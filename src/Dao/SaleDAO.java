package Dao;

import model.Sale;

/**
 * Data Access Object contract for {@link Sale} persistence. Declares the
 * operations the service layer relies on to load and save the sales
 * history, independently of how or where the data is actually stored.
 */
public interface SaleDAO extends Dao<Sale> {
}
