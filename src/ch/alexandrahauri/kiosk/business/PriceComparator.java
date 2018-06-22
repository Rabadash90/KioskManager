package ch.alexandrahauri.kiosk.business;

import java.util.Comparator;

/**
 * Own Comparator, to compare the price between two articles
 *
 * @author: Alexandra
 * @since: 22.06.2018
 **/
public class PriceComparator implements Comparator<Article> {

    @Override
    public int compare(Article o1, Article o2) {
        return ((Double) o2.getPrice()).compareTo(o1.getPrice());
    }
}
