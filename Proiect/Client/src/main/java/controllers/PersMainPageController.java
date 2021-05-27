package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PersMainPageController {

    private PersonalController controller;
    private long id;

    @FXML
    private Label id_field;

    @FXML
    void placeOrderHandle(ActionEvent event) {
        try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/plaseazaComandaView.fxml"));
        Parent page = loader.load();

        PlaseazaComandaController control = loader.getController();
        control.setController(controller);
        control.setPersId(id);

        Scene mainPageScene = new Scene(page);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainPageScene);
        window.setTitle("Main");
        window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void viewOrdersHandle(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/viewComenziView.fxml"));
            Parent page = loader.load();

            ViewComenziController control = loader.getController();
            control.setController(controller);
            control.setPersId(id);

            Scene mainPageScene = new Scene(page);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainPageScene);
            window.setTitle("Main");
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setController(PersonalController controller) {
        this.controller = controller;
    }

    public void setId(long id) {
        this.id = id;
        id_field.setText(Long.toString(id));
    }
}
