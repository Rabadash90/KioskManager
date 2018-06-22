package ch.alexandrahauri.kiosk.business;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Initializes the start DataSet of Kiosk, Supplier and Employee
 *
 * @author: Alexandra
 * @since: 16.06.2018
 **/
public class Initializer {
    public Initializer() {
        HashSet<Article> supplierArticles = supplierArticles = initializeArticles();

        HashMap<Article, Integer> initialKioskArticles = new HashMap<>();
        Integer initialAmount = 10;
        for (Article article : supplierArticles) {
            initialKioskArticles.put(article, initialAmount);
        }
        Supplier supplier = new Supplier("Coop", supplierArticles);
        Kiosk shoppi = new Kiosk("Shoppi", "Spreitenbach", 1000, "Müller Heinz");
        Kiosk bahnhofKiosk = new Kiosk("Bahnhofkiosk", "Dietikon", 500, "Keller Laurenz");
        Kiosk schlaecker = new Kiosk("Schläcker", "Schlieren", 1000, "Maurer Peter");
        Kiosk laedeli = new Kiosk("Lädeli", "Altstetten", 1000, "Schneider Fritz");


        shoppi.setSupplier(supplier);
        bahnhofKiosk.setSupplier(supplier);
        schlaecker.setSupplier(supplier);
        laedeli.setSupplier(supplier);

        shoppi.setArticles(initialKioskArticles);
        bahnhofKiosk.setArticles(initialKioskArticles);
        schlaecker.setArticles(initialKioskArticles);
        laedeli.setArticles(initialKioskArticles);

        shoppi.setOpen(true);
        bahnhofKiosk.setOpen(false);
        schlaecker.setOpen(false);
        laedeli.setOpen(false);
    }

    /**
     * Initializes Articles
     * @return articles, set of articles
     */
    private HashSet<Article> initializeArticles() {
        HashSet<Article> articles = new HashSet<>();
        articles.add(new Article("Radeberger", 2.00, Category.ALCOHOL));
        articles.add(new Article("Sprite", 1.50, Category.SOFTDRINK));
        articles.add(new Article("Marlboro", 7.50, Category.TOBACCO));
        articles.add(new Article("Twix", 1.50, Category.SNACK_SWEET));
        articles.add(new Article("Schinkensandwich", 6.00, Category.SNACK_SANDWICH));
        articles.add(new Article("Erdnüsse", 4.50, Category.SNACK_SALTY));
        articles.add(new Article("Snickers", 1.20, Category.SNACK_SWEET));
        articles.add(new Article("Mars", 1.20, Category.SNACK_SWEET));
        articles.add(new Article("Apfel", 1.00, Category.SNACK_FRUIT));
        articles.add(new Article("NZZ", 5.00, Category.MAGAZINE_LOCAL));
        articles.add(new Article("World News", 5.00, Category.MAGAZINE_INTERNATIONAL));
        articles.add(new Article("Blick", 1.00, Category.MAGAZINE_GOSSIP));
        articles.add(new Article("Spongebob", 3.00, Category.MAGAZINE_KIDS));
        return articles;
    }
}
