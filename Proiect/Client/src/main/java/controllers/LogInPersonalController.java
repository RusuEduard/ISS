package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.IServices;
import services.ServiceException;

import java.io.IOException;

public class LogInPersonalController {

    private PersonalController controller;

    public void setService(PersonalController controller){
        this.controller = controller;
    }

    @FXML
    private TextField login_text;


    @FXML
    void LogInHandle(ActionEvent event) {
        try{
        long id = controller.login_personal(login_text.getText());
        AlertMessage.showPopUp(null, Long.toString(id));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/mainView.fxml"));
        Parent page = loader.load();

        PersMainPageController control = loader.getController();
        control.setController(controller);
        control.setId(id);

        Scene mainPageScene = new Scene(page);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainPageScene);
        window.setTitle("Main");
        window.show();
        } catch (ServiceException | IOException e) {
            AlertMessage.showErrorMessage(null, e.getMessage());
        }
    }

}