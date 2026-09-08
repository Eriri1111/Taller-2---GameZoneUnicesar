/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main.Dao;

import Main.Product;
import Main.VideoGame;
import Main.Console;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
                            console.getTitle(),console.getPrice(), console.getStockQuantity(),
                            console.getBrand(),console.getModel(),console.getGeneration()
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving products to file: " + e.getMessage());
        }
    }

    
    
}
