//import UI.MainController;
import service.ProjectService;
import service.TransactionService;
import service.validators.TransactionServiceValidator;
import UI.TransactionController;
import UI.ClientController;
import UI.ProjectController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;
import model.Project;
import model.Transaction;
import repository.IRepository;
import service.ClientService;
import service.UndoRedoService;
import service.validators.ClientServiceValidator;
import service.validators.ProjectServiceValidator;
import model.Client;
import model.validators.TransactionValidator;
import model.validators.ClientValidator;
import model.validators.ProjectValidator;
import repository.InMemoryRepository;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UI/projectWindow.fxml"));
        Parent root = fxmlLoader.load();

        IRepository<Project> projectIRepository = new InMemoryRepository<>();
        IRepository<Transaction> transactionIRepository = new InMemoryRepository<>();
        IRepository<Client> clientIRepository = new InMemoryRepository<>();

        UndoRedoService undoRedoService = new UndoRedoService();

        ClientValidator clientValidator = new ClientValidator();
        ClientServiceValidator clientServiceValidator = new ClientServiceValidator();
        ClientService clientService = new ClientService(clientIRepository, clientValidator, clientServiceValidator,
                                                        undoRedoService);

        ProjectValidator projectValidator = new ProjectValidator();
        ProjectServiceValidator projectServiceValidator = new ProjectServiceValidator();
        ProjectService projectService = new ProjectService(projectIRepository, projectValidator, projectServiceValidator, undoRedoService);

        TransactionServiceValidator transactionServiceValidator = new TransactionServiceValidator();
        TransactionValidator transactionValidator = new TransactionValidator();
        TransactionService transactionService = new TransactionService(transactionIRepository, projectIRepository,
                transactionValidator, clientService, projectService, transactionServiceValidator, undoRedoService);

        projectService.add("Project One", 2000, 1550, true);
        projectService.add("Project Two", 2001, 2000, true);
        projectService.add("Project Three", 2003, 1500, true);
        projectService.add("Project Four", 2003, 3050, true);
        projectService.add("Project Five", 2003, 1800, true);

        clientService.add("Ion", "Popescu");
        clientService.add("Ion", "Popescu");
        clientService.add("Maria", "Ionescu");
        clientService.add("Petre", "Amariei");

        transactionService.add(0, 1000, 2);
        transactionService.add(0, 1000, 2);
        transactionService.add(0, 1001, 3);
        transactionService.add(0, 1002, 4);
        transactionService.add(1, 1000, 2);
        transactionService.add(2, 1001, 3);
        transactionService.add(2, 1002, 4);
        transactionService.add(2, 1003, 4);

        ProjectController projectController = fxmlLoader.getController();
        projectController.setServices(projectService, clientService, transactionService, undoRedoService);

        FXMLLoader fxmlLoaderClient = new FXMLLoader(getClass().getResource("UI/clientWindow.fxml"));
        Parent rootClient = fxmlLoaderClient.load();
        ClientController clientController = fxmlLoaderClient.getController();
        clientController.setServices(clientIRepository, clientValidator, clientServiceValidator, undoRedoService, clientService);

        FXMLLoader fxmlLoaderBooking = new FXMLLoader(getClass().getResource("UI/transactionWindow.fxml"));
        Parent rootBooking = fxmlLoaderBooking.load();
        TransactionController transactionController = fxmlLoaderBooking.getController();
        transactionController.setServices(transactionIRepository, projectIRepository,
                transactionValidator, clientService, projectService, transactionServiceValidator,
                undoRedoService, transactionService);

        primaryStage.setTitle("Project manager");
        primaryStage.setScene(new Scene(root, 600, 650));
        primaryStage.show();

        Stage stageClient = new Stage();
        stageClient.setTitle("Client manager");
        stageClient.setScene(new Scene(rootClient, 600, 650));
        stageClient.show();

        Stage stageBooking = new Stage();
        stageBooking.setTitle("Transaction manager");
        stageBooking.setScene(new Scene(rootBooking, 600, 650));
        stageBooking.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
