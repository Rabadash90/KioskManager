package ch.alexandrahauri.kiosk.business;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * The KioskManager handles all the main Tasks.
 * KioskManager is a singleton
 *
 * @author: Alexandra
 * @since: 16.06.2018
 **/
public class KioskManager {
    private static final Logger logger = LoggerFactory.getLogger(KioskManager.class);
    private HashSet<Kiosk> kiosks = new HashSet<>();
    private static KioskManager instance = new KioskManager();
    private Customer customer;
    private Supplier defaultSupplier;

    private KioskManager(){
        initialize();
    };

    public static KioskManager getInstance(){
        return instance;
    }

    /**
     * creates an initial Map of KioskArticles from the supplier
     *
     * @param supplierArticles
     * @param initialAmount
     * @return
     */
    private HashMap<Article, Integer> createInitialKioskArticles ( HashSet<Article> supplierArticles , Integer initialAmount) {
        HashMap<Article, Integer> articlesMap  = new HashMap<>();
        for (Article article : supplierArticles) {
            articlesMap.put(article, initialAmount);
        }
        return articlesMap;
    }

    private void initialize() {
        HashSet<Article> supplierArticles = new HashSet<>();
        supplierArticles = initializeArticles();
        Integer initialAmount = 10;
        createInitialKioskArticles(supplierArticles, initialAmount);
        defaultSupplier = new Supplier("Coop", supplierArticles);
        Kiosk shoppi = new Kiosk("Shoppi", "Spreitenbach", 1000, "Müller Heinz");
        Kiosk bahnhofKiosk = new Kiosk("Bahnhofkiosk", "Dietikon", 500, "Keller Laurenz");
        Kiosk schlaecker = new Kiosk("Schläcker", "Schlieren", 1000, "Maurer Peter");
        Kiosk laedeli = new Kiosk("Lädeli", "Altstetten", 1000, "Schneider Fritz");


        shoppi.setSupplier(defaultSupplier);
        bahnhofKiosk.setSupplier(defaultSupplier);
        schlaecker.setSupplier(defaultSupplier);
        laedeli.setSupplier(defaultSupplier);

        shoppi.setArticles(createInitialKioskArticles(supplierArticles, initialAmount));
        bahnhofKiosk.setArticles(createInitialKioskArticles(supplierArticles, initialAmount));
        schlaecker.setArticles(createInitialKioskArticles(supplierArticles, initialAmount));
        laedeli.setArticles(createInitialKioskArticles(supplierArticles, initialAmount));

        shoppi.setOpen(true);
        bahnhofKiosk.setOpen(false);
        schlaecker.setOpen(false);
        laedeli.setOpen(false);

        kiosks.add(shoppi);
        kiosks.add(bahnhofKiosk);
        kiosks.add(schlaecker);
        kiosks.add(laedeli);
    }

    /**
     * Default Set of Articles to initialize
     *
     * @return Set of Articles
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


    /**
     * gets articles from seller, a seller could be either a kiosk or a supplier
     *
     * @param sellerName
     * @param isKioskSeller
     * @return returns a map of articles and stock of Articles
     */
    public HashMap<Article, Integer> getArticles(String sellerName, Boolean isKioskSeller) {
        HashMap<Article, Integer> articlesToReturn = new HashMap<>();
        for (Kiosk kiosk: kiosks) {
            if (kiosk.getName().equals(sellerName)) {
                if (isKioskSeller) {
                    return kiosk.getArticles();
                } else {
                    Iterator iterator = kiosk.getSupplier().getArticles().iterator();
                    Integer initialAmount = 9999;
                    while (iterator.hasNext()) {
                        articlesToReturn.put((Article) iterator.next(), initialAmount);
                    }
                    return articlesToReturn;
                }
            }
        }
        return articlesToReturn;
    }

