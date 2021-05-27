package controllers;

import domain.Comanda;
import domain.Medicament;
;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class CosController {

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

    private PersonalController controller;
    private Long persId;
    private Map<Medicament, Long> cos;

    @FXML
    private TableView<Med_cant> cosTableView;

    @FXML
    private TableColumn<Med_cant, String> medNameCol;

    @FXML
    private TableColumn<Med_cant, Long> quantCol;



    @FXML
    void backToShopHandle(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/plaseazaComandaView.fxml"));
            Parent page = loader.load();

            PlaseazaComandaController control = loader.getController();
            control.setController(controller);
            control.setPersId(persId);
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

    @FXML
    void cancelHandle(ActionEvent event) {
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setController(PersonalController controller) {
        this.controller = controller;
    }


    public void setId(long persId) {
        this.persId = persId;
    }

    public void setCos(Map<Medicament, Long> cos){
        this.cos = cos;
        initTable();
    }

    ObservableList<Med_cant> meds = FXCollections.observableArrayList();

    private void initTable() {
        for(var set : cos.entrySet()){
            Med_cant med = new Med_cant();
            med.setMed_name(set.getKey().getNume());
            med.setMed_id(set.getKey().getId());
            med.setCant(set.getValue());
            meds.add(med);
        }
    }

    @FXML
    private void initialize() {
        medNameCol.setCellValueFactory(new PropertyValueFactory<>("med_name"));
        quantCol.setCellValueFactory(new PropertyValueFactory<>("cant"));
        cosTableView.setItems(meds);
    }

    @FXML
    private CheckBox urgentCheckBox;

    @FXML
    void placeOrderHandle(ActionEvent event) {
        String tip = "Comun";
        if(urgentCheckBox.isSelected())
            tip = "URGENT";

        Comanda comanda = new Comanda();
        comanda.setStatus("In asteptare");
        comanda.setTerminal(Integer.toUnsignedLong(1));
        comanda.setData(LocalDate.now());
        comanda.setTip(tip);
        comanda.setPersonal_id(persId);
        comanda.setMedicamente(cos);

        controller.saveComanda(comanda);
        AlertMessage.showPopUp(null, "Comanda a fost trimisa!");
    }
}