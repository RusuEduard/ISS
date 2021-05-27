package controllers;

import domain.Medicament;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ServiceException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class PlaseazaComandaController {

    @FXML
    public TableView<Medicament> medTableVIew;
    private PersonalController controller;
    private long persId;

    public void setController(PersonalController controller)
    {
        this.controller = controller;
        load_medicamente();
    }


    @FXML
    private TableColumn<Medicament, String> medNameCol;


    @FXML
    private TextField cantitateTxt;

    ObservableList<Medicament> medsModel = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        medNameCol.setCellValueFactory(new PropertyValueFactory<>("nume"));
        medTableVIew.setItems(medsModel);
    }

    private void load_medicamente() {
        try{
        List<Medicament> list = StreamSupport.stream(controller.get_meds().spliterator(), false)
                .collect(Collectors.toList());
        medsModel.setAll(list);
        } catch (ServiceException e) {
            AlertMessage.showErrorMessage(null, e.getMessage());
        }
    }

    public void setPersId(long id) {
        this.persId = id;
    }

    public void setCos(Map<Medicament, Long> cos){
        this.cos = cos;
    }


    Map<Medicament, Long> cos = new HashMap<>();

    @FXML
    void handleAddCos(ActionEvent event) {
        try{
            Long cantitate = Long.parseLong(cantitateTxt.getText());
            if(cantitate <= 0){
                throw new NumberFormatException();
            }
            Medicament med = medTableVIew.getSelectionModel().getSelectedItem();

            cos.put(med, cantitate);

            AlertMessage.showPopUp(null, Integer.toString(cos.size()));
        }
        catch (NumberFormatException e){
            AlertMessage.showErrorMessage(null, "Cantitatea trebuie sa fie un numar pozitiv mai mare decat 0!");
        }
    }


    @FXML
    void handleBackBtn(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/mainView.fxml"));
            Parent page = loader.load();

            PersMainPageController control = loader.getController();
            control.setController(controller);
            control.setId(persId);

            Scene mainPageScene = new Scene(page);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainPageScene);
            window.setTitle("Main");
            window.show();
    } catch ( IOException e) {
        AlertMessage.showErrorMessage(null, e.getMessage());
    }
    }

    @FXML
    void ViewCosHandle(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/cosView.fxml"));
            Parent page = loader.load();

            CosController control = loader.getController();
            control.setController(controller);
            control.setId(persId);
            control.setCos(cos);

            Scene mainPageScene = new Scene(page);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainPageScene);
            window.setTitle("Main");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