    /**
     * buy Articles, checks if kiosk had enough money
     *
     * @param articlesSelectedToSale
     * @param sellerName
     * @param isKioskSeller
     * @return if the task was successful
     */
    public Boolean buyArticles(HashMap<String, Integer> articlesSelectedToSale, String sellerName, Boolean isKioskSeller) {
        Boolean isOk = true;
        HashMap<Article, Integer> articlesToReturn = new HashMap<>();
        Double totalCost = 0.0;
        for (Kiosk kiosk: kiosks) {
            if (kiosk.getName().equals(sellerName)) {
                if (isKioskSeller) {
                    // artikelanzahl abziehen vom kiosk
                    HashMap<Article, Integer> kioskArticles = kiosk.getArticles();
                    Iterator<Map.Entry<Article, Integer>> iterator = kioskArticles.entrySet().iterator();
                    Boolean needToBeAdult = false;
                    while (iterator.hasNext()) {
                        Map.Entry  entry = (Map.Entry)iterator.next();
                        Article article = (Article) entry.getKey();
                        if (articlesSelectedToSale.containsKey(article.getName())) {
                            if (article.getCategory().equals(Category.ALCOHOL) || article.getCategory().equals(Category.TOBACCO)) {
                                needToBeAdult = true;
                            }
                            Integer remainingQuantity = (Integer) entry.getValue() - articlesSelectedToSale.get(article.getName());
                            totalCost += (article.getPrice() * articlesSelectedToSale.get(article.getName()));
                            articlesToReturn.put(article, remainingQuantity);
                        }
                    }
                    if (needToBeAdult && customer.getAge() < 18) {
                        isOk = false;
                    }
                    // we got the total cost now
                    // TODO make it synchronized
                    iterator = articlesToReturn.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry  entry = (Map.Entry)iterator.next();
                        kioskArticles.replace((Article) entry.getKey(), (Integer) entry.getValue());
                    }
                    kiosk.setArticles(kioskArticles);


                } else {
                    try {
                        Iterator iterator = kiosk.getSupplier().getArticles().iterator();
                        while (iterator.hasNext()) {
                            Article supplierArticle = (Article) iterator.next();
                            if (articlesSelectedToSale.containsKey(supplierArticle.getName())) {
                                totalCost += (supplierArticle.getPrice() * articlesSelectedToSale.get(supplierArticle.getName()));
                                articlesToReturn.put(supplierArticle, articlesSelectedToSale.get(supplierArticle.getName()));
                            }
                        }

                        // TODO make it synchronized
                        HashMap<Article, Integer> articles = kiosk.getArticles();
                        iterator = articlesToReturn.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) iterator.next();
                            Integer articleNewAmount = articles.get(entry.getKey()) + (Integer) entry.getValue();
                            articles.replace((Article) entry.getKey(), articleNewAmount);
                        }
                        // set Money for kiosk
                        if (kiosk.getCash() < totalCost) {
                            throw new OutOfMoneyException("Kiosk has " + kiosk.getCash() + " and wants to buy articles with the total cost of " + totalCost + ".");
                        }
                        kiosk.setCash(kiosk.getCash() - totalCost);
                        kiosk.setArticles(articles);
                    } catch (OutOfMoneyException e) {
                        isOk = false;
                        logger.error(e.getMessage());
                    }
                }


            }
        }
        return isOk;
    }

    /**
     * gets kiosk Informations
     *
     * @return a map of KioskNames and status isOpen
     */
    public HashMap<String, Boolean> getKiosksInfos() {
        HashMap<String, Boolean> kiosksInfos = new HashMap<String, Boolean>();

        for (Kiosk kiosk: kiosks) {
            kiosksInfos.put(kiosk.getName(), kiosk.getOpen());
        }

        return kiosksInfos;
    }

    public void setKioskIsOpen(String kioskName, Boolean isOpen) {
        for (Kiosk kiosk: kiosks) {
            if (kiosk.getName().equals(kioskName)) {
                kiosk.setOpen(isOpen);
            }
        }
    }

    public HashSet<Kiosk> getKiosks() {
        return kiosks;
    }

    /**
     * creates a kiosk with articles from defaultSupplier
     *
     * @param kioskName
     * @param location
     * @param cash
     * @param employeeName
     */
    public void createKiosk(String kioskName, String location, Double cash, String employeeName) {
        Kiosk createdKiosk = new Kiosk(kioskName, location, cash, employeeName);
        createdKiosk.setSupplier(defaultSupplier);
        createdKiosk.setArticles(createInitialKioskArticles(defaultSupplier.getArticles(), 10));
        createdKiosk.setOpen(false);
        kiosks.add(createdKiosk);
    }


    /**
     * creates an Excel with the Inventory of the current articles
     *
      * @param kioskName
     */
    public void createInventory(String kioskName) {

        HashMap<Article, Integer> articles = new HashMap<>();
        for (Kiosk kiosk: kiosks) {
            if (kiosk.getName().equals(kioskName)) {
                articles = kiosk.getArticles();
            }
        }

        List<Article> sortedArticles = new ArrayList<>();

        Iterator iterator = articles.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry  entry = (Map.Entry)iterator.next();
            sortedArticles.add((Article) entry.getKey());
        }
        Collections.sort(sortedArticles, new PriceComparator());


        FileOutputStream fileOut = null;
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Articles from " + kioskName);

            // Title Row
            XSSFRow titleRow = sheet.createRow(0);
            XSSFCell titleArticleNameCell = titleRow.createCell(0);
            titleArticleNameCell.setCellValue("Name");
            XSSFCell titleArticlePriceCell = titleRow.createCell(1);
            titleArticlePriceCell.setCellValue("Price");
            XSSFCell titleArticleStockCell = titleRow.createCell(2);
            titleArticleStockCell.setCellValue("Stock");
            Integer currentRowNumber = 0;
            // Article Rows
            for (Article article : sortedArticles) {
                currentRowNumber++;
                XSSFRow row = sheet.createRow(currentRowNumber);
                XSSFCell articleNameCell = row.createCell(0);
                articleNameCell.setCellValue(article.getName());
                XSSFCell articlePriceCell = row.createCell(1);
                articlePriceCell.setCellValue(Double.toString(article.getPrice()));
                XSSFCell articleStockCell = row.createCell(2);
                articleStockCell.setCellValue(Integer.toString(articles.get(article)));
            }
            fileOut = new FileOutputStream( "export/Inventar.xlsx");
            wb.write(fileOut);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOut != null) {
                try {
                    fileOut.flush();
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * creates a customer
     *
     * @param name
     * @param age
     */
    public void createCustomer(String name, Integer age) {
        customer = new Customer(name, age);
    }

    /**
     * Starts five threads for automatic test
     *
     * @param kioskName
     */
    public void automaticTest(String kioskName) {
        ThreadBuy thread1 = new ThreadBuy("Meier", kioskName);
        thread1.start();
        ThreadBuy thread2 = new ThreadBuy("Simon", kioskName);
        thread2.start();
        ThreadBuy thread3 = new ThreadBuy("Brenn", kioskName);
        thread3.start();
        ThreadBuy thread4 = new ThreadBuy("Apfel", kioskName);
        thread4.start();
        ThreadBuy thread5 = new ThreadBuy("Kaeufer", kioskName);
        thread5.start();
    }

    /**
     * Selects one Article of Each article which the kiosk has.
     *
     * @param articles
     * @return a map of selected Articles and the selected number
     */
    public HashMap<String, Integer> automaticSelectOneArticleOfEach(HashMap<Article, Integer> articles) {
        HashMap<String, Integer> selectedArticles = new HashMap<String, Integer>();
        Iterator iterator = articles.entrySet().iterator();
        // select one Article of each
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            selectedArticles.put(((Article)entry.getKey()).getName(),1);
        }
        return selectedArticles;
    }
}
