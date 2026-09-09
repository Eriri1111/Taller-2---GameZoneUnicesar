# Promotion analysis for GameZone Unicesar

This document answers the five guiding questions requested for the promotion module design.

1. Class hierarchy and polymorphism

The three promotion types share common attributes (id, name, startDate, endDate) and common behaviors (checking validity and computing a discount for a sale). This is modeled with an abstract base class Promotion and three concrete subclasses: PercentageDiscount, CategoryDiscount and BulkPurchaseDiscount.

The mechanism that allows each promotion to compute its discount differently without the rest of the system knowing about concrete types is polymorphism via an abstract method. Promotion declares an abstract calculateDiscount(Sale sale) method which each subclass implements with its own logic. The rest of the system works with Promotion references and calls calculateDiscount polymorphically.

2. Declaration of the discount calculation method

In the base class Promotion the discount calculation is declared as an abstract method:

public abstract double calculateDiscount(Sale sale);

This declaration guarantees that every concrete subclass must provide its own implementation. It enforces at compile-time that no concrete Promotion class can be instantiated without implementing the calculation logic.

3. Location of the "best promotion" selection logic

The logic that selects the promotion that yields the largest monetary discount belongs to PromotionService.

Justification:
- PromotionService operates at the service/business layer and already knows how to load and filter promotions (persistence concerns are delegated to PromotionRepository). Selecting the best promotion is a business rule that coordinates multiple Promotion instances for a given Sale, so it belongs in the service layer.
- Placing it in PromotionService preserves separation of concerns and keeps domain model classes small and focused: Promotion describes a single promotion and how to compute its discount; PromotionService implements promotion-related business processes that coordinate promotions.

Why not in Sale or the console menu:
- Sale is a domain entity that represents a single sale and should not be responsible for orchestration across many promotions or access to repositories/services. Embedding selection logic in Sale would mix persistence/service concerns into the model and break layering.
- The console menu is part of the UI layer and should not implement business rules. Putting selection logic there would prevent reuse of the rule by other UI or API clients and would violate single-responsibility and layering principles.

4. Modifications to Sale and generateReceipt

Required changes to Sale:
- Add two private attributes: String appliedPromotionName and double discountAmount, with getters and setters.
- Keep existing attributes and behavior unchanged otherwise.

Required changes to generateReceipt:
- Calculate and display:
  - Subtotal (sum of item prices)
  - Discount applied (the numeric discount amount and the appliedPromotionName)
  - Total final (subtotal minus discount)

Do these changes break existing behavior?
- No. Changes are additive. If no promotion applies, appliedPromotionName remains null (or an empty string) and discountAmount is zero, so total equals subtotal. Existing flows that don't use the new fields continue to function. Ensure generateReceipt handles null/empty appliedPromotionName gracefully.

5. Where to validate promotion's active period

Responsibility should be shared but primarily implemented in the Promotion class via a method boolean isActive(LocalDate date).

Justification:
- Promotion encapsulates its own validity rules (startDate and endDate), so it should expose isActive(LocalDate) so callers can easily check whether a promotion is applicable for a given date.
- PromotionService uses Promotion.isActive(LocalDate.now()) when listing active promotions or when selecting candidates for a Sale. This keeps date comparison logic centralized in Promotion while allowing the service to compose and filter promotions.

Summary

- Use an abstract Promotion base class with an abstract calculateDiscount(Sale) method and an isActive(LocalDate) helper.
- Implement PercentageDiscount, CategoryDiscount and BulkPurchaseDiscount subclasses with concrete calculation logic.
- Implement persistence in PromotionRepository (CSV file) and business processes in PromotionService, including findBestPromotionFor(Sale).
- Keep selection logic in PromotionService (service/business layer), not in Sale or UI.
- Make additive changes to Sale to store applied promotion name and discount amount and update generateReceipt to display the new fields.

