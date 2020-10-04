package UI;

import service.TransactionService;
import service.ClientService;
import service.ProjectService;
import service.UndoRedoService;
import service.validators.TransactionServiceValidator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Transaction;
import model.Project;
import model.validators.TransactionValidator;
import repository.IRepository;

import java.security.KeyException;

public class TransactionController {

    public TableView tblTransaction;
    public TableColumn colIdTransaction;

    public TableColumn colIdProject;
    public TableColumn colClientCard;
    public TableColumn colNumberOfProjects;
    public TextField txtIdProject;
    public TextField txtClientCard;
    public TextField txtNumberOfProjects;
    public TableColumn colDateTime;
    public TextField txtInitialDay;
    public TextField txtFinalDay;

    private IRepository<Transaction> bookPlaceIRepository;
    private IRepository<Project> movieIRepository;
    private TransactionValidator transactionValidator;
    private ClientService clientService;
    private ProjectService projectService;
    private TransactionServiceValidator transactionServiceValidator;
    private UndoRedoService undoRedoService;
    private TransactionService transactionService;

    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();
//    private ObservableList<Integer> listForOneClient = FXCollections.observableArrayList();

    public void setServices(IRepository<Transaction> bookPlaceIRepository, IRepository<Project> movieIRepository,
                            TransactionValidator transactionValidator, ClientService clientService, ProjectService projectService,
                            TransactionServiceValidator transactionServiceValidator, UndoRedoService undoRedoService,
                            TransactionService transactionService) {
        this.transactionServiceValidator = transactionServiceValidator;
        this.movieIRepository = movieIRepository;
        this.transactionValidator = transactionValidator;
        this.clientService = clientService;
        this.projectService = projectService;
        this.transactionServiceValidator = transactionServiceValidator;
        this.undoRedoService = undoRedoService;
        this.transactionService = transactionService;
    }

    @FXML
    private void initialize() {

        Platform.runLater(() -> {
            transactions.addAll(transactionService.getAll());
            tblTransaction.setItems(transactions);

//            listClientMovies.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        });
    }

    public void showSelected(MouseEvent mouseEvent) {
    }

    public void btnAddTransactionClick(ActionEvent actionEvent) {

        try {
            int idMovie = Integer.parseInt(txtIdProject.getText());
            int clientCard = Integer.parseInt(txtClientCard.getText());
            int numberOfProjects = Integer.parseInt(txtNumberOfProjects.getText());

            transactionService.add(idMovie, clientCard, numberOfProjects);

            transactions.clear();
            transactions.addAll(transactionService.getAll());
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }

    }

    public void btnDeleteTransactionClick(ActionEvent actionEvent) {

        Transaction selected = (Transaction) tblTransaction.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                transactionService.delete(selected.getId());
                this.transactions.clear();
                this.transactions.addAll(transactionService.getAll());
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            } catch (KeyException kex) {
                Common.showValidationError(kex.getMessage());
            }
        }
        this.tblTransaction.refresh();
    }

    public void btnUpdateClick(ActionEvent actionEvent) {

        Transaction oldVersionTransaction = (Transaction) this.tblTransaction.getSelectionModel().getSelectedItem();
        try {
            int idMovie;
            if (txtIdProject.getText().equals("")) {
                idMovie = oldVersionTransaction.getIdProject();
            } else {
                idMovie = Integer.parseInt(txtIdProject.getText());
            }

            int numberOfTickets;
            if (txtNumberOfProjects.getText().equals("")) {
                numberOfTickets = oldVersionTransaction.getNumberOfProjects();
            } else {
                numberOfTickets = Integer.parseInt(txtNumberOfProjects.getText());
            }

            transactionService.update(oldVersionTransaction.getId(), idMovie, numberOfTickets);
            transactions.clear();
            transactions.addAll(transactionService.getAll());
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        } catch (KeyException kex) {
            Common.showValidationError(kex.getMessage());
        }
    }

    public void btnDeleteBetweenDaysClick(ActionEvent actionEvent) {

        try{
            int initialDay = Integer.parseInt(txtInitialDay.getText());
            int finalDay = Integer.parseInt(txtFinalDay.getText());

            this.transactionService.deleteByDay(initialDay, finalDay);
            this.transactions.clear();
            this.transactions.addAll(transactionService.getAll());
        } catch (KeyException e) {
            Common.showValidationError(e.getMessage());
        } catch (IllegalArgumentException e){
            Common.showValidationError(e.getMessage());
        }
        this.tblTransaction.refresh();
    }
}
