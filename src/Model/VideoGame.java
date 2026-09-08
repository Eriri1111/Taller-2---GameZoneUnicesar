package com.gamezone.model;

/**
 * Represents a videogame sold by the store. In addition to the common
 * attributes inherited from {@link Product}, a videogame is characterized
 * by the platform it was developed for, its genre and its recommended
 * age rating.
 */
public class Videogame extends Product {

    private String platform;
    private String genre;
    private String ageRating;

    /**
     * Creates a new videogame.
     *
     * @param id        unique identifier of the product
     * @param title     title of the videogame
     * @param price     unit price
     * @param quantity  quantity available in inventory
     * @param platform  platform the game was developed for (e.g. PS5, PC)
     * @param genre     genre of the videogame (e.g. RPG, action, sports)
     * @param ageRating recommended age rating (e.g. E, T, M)
     */
    public Videogame(String id, String title, double price, int quantity,
                      String platform, String genre, String ageRating) {
        super(id, title, price, quantity);
        this.platform = platform;
        this.genre = genre;
        this.ageRating = ageRating;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    @Override
    public String getDescription() {
        return String.format(
                "Videogame: %s | Platform: %s | Genre: %s | Age rating: %s | Price: %.2f | Stock: %d",
                getTitle(), platform, genre, ageRating, getPrice(), getQuantity());
    }
}
