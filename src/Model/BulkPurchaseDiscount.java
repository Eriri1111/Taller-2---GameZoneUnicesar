package model;

import java.time.LocalDate;

/**
 * Promotion that applies a percentage discount over the total value of a
 * sale, but only when the sale includes at least a minimum quantity of
 * products (e.g. 10% off when the sale includes three or more products).
 */
public class BulkPurchaseDiscount extends Promotion {

    private int minimumQuantity;
    private double percentage;

    /**
     * Creates a new volume-based promotion.
     *
     * @param id              unique identifier of the promotion
     * @param name            commercial name of the promotion
     * @param startDate       first date the promotion is valid (inclusive)
     * @param endDate         last date the promotion is valid (inclusive)
     * @param minimumQuantity minimum total number of units required in the sale
     * @param percentage      discount percentage applied to the sale total (0-100)
     */
    public BulkPurchaseDiscount(String id, String name, LocalDate startDate, LocalDate endDate,
                                 int minimumQuantity, double percentage) {
        super(id, name, startDate, endDate);
        this.minimumQuantity = minimumQuantity;
        this.percentage = percentage;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    /**
     * Calculates the discount as the configured percentage applied to the
     * full total of the sale, but only when the total number of units in
     * the sale reaches the configured minimum. Otherwise, no discount is
     * granted.
     *
     * @param sale the sale to evaluate
     * @return the discount amount, in pesos, or zero if the minimum
     *         quantity requirement is not met
     */
    @Override
    public double calculateDiscount(Sale sale) {
        int totalUnits = 0;
        for (SaleItem item : sale.getItems()) {
            totalUnits += item.getQuantity();
        }

        if (totalUnits >= minimumQuantity) {
            return sale.calculateTotal() * (percentage / 100.0);
        }
        return 0.0;
    }
}
