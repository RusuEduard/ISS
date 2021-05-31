package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.IServices;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import services.ServiceException;

import java.io.IOException;

public class LogInController {
    private FarmacistController ctrl;

    public void setService(FarmacistController controller) {
        this.ctrl = controller;
    }
    @FXML
    private TextField loginIdTxt;

    @FXML
    void logInHandle(ActionEvent event) {
        String login_id = loginIdTxt.getText();
        try {
            ctrl.login(login_id);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/mianPageView.fxml"));
            Parent page = loader.load();

            MainPageController control = loader.getController();
            control.setController(ctrl);

            Scene mainPageScene = new Scene(page);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainPageScene);
            window.setTitle("Main");
            window.show();
        } catch (ServiceException | IOException e) {
            e.printStackTrace();
        }
    }
}
