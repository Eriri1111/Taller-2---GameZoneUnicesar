package model;

import java.time.LocalDate;

/**
 * Promotion that applies a flat percentage discount over the total value
 * of a sale, regardless of which products it contains (e.g. 15% off the
 * whole purchase).
 */
public class PercentageDiscount extends Promotion {

    private double percentage;

    /**
     * Creates a new percentage-based promotion.
     *
     * @param id         unique identifier of the promotion
     * @param name       commercial name of the promotion
     * @param startDate  first date the promotion is valid (inclusive)
     * @param endDate    last date the promotion is valid (inclusive)
     * @param percentage discount percentage applied to the sale total (0-100)
     */
    public PercentageDiscount(String id, String name, LocalDate startDate, LocalDate endDate,
                               double percentage) {
        super(id, name, startDate, endDate);
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    /**
     * Calculates the discount as the configured percentage applied to the
     * full total of the sale.
     *
     * @param sale the sale to evaluate
     * @return the discount amount, in pesos
     */
    @Override
    public double calculateDiscount(Sale sale) {
        return sale.calculateTotal() * (percentage / 100.0);
    }
}
