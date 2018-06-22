package ch.alexandrahauri.kiosk.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Kiosk Class, contains name, location, cash, employee, articles, supplier and status isOpen
 * @author: Alexandra
 * @since: 03.06.2018
 **/
public class Kiosk {
    private static final Logger logger = LoggerFactory.getLogger(Kiosk.class);
    String name;
    String location;
    double cash;
    Employee employee;
    HashMap<Article, Integer> articles = new HashMap<>();
    Supplier supplier;
    Boolean isOpen;

    public Kiosk(String name, String location, double cash, String employeeName) {
        this.name = name;
        this.location = location;
        this.cash = cash;
        this.employee = new Employee(employeeName);
        this.isOpen = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
        logger.info(name + " setted cash to " + cash);
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public HashMap<Article, Integer> getArticles() {
        return articles;
    }

    public void setArticles(HashMap<Article, Integer> articles) {
        this.articles = articles;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
