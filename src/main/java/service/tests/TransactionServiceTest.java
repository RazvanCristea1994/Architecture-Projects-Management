package service.tests;

import service.ClientService;
import service.ProjectService;
import service.TransactionService;
import service.UndoRedoService;
import service.validators.ClientServiceValidator;
import service.validators.ProjectServiceValidator;
import service.validators.TransactionServiceValidator;
import model.Client;
import model.Project;
import model.Transaction;
import model.validators.ClientValidator;
import model.validators.ProjectValidator;
import model.validators.TransactionValidator;
import org.junit.jupiter.api.BeforeEach;
import repository.IRepository;
import repository.InMemoryRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionServiceTest {

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
    Project project = new Project( 0, "Casa M", 2000, 1500, false);
    Project project1 = new Project( 1, "Casa T", 2010, 3000, false);

    LocalDateTime dateTime = LocalDateTime.now();
    DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    String formattedDate = dateTime.format(myFormat);
    LocalDateTime formattedDateTime = LocalDateTime.parse(formattedDate);

    TransactionService transactionService = new TransactionService(transactionIRepository, projectIRepository,
            transactionValidator, clientService, projectService, transactionServiceValidator, undoRedoService);

    Client client = new Client(1000, "Ion", "Popescu");
    Client client1 = new Client(1001, "Maria", "Popescu");
    Client client2 = new Client(1002, "Ioana", "Popescu");
    Transaction transaction = new Transaction(0,0, 1000, 1);
    Transaction transaction1 = new Transaction(1,1, 1001, 1);
    Transaction transaction2 = new Transaction(2,0, 1002, 1);
    Transaction transaction3 = new Transaction(3,1, 1003, 1);

    @BeforeEach
    void populate() {
        projectService.add(project.getTitle(),
                project.getYear(),
                project.getPrice(), project.getIsInProgress());
        projectService.add(project1.getTitle(),
                project1.getYear(),
                project1.getPrice(), project.getIsInProgress());

        transactionService.add(
                1,
                transaction.getClientCard(),
                transaction.getNumberOfProjects());
        transactionService.add(
                1,
                transaction1.getClientCard(),
                transaction1.getNumberOfProjects());
        transactionService.add(
                1,
                transaction2.getClientCard(),
                transaction2.getNumberOfProjects());
        transactionService.add(
                0,
                transaction3.getClientCard(),
                transaction3.getNumberOfProjects());
    }


}