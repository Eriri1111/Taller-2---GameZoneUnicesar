package ui;

import com.gamezone.model.Client;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.SaleItem;
import com.gamezone.model.Seller;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

/**
 * Console-based user interface of GameZone Unicesar. Presents the main
 * menu with the ten required operations and delegates every business
 * decision to the service layer. This class never accesses the
 * persistence layer directly.
 */
public class ConsoleUI {

    private final ProductService productService;
    private final PersonService personService;
    private final SaleService saleService;
    private final Scanner scanner;

    /**
     * Creates the console UI wired to the three application services.
     *
     * @param productService service used for product operations
     * @param personService  service used for client and seller operations
     * @param saleService    service used for sale operations
     */
    public ConsoleUI(ProductService productService, PersonService personService, SaleService saleService) {
        this.productService = productService;
        this.personService = personService;
        this.saleService = saleService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the main application loop, showing the menu until the user
     * chooses to exit.
     */
    public void start() {
        boolean running = true;
        System.out.println("=== Welcome to GameZone Unicesar ===");

        while (running) {
            printMenu();
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1" -> registerVideogame();
                case "2" -> registerConsole();
                case "3" -> listProducts();
                case "4" -> registerClient();
                case "5" -> listClients();
                case "6" -> listSellers();
                case "7" -> registerSale();
                case "8" -> listAllSales();
                case "9" -> listSalesByClient();
                case "10" -> listSalesBySeller();
                case "0" -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("---- MAIN MENU ----");
        System.out.println("1. Register a new videogame");
        System.out.println("2. Register a new console");
        System.out.println("3. List all products");
        System.out.println("4. Register a new client");
        System.out.println("5. List all clients");
        System.out.println("6. List all sellers");
        System.out.println("7. Register a new sale");
        System.out.println("8. View full sales history");
        System.out.println("9. View purchase history of a client");
        System.out.println("10. View sales attended by a seller");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    private void registerVideogame() {
        System.out.println("--- Register videogame ---");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Price: ");
        double price = readDouble();
        System.out.print("Quantity: ");
        int quantity = readInt();
        System.out.print("Platform: ");
        String platform = scanner.nextLine();
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        System.out.print("Age rating: ");
        String ageRating = scanner.nextLine();

        String id = generateId();
        productService.registerVideogame(id, title, price, quantity, platform, genre, ageRating);
        System.out.println("Videogame registered with id: " + id);
    }

    private void registerConsole() {
        System.out.println("--- Register console ---");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Price: ");
        double price = readDouble();
        System.out.print("Quantity: ");
        int quantity = readInt();
        System.out.print("Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Generation: ");
        String generation = scanner.nextLine();

        String id = generateId();
        productService.registerConsole(id, title, price, quantity, brand, model, generation);
        System.out.println("Console registered with id: " + id);
    }

    private void listProducts() {
        System.out.println("--- Product inventory ---");
        List<Product> products = productService.listProducts();
        if (products.isEmpty()) {
            System.out.println("No products registered yet.");
            return;
        }
        for (Product product : products) {
            System.out.println("[" + product.getId() + "] " + product.getDescription());
        }
    }

    private void registerClient() {
        System.out.println("--- Register client ---");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        String id = generateId();
        personService.registerClient(id, name, phone, email);
        System.out.println("Client registered with id: " + id);
    }

    private void listClients() {
        System.out.println("--- Registered clients ---");
        List<Client> clients = personService.listClients();
        if (clients.isEmpty()) {
            System.out.println("No clients registered yet.");
            return;
        }
        for (Client client : clients) {
            System.out.println("[" + client.getId() + "] " + client.getName() + " - " + client.getRoleDescription());
        }
    }

    private void listSellers() {
        System.out.println("--- Registered sellers ---");
        List<Seller> sellers = personService.listSellers();
        if (sellers.isEmpty()) {
            System.out.println("No sellers registered yet.");
            return;
        }
        for (Seller seller : sellers) {
            System.out.println("[" + seller.getId() + "] " + seller.getName() + " - " + seller.getRoleDescription());
        }
    }

    private void registerSale() {
        System.out.println("--- Register sale ---");
        System.out.print("Client id: ");
        String clientId = scanner.nextLine().trim();
        System.out.print("Seller id: ");
        String sellerId = scanner.nextLine().trim();

        Map<String, Integer> items = new HashMap<>();
        boolean addingItems = true;

        while (addingItems) {
            System.out.print("Product id (or leave empty to finish): ");
            String productId = scanner.nextLine().trim();
            if (productId.isEmpty()) {
                addingItems = false;
                continue;
            }
            System.out.print("Quantity: ");
            int quantity = readInt();
            items.merge(productId, quantity, Integer::sum);
        }

        try {
            String saleId = generateId();
            Sale sale = saleService.registerSale(saleId, clientId, sellerId, items);
            System.out.println("Sale registered with id: " + sale.getId());
            System.out.printf("Total: %.2f%n", sale.calculateTotal());
        } catch (IllegalArgumentException e) {
            System.out.println("Could not register sale: " + e.getMessage());
        }
    }

    private void listAllSales() {
        System.out.println("--- Full sales history ---");
        printSales(saleService.listSales());
    }

    private void listSalesByClient() {
        System.out.print("Client id: ");
        String clientId = scanner.nextLine().trim();
        System.out.println("--- Purchase history for client " + clientId + " ---");
        printSales(saleService.getSalesByClient(clientId));
    }

    private void listSalesBySeller() {
        System.out.print("Seller id: ");
        String sellerId = scanner.nextLine().trim();
        System.out.println("--- Sales attended by seller " + sellerId + " ---");
        printSales(saleService.getSalesBySeller(sellerId));
    }

    private void printSales(List<Sale> sales) {
        if (sales.isEmpty()) {
            System.out.println("No sales found.");
            return;
        }
        for (Sale sale : sales) {
            System.out.println("Sale [" + sale.getId() + "] date=" + sale.getDate()
                    + " client=" + sale.getClientId() + " seller=" + sale.getSellerId());
            for (SaleItem item : sale.getItems()) {
                System.out.printf("    - %s x%d = %.2f%n",
                        item.getProductTitle(), item.getQuantity(), item.getSubtotal());
            }
            System.out.printf("  Total: %.2f%n", sale.calculateTotal());
        }
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid integer: ");
            }
        }
    }

    private double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private String generateId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
