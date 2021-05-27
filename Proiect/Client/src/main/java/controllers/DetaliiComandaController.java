package controllers;

import domain.Comanda;
import domain.Medicament;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class DetaliiComandaController {
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

    private Long persId;
    private PersonalController controller;
    private Comanda comanda;

    public void setPersId(Long persId) {
        this.persId = persId;
    }

    public void setController(PersonalController controller) {
        this.controller = controller;
    }

    public void setComanda(Comanda comanda){
        this.comanda = comanda;
        initData();
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
    private Label stafIdLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label stareLabel;

    @FXML
    private Label statusLabel;

    private void initData() {
        stafIdLabel.setText(Long.toString(comanda.getPersonal_id()));
        dateLabel.setText(comanda.getData().toString());
        stareLabel.setText(comanda.getTip());
        statusLabel.setText(comanda.getStatus());
        initTableModel();
    }

    ObservableList<Med_cant> meds = FXCollections.observableArrayList();

    private void initTableModel() {
        for(var set : comanda.getMedicamente().entrySet()){
            Med_cant med = new Med_cant();
            med.setMed_name(set.getKey().getNume());
            med.setMed_id(set.getKey().getId());
            med.setCant(set.getValue());
            meds.add(med);
        }
    }

    @FXML
    void backButtonHandle(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/viewComenziView.fxml"));
            Parent page = loader.load();

            ViewComenziController control = loader.getController();
            control.setController(controller);
            control.setPersId(persId);

            Scene mainPageScene = new Scene(page);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainPageScene);
            window.setTitle("Main");
            window.show();

        } catch (
        IOException e) {
            e.printStackTrace();
        }
    }

}
