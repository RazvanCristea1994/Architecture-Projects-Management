package UI;

import service.TransactionService;
import service.ClientService;
import service.ProjectService;
import service.UndoRedoService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Project;

import java.security.KeyException;

public class ProjectController {
    public TableView tblProject;
    public ListView listMovies;
    public TableColumn colIdProject;
    public TableColumn colNameProject;
    public TableColumn colYear;
    public TableColumn colPrice;
    public TableColumn colIsInProgress;
    public CheckBox chkIsInProgress;
    public TextField txtProjectTitle;
    public TextField txtYear;
    public TextField txtPrice;

    public Button btnAddProject;
    public Button btnDeleteProject;
    public Button btnUpdate;

    public ProjectService projectService;
    public ClientService clientService;
    public TransactionService transactionService;
    public UndoRedoService undoRedoService;
    public Button btnIncreasePrice;
    public TextField txtValueToIncrease;
    public TextField txtValueLimit;
    public Button btnSortByPrice;

    private ObservableList<Project> projects = FXCollections.observableArrayList();
    private ObservableList<Integer> listForOneClient = FXCollections.observableArrayList();

    public void setServices(ProjectService projectService, ClientService clientService, TransactionService transactionService,
                            UndoRedoService undoRedoService) {
        this.projectService = projectService;
        this.clientService = clientService;
        this.transactionService = transactionService;
    }

    @FXML
    private void initialize() throws Exception{

        Platform.runLater(() -> {
            projects.addAll(this.projectService.getAll());
            tblProject.setItems(projects);
        });
    }

    public void btnAddProjectClick(ActionEvent actionEvent) {
        try {
            String title = txtProjectTitle.getText();
            int year = Integer.parseInt(txtYear.getText());
            int price = Integer.parseInt(txtPrice.getText());
            boolean isInProgress = chkIsInProgress.isSelected();

            projectService.add(title, year, price, isInProgress);

            projects.clear();
            projects.addAll(projectService.getAll());
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }

    public void btnUpdateClick(ActionEvent actionEvent) {

        Project oldVersionProject = (Project) this.tblProject.getSelectionModel().getSelectedItem();
        try {
            String title = txtProjectTitle.getText();
            if (title.equals("")) {
                title = oldVersionProject.getTitle();
            }

            int year;
            if (txtYear.getText().equals("")) {
                year = oldVersionProject.getYear();
            } else {
                year = Integer.parseInt(txtYear.getText());
            }

            int price;
            if (txtPrice.getText().equals("")) {
                price = oldVersionProject.getPrice();
            } else {
                price = Integer.parseInt(txtPrice.getText());
            }

            boolean isInProgress = chkIsInProgress.isSelected();

            projectService.update(oldVersionProject.getId(), title, year, price, isInProgress);
            projects.clear();
            projects.addAll(projectService.getAll());
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        } catch (KeyException kex) {
            Common.showValidationError(kex.getMessage());
        }
    }

    public void btnDeleteProjectClick(ActionEvent actionEvent) {

        Project selected = (Project) tblProject.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                projectService.delete(selected.getId());
                this.projects.clear();
                this.projects.addAll(projectService.getAll());
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            } catch (KeyException kex) {
                Common.showValidationError(kex.getMessage());
            }
        }
        this.tblProject.refresh();
    }

    public void btnIncreasePriceClick(ActionEvent actionEvent) {

        try {
            int valueToIncrease = Integer.parseInt(txtValueToIncrease.getText());
            int valueLimit = Integer.parseInt(txtValueLimit.getText());
            projectService.increasePrice(valueLimit, valueToIncrease);
            this.tblProject.refresh();
        } catch (IllegalArgumentException e){
            Common.showValidationError(e.getMessage());
        }
    }

    public void btnSortByPriceClick(ActionEvent actionEvent) {

        tblProject.getItems().clear();
        Platform.runLater(() -> {
            projects.addAll(this.projectService.sortProjects());
            tblProject.setItems(projects);
        });
    }
}
