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

public class MainPageController implements Iface {
    private FarmacistController ctrl;

    public void setController(FarmacistController ctrl){
        this.ctrl = ctrl;
        ctrl.setIface(this);
        initModel();
    }

    private ObservableList<Comanda> comenzi = FXCollections.observableArrayList();

    @Override
    public void initModel() {
        Platform.runLater(()->{
            try {
                List<Comanda> cmds = StreamSupport.stream(ctrl.getComenziInAsteptare().spliterator(), false)
                        .collect(Collectors.toList());
                comenzi.setAll(cmds);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private TableView<Comanda> comenziTableView;

    @FXML
    private TableColumn<Comanda, Long> idCol;

    @FXML
    private TableColumn<Comanda, String> tipCol;

    @FXML
    private TableColumn<Comanda, LocalDate> dataCol;

    @FXML
    private Button detalisBtn;

    @FXML
    private void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        tipCol.setCellValueFactory(new PropertyValueFactory<>("tip"));
        comenziTableView.setItems(comenzi);
    }


    @FXML
    void handleSelectComanda(MouseEvent event) {
        if(comenziTableView.getSelectionModel().getSelectedItem() != null)
            detalisBtn.setVisible(true);
        else
            detalisBtn.setVisible(false);
    }

    @FXML
    void handleViewDetalii(ActionEvent event) {
        Comanda cmd = comenziTableView.getSelectionModel().getSelectedItem();
        if(cmd != null){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/detailsView.fxml"));
                Parent page = loader.load();

                DetailsView control = loader.getController();
                control.setCtrl(ctrl);
                control.setCmd(cmd);

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

    @FXML
    void handleViewStoc(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/viewStock.fxml"));
            Parent page = loader.load();

            StockController control = loader.getController();
            control.setCtrl(ctrl);

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