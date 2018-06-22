package ch.alexandrahauri.kiosk.business;


import java.util.HashSet;

/**
 * Supplier Class, contains name and articles
 *
 * @author: Alexandra
 * @since: 03.06.2018
 **/
public class Supplier {
    private String name;
    private HashSet<Article> articles = new HashSet<>();

    public Supplier(String name, HashSet<Article> articles) {
        this.name = name;
        this.articles = articles;
    }

    public HashSet<Article> getArticles() {
        return articles;
    }
}
