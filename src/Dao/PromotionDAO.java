package Dao;

import model.Promotion;

/**
 * Data Access Object contract for {@link Promotion} persistence. Declares
 * the operations the service layer relies on to load and save the
 * registered promotions, independently of how or where the data is
 * actually stored.
 */
public interface PromotionDAO extends Dao<Promotion> {
}
