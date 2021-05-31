package controllers;

import domain.Comanda;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ViewComenziController implements IFace{
    private PersonalController controller;
    private Long persId;

    public void setController(PersonalController controller){
        this.controller = controller;
        controller.setController(this);
        initModel();
    }

    public void setPersId(Long id){
        this.persId = id;
    }

    ObservableList<Comanda> comenzi = FXCollections.observableArrayList();

    @Override
    public void initModel() {
        Platform.runLater(()->{
            try {
                List<Comanda> list = StreamSupport.stream(controller.getComenzi().spliterator(), false).collect(Collectors.toList());
                comenzi.setAll(list);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private TableView<Comanda> comenziTableView;

    @FXML
    private TableColumn<Comanda, Long> staffIdCol;

    @FXML
    private TableColumn<Comanda, LocalDate> dataCol;

    @FXML
    private TableColumn<Comanda, String> statusCol;

    @FXML
    private void initialize() {
        staffIdCol.setCellValueFactory(new PropertyValueFactory<>("personal_id"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        comenziTableView.setItems(comenzi);
    }

    @FXML
    void backButtonHandle(ActionEvent event) {
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
    void detailsButtonHandle(ActionEvent event) {
        Comanda comanda = comenziTableView.getSelectionModel().getSelectedItem();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/DetaliiComandaView.fxml"));
            Parent page = loader.load();

            DetaliiComandaController control = loader.getController();
            control.setController(controller);
            control.setPersId(persId);
            control.setComanda(comanda);

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
    private Button cancelOrderButton;

    @FXML
    void handleOrderSelection(MouseEvent event) {
        Comanda comanda = comenziTableView.getSelectionModel().getSelectedItem();
        if(comanda != null && comanda.getStatus().equals("In asteptare")){
            cancelOrderButton.setVisible(true);
        }
        else{
            cancelOrderButton.setVisible(false);
        }
    }

    @FXML
    void handleCancelOrder(ActionEvent event) {
        Comanda comanda = comenziTableView.getSelectionModel().getSelectedItem();
        try {
            controller.cancelComanda(comanda.getId());
            initModel();
            cancelOrderButton.setVisible(false);
        } catch (ServiceException e) {
            AlertMessage.showErrorMessage(null, e.getMessage());
        }
    }
}