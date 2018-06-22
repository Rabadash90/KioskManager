package ch.alexandrahauri.kiosk.controller;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * Data Container for Article Selection, this just contains the Informations which are shown on the UI.
 *
 * @author: Alexandra
 * @since: 09.06.2018
 **/
public class ArticleSelection {

    CheckBox isSelected;
    String articleName;
    Double articleCost;
    Integer articleInStock;
    TextField quantity;

    public ArticleSelection(String articleName, Double articleCost, Integer articleInStock) {
        this.isSelected = new CheckBox();
        this.isSelected.setSelected(false);
        this.articleName = articleName;
        this.articleCost = articleCost;
        this.articleInStock = articleInStock;
        this.quantity = new TextField();
        this.quantity.setText("0");
    }

    public Double getArticleCost() {
        return articleCost;
    }

    public void setArticleCost(Double articleCost) {
        this.articleCost = articleCost;
    }

    public Integer getArticleInStock() {
        return articleInStock;
    }

    public void setArticleInStock(Integer articleInStock) {
        this.articleInStock = articleInStock;
    }

    public CheckBox getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(CheckBox isSelected) {
        this.isSelected = isSelected;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public TextField getQuantity() {
        return quantity;
    }

    public void setQuantity(TextField quantity) {
        this.quantity = quantity;
    }
}
