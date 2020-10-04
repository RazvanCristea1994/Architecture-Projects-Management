package service;

import service.validators.ClientServiceValidator;
import model.Client;
import model.undoredo.AddOperation;
import model.validators.ClientValidator;
import repository.IRepository;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private IRepository<Client> clientIRepository;
    private ClientValidator clientValidator;
    private ClientServiceValidator clientServiceValidator;

    private UndoRedoService undoRedoService;

    private static int id = 999;

    public ClientService(IRepository<Client> clientIRepository, ClientValidator clientValidator,
                         ClientServiceValidator clientServiceValidator, UndoRedoService undoRedoService) {
        this.clientIRepository = clientIRepository;
        this.clientValidator = clientValidator;
        this.clientServiceValidator = clientServiceValidator;
        this.undoRedoService = undoRedoService;
    }

    public Client findById(int id){
        return clientIRepository.read(id);
    }

    public void add(String firstName, String lastName) {

        String message = this.clientValidator.validate(firstName, lastName);
        if (!message.equals("")){
            throw new IllegalArgumentException(message);
        }
        ClientService.id++;
        Client client = new Client(ClientService.id, firstName, lastName);
        this.clientIRepository.create(client);
        this.undoRedoService.addToUndo(new AddOperation(clientIRepository, client));
    }

    public void printListOfClients(){
        System.out.println("\nThe list of clients: ");
        for(Client client : this.clientIRepository.read()){
            System.out.println(client);
        }
    }

    public void update(int id, String firstName, String lastName) throws KeyException, IllegalArgumentException {

        StringBuilder messages = new StringBuilder();
        messages.append(clientValidator.validate(firstName, lastName));
        messages.append(clientServiceValidator.validate(findById(id)));
        if (!messages.toString().equals("")){
            throw new IllegalArgumentException(String.valueOf(messages));
        }
        Client clientToUpdate = this.clientIRepository.read(id);
        List<Integer> listOfProjects = clientToUpdate.getProjectsBought();

        Client client = new Client(id, firstName, lastName);
        this.clientIRepository.update(client);

        client.setMovieSeen(listOfProjects);
    }

    public void delete(int id) throws KeyException {
        this.clientIRepository.delete(id);
        this.undoRedoService.addToUndo(new AddOperation(clientIRepository, findById(id)));
    }

    public List<Client> findClientByName(String firstName, String lastName){

        List<Client> clientList = new ArrayList<>();
        for(Client client : this.clientIRepository.read()){
            if (client.getLastName().equalsIgnoreCase(lastName) && client.getFirstName().equalsIgnoreCase(firstName)){
                clientList.add(client);
            }
        }
        if (clientList.isEmpty()){
            throw new NullPointerException("The client does not exist or the name is written wrong");
        }
        return clientList;
    }

    public List<Client> sortClientCards(){

        List<Client> clientList = this.clientIRepository.read();
        clientList.sort((client1, client2) -> client1.getProjectsBought().size() < client2.getProjectsBought().size() ? 1 : -1);
        return clientList;
    }

    public List<Client> getAll(){
        return this.clientIRepository.read();
    }

    public List<Integer> getAllProjects(Client client){
        return client.getProjectsBought();
    }

}
