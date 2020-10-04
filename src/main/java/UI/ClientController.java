package UI;

import service.ClientService;
import service.UndoRedoService;
import service.validators.ClientServiceValidator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import model.Client;
import model.validators.ClientValidator;
import repository.IRepository;

import java.security.KeyException;

public class ClientController {

    public TableView tblClients;
    public ListView listClientProjects;
    public TableColumn colIdClient;
    public TableColumn colFirstName;
    public TextField txtClientFirstName;
    public TextField txtClientLastName;

    public Button btnAddClient;
    public Button btnDeleteClient;
    public Button btnUpdate;

    private IRepository<Client> clientIRepository;
    private ClientValidator clientValidator;
    private ClientServiceValidator clientServiceValidator;
    private ClientService clientService;
    private UndoRedoService undoRedoService;

    private ObservableList<Client> clients = FXCollections.observableArrayList();
    private ObservableList<Integer> listForOneClient = FXCollections.observableArrayList();

    public void setServices(IRepository<Client> clientIRepository, ClientValidator clientValidator,
                            ClientServiceValidator clientServiceValidator, UndoRedoService undoRedoService,
                            ClientService clientService) {
        this.clientIRepository = clientIRepository;
        this.clientValidator = clientValidator;
        this.clientServiceValidator = clientServiceValidator;
        this.undoRedoService = undoRedoService;
        this.clientService = clientService;
    }

    @FXML
    private void initialize() {

        Platform.runLater(() -> {
            clients.addAll(clientService.getAll());
            tblClients.setItems(clients);

            listClientProjects.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        });
    }

    public void btnAddClientClick(ActionEvent actionEvent) {
        try {
            String firstName = txtClientFirstName.getText();
            String lastName = txtClientLastName.getText();

            clientService.add(firstName, lastName);

            clients.clear();
            clients.addAll(clientService.getAll());
        } catch ( RuntimeException rex ){
            Common.showValidationError(rex.getMessage());
        }
    }

    public void btnUpdateClick(ActionEvent actionEvent) {

        Client oldVersionClient = (Client) this.tblClients.getSelectionModel().getSelectedItem();
        try{
            String firstName = txtClientFirstName.getText();
            if (firstName.equals("")){
                firstName = oldVersionClient.getFirstName();
            }

            String lastName = txtClientLastName.getText();
            if (lastName.equals("")){
                lastName = oldVersionClient.getLastName();
            }

            clientService.update(oldVersionClient.getId(), firstName, lastName);
            clients.clear();
            clients.addAll(clientService.getAll());
        } catch ( RuntimeException rex ){
            Common.showValidationError(rex.getMessage());
        } catch ( KeyException kex ){
            Common.showValidationError(kex.getMessage());
        }
    }

    public void btnDeleteClientClick(ActionEvent actionEvent){

        Client selected = (Client) tblClients.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                clientService.delete(selected.getId());
                this.clients.clear();
                this.clients.addAll(clientService.getAll());
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            } catch (KeyException kex) {
                Common.showValidationError(kex.getMessage());
            }
        }
        this.tblClients.refresh();
    }

    public void showSelected(MouseEvent mouseEvent) {
        listForOneClient.clear();
        tblClients.refresh();

        listClientProjects.getSelectionModel().clearSelection();
        TableSelectionModel<Client> tableSelectionModel = tblClients.getSelectionModel();
        Client client = tableSelectionModel.getSelectedItem();

        listForOneClient.addAll(clientService.getAllProjects(client));
        listClientProjects.setItems(listForOneClient);
    }

    public void btnUndoClick(ActionEvent actionEvent) {

        if(this.undoRedoService.undo()){
            System.out.println("Undo done.");
        }else{
            System.out.println("No undo left.");
        }
        clients.clear();
        clients.addAll(clientService.getAll());
    }
}
