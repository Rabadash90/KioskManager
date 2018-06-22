package ch.alexandrahauri.kiosk.business;

/**
 * Articles Class, contains name, price and category
 *
 * @author: Alexandra
 * @since: 03.06.2018
 **/
public class Article {
    private String name;
    private double price;
    private Category category;

    public Article(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
