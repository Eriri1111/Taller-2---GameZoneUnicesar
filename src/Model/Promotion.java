package model;

import java.time.LocalDate;

/**
 * Abstract base class that represents any promotional campaign the store
 * can run. Holds the attributes common to every promotion type
 * (identifier, name, and the validity date range), while delegating the
 * discount calculation to each concrete subclass, since the rule used to
 * compute the discount differs between promotion types. This class cannot
 * be instantiated directly, since a "generic promotion" without a defined
 * calculation strategy has no meaning in the business domain.
 */
public abstract class Promotion {

    private String id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Creates a new promotion with the attributes shared by every type.
     *
     * @param id        unique identifier of the promotion
     * @param name      commercial name of the promotion
     * @param startDate first date the promotion is valid (inclusive)
     * @param endDate   last date the promotion is valid (inclusive)
     */
    protected Promotion(String id, String name, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Checks whether this promotion is in force on the given date, that
     * is, whether the date falls within the inclusive range defined by
     * {@link #getStartDate()} and {@link #getEndDate()}.
     *
     * @param date the date to check
     * @return {@code true} if the promotion is active on that date
     */
    public boolean isActive(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    /**
     * Calculates the monetary discount, in pesos, that this promotion
     * would grant to the given sale. Each subclass must provide its own
     * implementation, since the calculation rule depends on the specific
     * type of promotion. The rest of the system never needs to know which
     * concrete type is being evaluated, since it always interacts with
     * promotions through this abstract contract.
     *
     * @param sale the sale to evaluate
     * @return the discount amount, in pesos, that this promotion grants
     */
    public abstract double calculateDiscount(Sale sale);
}
