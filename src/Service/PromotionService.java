package service;

import Dao.PromotionDAO;
import model.BulkPurchaseDiscount;
import model.CategoryDiscount;
import model.PercentageDiscount;
import model.Promotion;
import model.Sale;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Contains the business rules related to promotions: registration,
 * listing and selection of the best applicable promotion for a sale.
 * This is the only layer authorized to invoke {@link PromotionDAO}; the
 * user interface never accesses persistence directly, and the model
 * layer never decides which promotion "wins" for a sale. The service
 * depends on the DAO interface rather than on any concrete, file-based
 * implementation.
 */
public class PromotionService {

    private final PromotionDAO promotionDAO;
    private final List<Promotion> promotions;

    /**
     * Creates the service and loads the current promotion data from disk.
     *
     * @param promotionDAO DAO used to persist promotions
     */
    public PromotionService(PromotionDAO promotionDAO) {
        this.promotionDAO = promotionDAO;
        this.promotions = new ArrayList<>(promotionDAO.loadAll());
    }

    /**
     * Registers a new percentage-based promotion and persists the change.
     *
     * @param id         unique identifier for the new promotion
     * @param name       commercial name of the promotion
     * @param startDate  first date the promotion is valid (inclusive)
     * @param endDate    last date the promotion is valid (inclusive)
     * @param percentage discount percentage applied to the sale total (0-100)
     * @return the newly created promotion
     */
    public PercentageDiscount registerPercentageDiscount(String id, String name, LocalDate startDate,
                                                           LocalDate endDate, double percentage) {
        PercentageDiscount promotion = new PercentageDiscount(id, name, startDate, endDate, percentage);
        promotions.add(promotion);
        persist();
        return promotion;
    }

    /**
     * Registers a new category-based promotion and persists the change.
     *
     * @param id             unique identifier for the new promotion
     * @param name           commercial name of the promotion
     * @param startDate      first date the promotion is valid (inclusive)
     * @param endDate        last date the promotion is valid (inclusive)
     * @param percentage     discount percentage applied to the target category (0-100)
     * @param targetCategory category the discount applies to ("VIDEOGAME" or "CONSOLE")
     * @return the newly created promotion
     */
    public CategoryDiscount registerCategoryDiscount(String id, String name, LocalDate startDate,
                                                       LocalDate endDate, double percentage,
                                                       String targetCategory) {
        CategoryDiscount promotion = new CategoryDiscount(id, name, startDate, endDate, percentage, targetCategory);
        promotions.add(promotion);
        persist();
        return promotion;
    }

    /**
     * Registers a new volume-based promotion and persists the change.
     *
     * @param id              unique identifier for the new promotion
     * @param name            commercial name of the promotion
     * @param startDate       first date the promotion is valid (inclusive)
     * @param endDate         last date the promotion is valid (inclusive)
     * @param minimumQuantity minimum total number of units required in the sale
     * @param percentage      discount percentage applied to the sale total (0-100)
     * @return the newly created promotion
     */
    public BulkPurchaseDiscount registerBulkPurchaseDiscount(String id, String name, LocalDate startDate,
                                                               LocalDate endDate, int minimumQuantity,
                                                               double percentage) {
        BulkPurchaseDiscount promotion =
                new BulkPurchaseDiscount(id, name, startDate, endDate, minimumQuantity, percentage);
        promotions.add(promotion);
        persist();
        return promotion;
    }

    /**
     * Returns every promotion currently registered, regardless of whether
     * it is active, expired or scheduled for the future.
     *
     * @return a list of all promotions
     */
    public List<Promotion> listAllPromotions() {
        return new ArrayList<>(promotions);
    }

    /**
     * Returns the promotions that are currently in force, that is, whose
     * validity range includes today's date.
     *
     * @return the list of currently active promotions
     */
    public List<Promotion> listActivePromotions() {
        LocalDate today = LocalDate.now();
        return promotions.stream()
                .filter(promotion -> promotion.isActive(today))
                .collect(Collectors.toList());
    }

    /**
     * Determines, among the currently active promotions, which one grants
     * the greatest monetary discount to the given sale. This is the
     * single place in the system where the "best promotion wins" business
     * rule is decided, keeping that decision out of {@code Sale} and out
     * of the console menu.
     *
     * @param sale the sale to evaluate
     * @return the promotion that grants the greatest discount, or
     *         {@code null} if no active promotion applies or every active
     *         promotion grants a discount of zero
     */
    public Promotion findBestPromotionFor(Sale sale) {
        LocalDate today = LocalDate.now();
        Promotion bestPromotion = null;
        double bestDiscount = 0.0;

        for (Promotion promotion : promotions) {
            if (!promotion.isActive(today)) {
                continue;
            }
            double discount = promotion.calculateDiscount(sale);
            if (discount > bestDiscount) {
                bestDiscount = discount;
                bestPromotion = promotion;
            }
        }

        return bestPromotion;
    }

    /**
     * Finds a promotion by its identifier.
     *
     * @param id identifier of the promotion to search for
     * @return an {@link Optional} containing the promotion if found
     */
    public Optional<Promotion> findById(String id) {
        return promotions.stream().filter(promotion -> promotion.getId().equals(id)).findFirst();
    }

    /**
     * Persists the current in-memory list of promotions to disk.
     */
    public void persist() {
        promotionDAO.saveAll(promotions);
    }
}
