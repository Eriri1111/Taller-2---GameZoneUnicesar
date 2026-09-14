package service;

import Model.Product;
import Model.Sale;
import Model.SaleItem;
import Dao.SaleDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Contains the business rules related to sales: registration, validation
 * and querying. This class coordinates the product and person modules to
 * validate a sale, and is the only layer authorized to invoke
 * {@link SaleDAO}. The service depends on the DAO interface rather than on
 * any concrete, file-based implementation.
 */
public class SaleService {

    private final SaleDAO saleDAO;
    private final ProductService productService;
    private final PersonService personService;
    private final List<Sale> sales;

    /**
     * Creates the service and loads the current sales data from disk.
     *
     * @param saleDAO        DAO used to persist sales
     * @param productService service used to validate and update stock
     * @param personService  service used to validate people and update
     *                        purchase history
     */
    public SaleService(SaleDAO saleDAO, ProductService productService,
                        PersonService personService) {
        this.saleDAO = saleDAO;
        this.productService = productService;
        this.personService = personService;
        this.sales = new ArrayList<>(saleDAO.loadAll());
    }

    /**
     * Registers a new sale after validating every business rule: the
     * client and seller must exist, the sale must contain at least one
     * product, and there must be sufficient stock for every requested
     * product. If validation succeeds, the stock of each product is
     * decreased, the sale is stored, and the sale is added to the
     * client's purchase history.
     *
     * @param saleId          unique identifier for the new sale
     * @param clientId        identifier of the client making the purchase
     * @param sellerId        identifier of the seller attending the sale
     * @param requestedItems  map of product identifier to requested quantity
     * @return the registered sale
     * @throws IllegalArgumentException if any business rule is violated
     */
    public Sale registerSale(String saleId, String clientId, String sellerId,
                              Map<String, Integer> requestedItems) {

        if (requestedItems == null || requestedItems.isEmpty()) {
            throw new IllegalArgumentException("A sale must contain at least one product.");
        }

        if (personService.findClientById(clientId).isEmpty()) {
            throw new IllegalArgumentException("Client not found: " + clientId);
        }

        if (personService.findSellerById(sellerId).isEmpty()) {
            throw new IllegalArgumentException("Seller not found: " + sellerId);
        }

        for (Map.Entry<String, Integer> entry : requestedItems.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();

            Optional<Product> product = productService.findById(productId);
            if (product.isEmpty()) {
                throw new IllegalArgumentException("Product not found: " + productId);
            }
            if (!productService.hasSufficientStock(productId, quantity)) {
                throw new IllegalArgumentException(
                        "Insufficient stock for product: " + product.get().getTitle());
            }
        }

        Sale sale = new Sale(saleId, LocalDate.now(), clientId, sellerId);

        for (Map.Entry<String, Integer> entry : requestedItems.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = productService.findById(productId).orElseThrow();

            sale.addItem(new SaleItem(product.getId(), product.getTitle(), product.getPrice(), quantity));
            productService.decreaseStock(productId, quantity);
        }

        sales.add(sale);
        persist();
        personService.addPurchaseToClient(clientId, saleId);

        return sale;
    }

    /**
     * Returns the complete history of sales registered in the store.
     *
     * @return a list of all sales
     */
    public List<Sale> listSales() {
        return new ArrayList<>(sales);
    }

    /**
     * Returns the purchase history of a specific client.
     *
     * @param clientId identifier of the client
     * @return the list of sales made by that client
     */
    public List<Sale> getSalesByClient(String clientId) {
        return sales.stream()
                .filter(sale -> sale.getClientId().equals(clientId))
                .collect(Collectors.toList());
    }

    /**
     * Returns the sales attended by a specific seller.
     *
     * @param sellerId identifier of the seller
     * @return the list of sales attended by that seller
     */
    public List<Sale> getSalesBySeller(String sellerId) {
        return sales.stream()
                .filter(sale -> sale.getSellerId().equals(sellerId))
                .collect(Collectors.toList());
    }

    /**
     * Persists the current in-memory list of sales to disk.
     */
    public void persist() {
        saleDAO.saveAll(sales);
    }
}
