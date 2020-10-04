package service;

import service.validators.ProjectServiceValidator;
import model.Project;
import repository.IRepository;
import model.undoredo.AddOperation;
import model.validators.ProjectValidator;

import java.security.KeyException;
import java.util.List;


public class ProjectService {

    private IRepository<Project> projectIRepository;
    private ProjectValidator projectValidator;
    private ProjectServiceValidator projectServiceValidator;

    private UndoRedoService undoRedoService;

    private static int id = -1;

    public ProjectService(IRepository<Project> projectIRepository, ProjectValidator projectValidator,
                          ProjectServiceValidator projectServiceValidator, UndoRedoService undoRedoService) {
        this.projectIRepository = projectIRepository;
        this.projectValidator = projectValidator;
        this.projectServiceValidator = projectServiceValidator;
        this.undoRedoService = undoRedoService;
    }

    /**
     * Add a new project. Undoable operation
     * @param title
     * @param year
     * @param price
     * @param isInProgress
     * @throws IllegalArgumentException
     */
    public void add(String title, int year, int price, boolean isInProgress) throws IllegalArgumentException{

        String message = projectValidator.validate(title, year, price);
        if (!message.equals("")){
            throw new IllegalArgumentException(message);
        }
        ProjectService.id++;
        Project project = new Project(ProjectService.id, title, year, price, isInProgress);
        this.projectIRepository.create(project);
        this.undoRedoService.addToUndo(new AddOperation(projectIRepository, project));
    }

    /**
     * Print the entire list of projects
     */
    public void printListOfProjects(){
        System.out.println("\nThe list of projects: ");
        for(Project project : this.projectIRepository.read()){
            System.out.println(project);
        }
    }

    /**
     * Updates an existing project
     * @param id
     * @param title
     * @param year
     * @param price
     * @param isInProgress
     * @throws KeyException
     * @throws IllegalArgumentException
     */
    public void update(int id, String title, int year, int price, boolean isInProgress) throws KeyException, IllegalArgumentException {

        StringBuilder messages = new StringBuilder();
        messages.append(projectServiceValidator.validate(findById(id)));
        messages.append(projectValidator.validate(title, year, price));
        if (!messages.toString().equals("")){
            throw new IllegalArgumentException(String.valueOf(messages));
        }

        Project project = new Project(id, title, year, price, isInProgress);
        this.projectIRepository.update(project);
    }

    public void delete(int id) throws KeyException{
        this.projectIRepository.delete(id);
        this.undoRedoService.addToUndo(new AddOperation(projectIRepository, findById(id)));
    }

    public Project findById(int id){
        return projectIRepository.read(id);
    }

    public Project findProjectByTitle(String title){

        Project project = null;
        for(Project projectToFind :  this.projectIRepository.read()){
            if (projectToFind.getTitle().equalsIgnoreCase(title)){
                project = projectToFind;
            }
        }
        if (project == null){
            throw new NullPointerException("The project does not exist in our DB or the title is written wrong");
        }
        return project;
    }

    public List<Project> getAll(){
        return this.projectIRepository.read();
    }

    public void increasePrice(int valueLimit, int valueToAdd){

        String message = this.projectServiceValidator.validateIncrease(valueLimit, valueToAdd);
        if (!message.equals("")){
            throw new IllegalArgumentException(message);
        }
        for(Project project : this.projectIRepository.read()){
            if(project.getPrice() <= valueLimit) {
                project.setPrice(project.getPrice() + valueToAdd);
            }
        }
    }

    public List<Project> sortProjects(){

        List<Project> sortedProjects = projectIRepository.read();
        sortedProjects.sort((project1, project2) -> project1.getPrice() < project2.getPrice() ? 1 : -1);
        return sortedProjects;
    }
}
