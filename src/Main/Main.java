package Main;

import Dao.PersonRepository;
import Dao.ProductRepository;
import Dao.SaleRepository;
import service.PersonService;
import service.ProductService;
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

        ProductService productService = new ProductService(productRepository);
        PersonService personService = new PersonService(personRepository);
        SaleService saleService = new SaleService(saleRepository, productService, personService);

        ConsoleUI consoleUI = new ConsoleUI(productService, personService, saleService);
        consoleUI.start();
    }
}
