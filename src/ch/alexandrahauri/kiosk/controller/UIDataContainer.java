package ch.alexandrahauri.kiosk.controller;

import ch.alexandrahauri.kiosk.business.Article;
import ch.alexandrahauri.kiosk.business.KioskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Data Container in the Business View, UIDataContainer calls the Kioskmanager in the Business package
 * UIDataContainer is a singleton
 *
 * @author: Alexandra
 * @since: 16.06.2018
 **/
public class UIDataContainer {

    private static final Logger logger = LoggerFactory.getLogger(UIDataContainer.class);

    private HashMap<String, Boolean> kiosksInfos = new HashMap<String, Boolean>();
    private String selectedKiosk;
    private HashMap<Article, Integer> articlesForSale = new HashMap<>();
    private Boolean isKioskSeller = false;
    private KioskManager kioskManager;
    private static UIDataContainer instance = new UIDataContainer();

    private UIDataContainer() {
        kioskManager = KioskManager.getInstance();
        kiosksInfos = kioskManager.getKiosksInfos();
    }

    public static UIDataContainer getInstance(){
        return instance;
    }

    /**
     * calls setKioskIsOpen in kioskManager
     *
     * @param kioskName
     * @param isOpen
     */
    public void setKioskIsOpen(String kioskName, Boolean isOpen) {
        kioskManager.setKioskIsOpen(kioskName, isOpen);
    }

    /**
     * calls createInventory in kioskManager
     *
     * @param selectedKiosk
     */
    public void createInventory(String selectedKiosk) {
        kioskManager.createInventory(selectedKiosk);
    }

    /**
     * calls buyArticles in kioskManager
     *
     * @param articlesSelectedToSale
     * @return boolean, if buying Article was successful
     */
    public Boolean buyArticles(HashMap<String, Integer> articlesSelectedToSale) {
        return kioskManager.buyArticles(articlesSelectedToSale, selectedKiosk ,isKioskSeller);
    }

    /**
     * calls createKiosk in kioskManager
     *
     * @param kioskName
     * @param location
     * @param cash
     * @param employeeName
     */
    public void createKiosk(String kioskName, String location, Double cash, String employeeName) {
       kioskManager.createKiosk(kioskName, location, cash, employeeName);
    }

    /**
     * calls createCustomer in kioskManager
     *
     * @param name
     * @param age
     */
    public void createCustomer(String name, Integer age) {
        kioskManager.createCustomer(name, age);
    }


    /**
     * calls automaticTest in kioskManager
     *
     * @param kioskName
     */
    public void automaticTest(String kioskName) {
        kioskManager.automaticTest(kioskName);
    }


    public void setKioskSeller(Boolean kioskSeller) {
        isKioskSeller = kioskSeller;
    }


    /**
     * calls getKiosksInfos in kioskManager
     *
     * @return kioskInfos - List of KioskNames and Status isOpen
     */
    public HashMap<String, Boolean> getKioskInfos() {
        kiosksInfos = kioskManager.getKiosksInfos();
        return kiosksInfos;
    }


    public void setSelectedKiosk(String selectedKiosk) {
        this.selectedKiosk = selectedKiosk;
    }


    public HashMap<Article, Integer> getArticlesForSale() {
        return articlesForSale;
    }


    /**
     * calls setArticlesForSale in kioskManager
     */
    public void setArticlesForSale() {
        articlesForSale = kioskManager.getArticles(selectedKiosk ,isKioskSeller);
    }
}
