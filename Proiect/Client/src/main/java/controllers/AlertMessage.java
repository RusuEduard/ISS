package controllers;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertMessage {
    static void showMessage(Stage owner, Alert.AlertType type, String header, String text) {
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }

    static void showErrorMessage(Stage owner, String text) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.initOwner(owner);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }

    static void showPopUp(Stage owner, String text){
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.initOwner(owner);
        message.setTitle("REMEMBER");
        message.setContentText(text);
        message.initModality(Modality.NONE);
        message.show();
    }
}
