/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Model.Product;
import Model.VideoGame;
import Model.Console;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class ProductRepository {

    private final String filePath;

    public ProductRepository(String filePath) {
        this.filePath = filePath;
    }

    public void saveProducts(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Product p : products) {
                if (p instanceof VideoGame) {
                    VideoGame game = (VideoGame) p;
                    writer.write(String.format("GAME;%s;%s;%.2f;%d;%s;%s;%s%n",
                            game.getId(), game.getTitle(), game.getPrice(), game.getStockQuantity(),
                            game.getPlatform(), game.getGenre(), game.getAgeRating()));
                } else if (p instanceof Console) {
                    Console console = (Console) p;
                    writer.write(String.format("CONSOLE;%s;%s;%.2f;%d;%s;%s;%s%n", console.getId(),
                            console.getTitle(), console.getPrice(), console.getStockQuantity(),
                            console.getBrand(), console.getModel(), console.getGeneration()
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving products to file: " + e.getMessage());
        }
    }

    public List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return products;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length < 8) {
                    continue;
                }

                String type = data[0];
                String id = data[1];
                String title = data[2];
                double price = Double.parseDouble(data[3].replace(",", "."));
                int stock = Integer.parseInt(data[4]);

                if ("GAME".equals(type)) {
                    String platform = data[5];
                    String genre = data[6];
                    String ageRating = data[7];
                    products.add(new VideoGame(id, title, price, stock, platform, genre, ageRating));
                } else if ("CONSOLE".equals(type)) {

                    String brand = data[5];
                    String model = data[6];
                    String generation = data[7];

                    products.add(
                            new Console(id, title, price, stock, brand, model, generation));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading products from file: " + e.getMessage());
        }

        return products;
    }

}
