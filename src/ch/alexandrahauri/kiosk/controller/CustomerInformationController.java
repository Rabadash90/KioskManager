package ch.alexandrahauri.kiosk.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Controller of Customer Information, asks for name and age input of the user
 *
 * @author: Alexandra
 * @since: 09.06.2018
 **/
public class CustomerInformationController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerInformationController.class);

    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private Button submitButton;

    /**
     * Submit Button Handler, checks the input if its valid
     * Goes to Article Selection if input was valid
     *
     * @param event
     */
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        logger.info("clicked submit client button");
        Window owner = submitButton.getScene().getWindow();
        if(nameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( "Form Error!");
            alert.setHeaderText(null);
            alert.setContentText( "Please enter your name");
            alert.initOwner(owner);
            alert.show();
            return;
        }
        if(ageField.getText().isEmpty() || !ageField.getText().matches("\\d{1,3}([\\.]\\d{0})?")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( "Form Error!");
            alert.setHeaderText(null);
            alert.setContentText( "Please enter your age");
            alert.setHeaderText(null);
            alert.initOwner(owner);
            alert.show();
            return;
        }
        try {
            UIDataContainer uiDataContainer = UIDataContainer.getInstance();
            // set Kiosk as Seller
            uiDataContainer.createCustomer(nameField.getText(), Integer.valueOf(ageField.getText()));
            uiDataContainer.setKioskSeller(true);
            uiDataContainer.setArticlesForSale();

            Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/articlesSelection.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Choose Articles");
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
}
