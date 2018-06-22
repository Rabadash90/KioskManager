package ch.alexandrahauri.kiosk.controller;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

/**
 * @author: Alexandra
 * @since: 04.06.2018
 **/

public abstract class Overview {
    String name;
    CheckBox isOpen;
    Button sellArticle;
    Button orderArticle;
    Button inventory;
    Button automaticTest;

    public Overview(String name, boolean open) {
        this.name = name;
        this.isOpen = new CheckBox();
        this.isOpen.setSelected(open);
        this.sellArticle = new Button("Einkauf tätigen");
        this.orderArticle = new Button("Artikel bestellen");
        this.inventory = new Button("Inventar");
        this.automaticTest = new Button("Automatischer Durchlauf");

        disableButtons(!isOpen.isSelected());

        handleOpenKioskAction(isOpen);
        handleSellArticleButtonAction(sellArticle);
        handleOrderArticleButtonAction(orderArticle);
        handleInventoryButtonAction(inventory);
        handleAutomaticButtonAction(automaticTest);
    }

    public void disableButtons(Boolean isDisabled) {
        sellArticle.setDisable(isDisabled);
        orderArticle.setDisable(isDisabled);
        inventory.setDisable(isDisabled);
        automaticTest.setDisable(isDisabled);
    }

    public abstract void handleOpenKioskAction(CheckBox isOpen);
    public abstract void handleSellArticleButtonAction(Button sellArticle);
    public abstract void handleOrderArticleButtonAction(Button orderArticle);
    public abstract void handleInventoryButtonAction(Button inventory);
    public abstract void handleAutomaticButtonAction(Button automaticTest);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CheckBox getOpen() {
        return isOpen;
    }

    public void setOpen(CheckBox open) {
        isOpen = open;
    }

    public Button getSellArticle() {
        return sellArticle;
    }

    public void setSellArticle(Button sellArticle) {
        this.sellArticle = sellArticle;
    }

    public Button getOrderArticle() {
        return orderArticle;
    }

    public void setOrderArticle(Button orderArticle) {
        this.orderArticle = orderArticle;
    }

    public Button getInventory() {
        return inventory;
    }

    public void setInventory(Button inventory) {
        this.inventory = inventory;
    }

    public Button getAutomaticTest() {
        return automaticTest;
    }

    public void setAutomaticTest(Button automaticTest) {
        this.automaticTest = automaticTest;
    }
}

