package model;

import java.time.LocalDate;

/**
 * Promotion that applies a percentage discount only to the products of a
 * specific category within a sale (e.g. 20% off videogames, even if the
 * sale also contains consoles). Products outside the target category are
 * left out of the discount calculation entirely.
 */
public class CategoryDiscount extends Promotion {

    private double percentage;
    private String targetCategory;

    /**
     * Creates a new category-based promotion.
     *
     * @param id             unique identifier of the promotion
     * @param name           commercial name of the promotion
     * @param startDate      first date the promotion is valid (inclusive)
     * @param endDate        last date the promotion is valid (inclusive)
     * @param percentage     discount percentage applied to the target category (0-100)
     * @param targetCategory category the discount applies to ("VIDEOGAME" or "CONSOLE")
     */
    public CategoryDiscount(String id, String name, LocalDate startDate, LocalDate endDate,
                             double percentage, String targetCategory) {
        super(id, name, startDate, endDate);
        this.percentage = percentage;
        this.targetCategory = targetCategory;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(String targetCategory) {
        this.targetCategory = targetCategory;
    }

    /**
     * Calculates the discount by summing the subtotal of only the sale
     * items that belong to the target category and applying the
     * configured percentage to that partial sum.
     *
     * @param sale the sale to evaluate
     * @return the discount amount, in pesos
     */
    @Override
    public double calculateDiscount(Sale sale) {
        double categorySubtotal = 0.0;
        for (SaleItem item : sale.getItems()) {
            if (targetCategory != null && targetCategory.equalsIgnoreCase(item.getCategory())) {
                categorySubtotal += item.getSubtotal();
            }
        }
        return categorySubtotal * (percentage / 100.0);
    }
}
