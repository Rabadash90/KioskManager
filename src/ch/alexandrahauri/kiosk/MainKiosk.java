package ch.alexandrahauri.kiosk;

import ch.alexandrahauri.kiosk.business.Initializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Main of the Kiosk, Starts the KioskManager in the overview.
 *
 * @author: Alexandra
 * @since: 04.06.2018
 **/
public class MainKiosk extends Application {
    //private static final Logger logger = LoggerFactory.getLogger(MainKiosk.class);
    @Override
    public void start(Stage stage) throws Exception {
        //logger.info("start stage");
        Initializer initializer = new Initializer();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/overview.fxml"));
        Scene scene = new Scene(root);

        scene.getStylesheets().add("/CSS/mycss.css");

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
