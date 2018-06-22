package ch.alexandrahauri.kiosk.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Thread Class to test Multithreading
 *
 * @author: Alexandra
 * @since: 22.06.2018
 **/
public class ThreadBuy implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ThreadBuy.class);
    private Thread t;
    private String threadName;
    private KioskManager kioskManager;
    private String kioskName;
    public ThreadBuy(String customerName, String kioskName) {
        kioskManager = KioskManager.getInstance();
        this.kioskName = kioskName;
        this.threadName = customerName;
        logger.info("Creating " + threadName);
    }

    public void run() {
        logger.info("Running " + threadName);
        try {
            Thread.sleep(1000);
            kioskManager.createCustomer(threadName, 21);
            logger.info("Created customer  " + threadName);
            HashMap<Article, Integer> articles = kioskManager.getArticles(kioskName, true);
            Thread.sleep(2000);
            HashMap<String, Integer> selectedArticles = kioskManager.automaticSelectOneArticleOfEach(articles);
            logger.info("Customer in Queue" + threadName);
            Thread.sleep(5000);
            kioskManager.buyArticles(selectedArticles, kioskName, true);
            logger.info("Bought Articles " + threadName);
        } catch (InterruptedException e) {
            logger.error("Thread " + threadName + " interrupted.");
        }

        logger.info("Thread " + threadName + " exiting.");
    }

    public void start() {
        logger.info("Starting " + threadName);
        if (t == null) {
            t = new Thread( this, threadName);
            t.start();
        }
    }
}
