package service;

import service.validators.TransactionServiceValidator;
import model.Project;
import model.Transaction;
import model.validators.TransactionValidator;
import repository.IRepository;
import model.undoredo.AddOperation;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private IRepository<Transaction> transactionIRepository;
    private IRepository<Project> projectIRepository;
    private TransactionValidator transactionValidator;
    private TransactionServiceValidator transactionServiceValidator;

    private ClientService clientService;
    private ProjectService projectService;
    private UndoRedoService undoRedoService;

    private static int id = -1;

    public TransactionService(IRepository<Transaction> transactionIRepository, IRepository<Project> projectIRepository,
                              TransactionValidator transactionValidator, ClientService clientService,
                              ProjectService projectService, TransactionServiceValidator transactionServiceValidator,
                              UndoRedoService undoRedoService) {
        this.projectIRepository = projectIRepository;
        this.transactionIRepository = transactionIRepository;
        this.transactionValidator = transactionValidator;
        this.clientService = clientService;
        this.projectService = projectService;
        this.transactionServiceValidator = transactionServiceValidator;
        this.undoRedoService = undoRedoService;
    }

    public void add(int idProject, int clientCard, int numberOfProjects) throws IllegalArgumentException{

        StringBuilder messages = new StringBuilder();

        messages.append(this.transactionValidator.validate(clientCard, numberOfProjects));
        Project projectToBuy = this.projectIRepository.read(idProject);
        messages.append(this.transactionServiceValidator.validateMovie(projectToBuy));
        if (!messages.toString().equals("")){
            throw new IllegalArgumentException(String.valueOf(messages));
        }

        projectToBuy.setTicketsSold(projectToBuy.getTicketsSold() + numberOfProjects);
        TransactionService.id++;
        Transaction transaction = new Transaction(id, idProject, clientCard, numberOfProjects);
        this.transactionIRepository.create(transaction);
        clientService.findById(clientCard).setMovieSeen(idProject);
        this.undoRedoService.addToUndo(new AddOperation(transactionIRepository, transaction));
    }

    public void printListOfTransactions(){
        System.out.println("\nThe list of transactions: ");
        for(Transaction transaction : this.transactionIRepository.read()){
            System.out.println(transaction);
        }
    }

    public void update(int idBooking, int idMovie, int numberOfProjects) throws KeyException, IllegalArgumentException {

        StringBuilder messages = new StringBuilder();

        Transaction transaction = this.transactionIRepository.read(idBooking);

        messages.append(transactionServiceValidator.validate(projectService.findById(idMovie), transaction));

        int clientCard = 0;
        if(transaction != null) {
            clientCard = transaction.getClientCard();
        } else {
            messages.append(transactionValidator.validate(clientCard, numberOfProjects));
        }
        if (!messages.toString().equals("")){
            throw new IllegalArgumentException(String.valueOf(messages));
        }

        Transaction bookPlace = new Transaction(idBooking, idMovie, clientCard, numberOfProjects);
        this.transactionIRepository.update(bookPlace);
    }

    public void delete(int id) throws KeyException {
        this.transactionIRepository.delete(id);
        this.undoRedoService.addToUndo(new AddOperation(transactionIRepository, findById(id)));

    }

    public void stopProjectFromWorkingOn(Project project){
        project.setInProgress(false);
    }

    public List<Transaction> findByHour(int initialHour, int finalHour){

        String message = this.transactionServiceValidator.validateHour(initialHour, finalHour);
        if (!message.equals("")){
            throw new IllegalArgumentException(message);
        }

        List<Transaction> bookingsList = new ArrayList<>();
        for(Transaction bookedPlace : transactionIRepository.read()){
            int bookingHour = bookedPlace.getDateTime().getHour();
            if (bookingHour >= initialHour && bookingHour <= finalHour){
                bookingsList.add(bookedPlace);
            }
        }
        return bookingsList;
    }

    private Transaction findById(int id){
        return transactionIRepository.read(id);
    }

    public List<Transaction> getAll(){
        return this.transactionIRepository.read();
    }

    public void deleteByDay(int initialDay, int finalDay) throws KeyException,IllegalArgumentException {

        String message = this.transactionServiceValidator.validateDay(initialDay, finalDay);
        if (!message.equals("")){
            throw new IllegalArgumentException(message);
        }
        for(Transaction transaction : this.transactionIRepository.read()){
            int transactionDay = transaction.getDateTime().getDayOfMonth();
            if (transactionDay >= initialDay && transactionDay <= finalDay){
                this.transactionIRepository.delete(transaction.getId());
            }
        }
    }
}
