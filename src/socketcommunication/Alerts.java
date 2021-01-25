package socketcommunication;

import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author d7o_ae
 */
public class Alerts {

    //show Alert window with a message e
    public static void showAlert(String e) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("please see error details below");
        alert.setContentText("Error: " + e);

        alert.showAndWait();
    }

    // show alert 2 (not used , just as a reference
    public static void showSuccessful(String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Done");
        window.setMinWidth(100);
        window.setMaxWidth(250);
        window.setMaxHeight(100);
        Label errorMessage = new Label(message);
        errorMessage.setTextFill(Color.web("#41c124"));
        Button closeBut = new Button("OK");
        closeBut.setOnAction(e -> window.close());
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(15, 15, 15, 15));
        vbox.getChildren().addAll(errorMessage, closeBut);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 250, 100);
        window.setScene(scene);
        window.showAndWait();

    }

    //confirmation Message
    public static boolean confirmAlert(String title, String header, String contnet) {

        // create new confirmation box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contnet);

        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);  // ... user chose OK

    }

    //Warning alert
    public static void warningAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
