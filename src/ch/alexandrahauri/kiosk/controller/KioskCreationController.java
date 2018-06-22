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
import java.util.HashMap;

/**
 * Controller of Kiosk Creation, asks for kioks Informations
 *
 * @author: Alexandra
 * @since: 09.06.2018
 **/
public class KioskCreationController {

    //private static final Logger logger = LoggerFactory.getLogger(KioskCreationController.class);
    private UIDataContainer uiDataContainer;

    @FXML
    private TextField nameField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField cashField;

    @FXML
    private TextField employeeField;

    @FXML
    private Button createKioskButton;

    /**
     * Submit Button Handler, checks the input if its valid
     * Creates the Kiosk if input was valid
     *
     * @param event
     */
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        uiDataContainer = UIDataContainer.getInstance();
        HashMap<String, Boolean> kioskInfos = uiDataContainer.getKioskInfos();
        Window owner = createKioskButton.getScene().getWindow();
        if(nameField.getText().isEmpty() || kioskInfos.containsKey(nameField.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( "Form Error!");
            alert.setHeaderText(null);
            if (nameField.getText().isEmpty()) {
                alert.setContentText( "Please enter a kiosk name");
            } else {
                alert.setContentText( "Kiosk name already exist, name it differently");
            }
            alert.initOwner(owner);
            alert.show();
            return;
        }
        if(locationField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( "Form Error!");
            alert.setHeaderText(null);
            alert.setContentText( "Please enter a location");
            alert.initOwner(owner);
            alert.show();
            return;
        }
        if(cashField.getText().isEmpty() || !cashField.getText().matches("\\d{0,7}([\\.]\\d{0,2})?")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( "Form Error!");
            alert.setHeaderText(null);
            if(cashField.getText().isEmpty()){
                alert.setContentText( "Please enter cash");
            }
            else {
                alert.setHeaderText(null);
            }
            alert.initOwner(owner);
            alert.show();
            return;
        }
        if(employeeField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle( "Form Error!");
            alert.setHeaderText(null);
            alert.setContentText( "Please enter an employee");
            alert.initOwner(owner);
            alert.show();
            return;
        }
        String kioskName = nameField.getText();
        Double cash = Double.valueOf(cashField.getText());
        String location = locationField.getText();
        String employeeName = employeeField.getText();
        uiDataContainer.createKiosk(kioskName, location, cash, employeeName);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle( "Creation Successful!");
        alert.setHeaderText(null);
        alert.setContentText( "Welcome " + nameField.getText());
        alert.initOwner(owner);
        alert.show();
        // back to Übersicht
        try {
            Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/overview.fxml"));
            Parent root = fxmlLoader.load();
            OverviewController ctrl = fxmlLoader.getController();
            ctrl.initialize();
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
}
