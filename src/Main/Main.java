package Main;

import Dao.PersonRepository;
import Dao.ProductRepository;
import Dao.PromotionRepository;
import Dao.SaleRepository;
import service.PersonService;
import service.ProductService;
import service.PromotionService;
import service.SaleService;
import ui.ConsoleUI;

/**
 * Entry point of the GameZone Unicesar application. Wires together the
 * persistence, service and user-interface layers and starts the console
 * menu. This class is the only place in the system responsible for
 * assembling the dependencies between layers.
 */
public class Main {

    private static final String DATA_FOLDER = "data";

    /**
     * Starts the GameZone Unicesar application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository(DATA_FOLDER + "/products.txt");
        PersonRepository personRepository = new PersonRepository(
                DATA_FOLDER + "/clients.txt",
                DATA_FOLDER + "/sellers.txt");
        SaleRepository saleRepository = new SaleRepository(DATA_FOLDER + "/sales.txt");
        PromotionRepository promotionRepository = new PromotionRepository(DATA_FOLDER + "/promotions.csv");

        ProductService productService = new ProductService(productRepository);
        PersonService personService = new PersonService(personRepository);
        PromotionService promotionService = new PromotionService(promotionRepository);
        SaleService saleService = new SaleService(saleRepository, productService, personService, promotionService);

        ConsoleUI consoleUI = new ConsoleUI(productService, personService, saleService, promotionService);
        consoleUI.start();
    }
}
