package ch.alexandrahauri.kiosk.controller;

import ch.alexandrahauri.kiosk.business.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controller for Article Selection, shows all the Articles which the kiosk has.
 *
 * @author: Alexandra
 * @since: 09.06.2018
 **/
public class ArticleSelectionController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(ArticleSelectionController.class);

    private UIDataContainer uiDataContainer;

    ObservableList<ArticleSelection> data;

    @FXML
    TableView tableview;

    @FXML
    private Button buyArticlesButton;

    /**
     * Submit Button Handler, checks the input if its valid
     * Buys Articles if input was valid
     *
     * @param event
     */
    @FXML
    protected void handleSubmitBuyArticlesButtonAction(ActionEvent event) {
        logger.info("clicked on buyArticle button");
        Window owner = buyArticlesButton.getScene().getWindow();
        Iterator<ArticleSelection> dataIterator = data.iterator();
        HashMap<String, Integer> selectedArticles = new HashMap<String, Integer>();
        while (dataIterator.hasNext()) {
            ArticleSelection article = dataIterator.next();
            if(article.getIsSelected().isSelected()) {
                // Check the quantity input of the selected element
                if(article.getQuantity().getText().isEmpty() || !article.getQuantity().getText().matches("\\d{1,3}([\\.]\\d{0})?")  ||  Integer.valueOf(article.getQuantity().getText()) <= 0 || Integer.valueOf(article.getQuantity().getText()) > article.getArticleInStock()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle( "Form Error!");
                    alert.setHeaderText(null);
                    alert.setContentText( "Please enter a valid number of articles you want to buy");
                    alert.setHeaderText(null);
                    alert.initOwner(owner);
                    alert.show();
                    return;
                }
                selectedArticles.put(article.articleName, Integer.valueOf(article.getQuantity().getText()));
            }
        }
        // buy Articles
        if (selectedArticles.size() > 0) {
            Boolean isOk = uiDataContainer.buyArticles(selectedArticles);
            if (!isOk) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle( "Form Error!");
                alert.setHeaderText(null);
                alert.setContentText( "Youre not old enough to buy alcohol or tobacco");
                alert.setHeaderText(null);
                alert.initOwner(owner);
                alert.show();
                return;
            }
        }

        try {

            Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/overview.fxml"));
            Parent root = fxmlLoader.load();;
            Scene scene = new Scene(root);
            stage.setTitle("Kiosk Manager");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * If its cancelled the View will change back to the Kiosk Manager Overview
     *
     * @param event
     */
    @FXML
    protected void handleCancelButtonAction(ActionEvent event) {
        try {
            Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/overview.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Kiosk Manager");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the Articles
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.info("initialize Articles");
        uiDataContainer = UIDataContainer.getInstance();
        HashMap<Article, Integer> articles = uiDataContainer.getArticlesForSale();
        Iterator<Map.Entry<Article, Integer>> iterator = articles.entrySet().iterator();
        data = FXCollections.observableArrayList();
        while (iterator.hasNext()) {
            Map.Entry articleEntry = (Map.Entry)iterator.next();
            Article article = (Article) articleEntry.getKey();
            Integer articleInStock = (Integer) articleEntry.getValue();
            data.add(new ArticleSelection(article.getName(), article.getPrice(), articleInStock));
        }
       tableview.setItems(data);
    }
}