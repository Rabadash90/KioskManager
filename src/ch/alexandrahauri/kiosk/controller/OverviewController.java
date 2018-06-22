package ch.alexandrahauri.kiosk.controller;
import java.io.IOException;
import java.util.*;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for Overview (Kiosk Manager), shows all the Kiosks.
 *
 * @author: Alexandra
 * @since: 04.06.2018
 **/
public class OverviewController {
    private static final Logger logger = LoggerFactory.getLogger(OverviewController.class);

    @FXML
    private Label label;

    @FXML
    TableView tableview;

    ObservableList<Overview> data;

    @FXML
    private Button createKioskButton;

    /**
     * Submit Button Handler, goes to Kiosk Creation
     *
     * @param event
     */
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        try {
            Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/kioskCreation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Initialized all the Kiosks
     */
    @FXML
    public void initialize() {
        UIDataContainer uiDataContainer = UIDataContainer.getInstance();
        HashMap<String, Boolean> kiosksInfos = uiDataContainer.getKioskInfos();
        data = FXCollections.observableArrayList();

        Iterator<Map.Entry<String, Boolean>> iteratorHashMaps = kiosksInfos.entrySet().iterator();
        while (iteratorHashMaps.hasNext()) {
            Map.Entry test =  (Map.Entry)iteratorHashMaps.next();
            String kioskName = (String) test.getKey();
            Boolean kioskStatus = (Boolean) test.getValue();
            data.add(new Overview(kioskName, kioskStatus) {
                @Override
                public void handleOpenKioskAction(CheckBox isOpen) {
                    isOpen.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            disableButtons(!isOpen.isSelected());
                            uiDataContainer.setKioskIsOpen(kioskName,isOpen.isSelected());
                        }
                    });
                }

                @Override
                public void handleSellArticleButtonAction(Button sellArticle) {
                    sellArticle.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/customerInformation.fxml"));
                                Parent root = fxmlLoader.load();
                                //CustomerInformationController ctrl = fxmlLoader.getController();
                                //ctrl.setKiosk("Haselgasse", "Wald");
                                uiDataContainer.setSelectedKiosk(kioskName);
                                Scene scene = new Scene(root);
                                stage.setTitle("Customer Information");
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                logger.error(e.getMessage());
                            }
                        }
                    });
                }

                @Override
                public void handleOrderArticleButtonAction(Button orderArticle) {
                    orderArticle.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                uiDataContainer.setSelectedKiosk(kioskName);
                                uiDataContainer.setKioskSeller(false);
                                uiDataContainer.setArticlesForSale();
                                Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/articlesSelection.fxml"));
                                Parent root = fxmlLoader.load();
                                Scene scene = new Scene(root);
                                stage.setTitle("Choose Article");
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                logger.error(e.getMessage());
                            }
                        }
                    });
                }

                @Override
                public void handleInventoryButtonAction(Button inventory) {
                    inventory.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            uiDataContainer.createInventory(kioskName);
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Form Error!");
                            alert.setHeaderText(null);
                            alert.setContentText(name + " You clicked on Inventory");
                            alert.initOwner(inventory.getScene().getWindow());
                            alert.show();
                        }
                    });
                }

                @Override
                public void handleAutomaticButtonAction(Button automaticTest) {
                    automaticTest.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            uiDataContainer.automaticTest(kioskName);
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle( "Form Error!");
                            alert.setHeaderText(null);
                            alert.setContentText( name + " You clicked on automatic Test");
                            alert.initOwner(automaticTest.getScene().getWindow());
                            alert.show();
                        }
                    });
                }
            });
        }

        tableview.setItems(data);
    }
}