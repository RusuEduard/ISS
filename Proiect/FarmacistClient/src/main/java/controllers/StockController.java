package controllers;

import domain.Medicament;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ServiceException;

import java.io.IOException;

public class StockController {

    private FarmacistController ctrl;

    public void setCtrl(FarmacistController ctrl) {
        this.ctrl = ctrl;
        initModel();
    }

    protected class Med_cant{
        Long med_id;
        String med_name;
        Long cant;

        public Med_cant() {
        }

        public Long getMed_id() {
            return med_id;
        }

        public void setMed_id(Long med_id) {
            this.med_id = med_id;
        }

        public String getMed_name() {
            return med_name;
        }

        public void setMed_name(String med_name) {
            this.med_name = med_name;
        }

        public Long getCant() {
            return cant;
        }

        public void setCant(Long cant) {
            this.cant = cant;
        }

        public Medicament getMed(){
            Medicament med = new Medicament();
            med.setId(this.med_id);
            med.setNume(this.getMed_name());
            return med;
        }
    }

    ObservableList<Med_cant> meds = FXCollections.observableArrayList();

    private void initModel() {
        try {
            meds.clear();
            for (var set : ctrl.getMedsCant().entrySet()) {
                Med_cant med = new Med_cant();
                med.setMed_name(set.getKey().getNume());
                med.setMed_id(set.getKey().getId());
                med.setCant(set.getValue());
                meds.add(med);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TableView<Med_cant> medsTableView;

    @FXML
    private TableColumn<Med_cant, String> nameCol;

    @FXML
    private TableColumn<Med_cant, Long> cantCol;

    @FXML
    private void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("med_name"));
        cantCol.setCellValueFactory(new PropertyValueFactory<>("cant"));
        medsTableView.setItems(meds);
    }

    @FXML
    void actualizeazaHandle(ActionEvent event) {
        try {
            ctrl.updateStoc();
            initModel();
        } catch (ServiceException e) {
            AlertMessage.showErrorMessage(null, e.getMessage());
        }
    }

    @FXML
    void backButtonHandle(ActionEvent event) {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}