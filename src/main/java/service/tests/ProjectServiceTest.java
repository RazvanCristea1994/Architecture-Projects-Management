package service.tests;

import model.Project;
import model.validators.ProjectValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.IRepository;
import repository.InMemoryRepository;
import service.ProjectService;
import service.UndoRedoService;
import service.validators.ProjectServiceValidator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceTest {

    IRepository<Project> projectIRepository = new InMemoryRepository<>();

    UndoRedoService undoRedoService = new UndoRedoService();

    ProjectValidator projectValidator = new ProjectValidator();
    ProjectServiceValidator projectServiceValidator = new ProjectServiceValidator();
    ProjectService projectService = new ProjectService(projectIRepository, projectValidator, projectServiceValidator, undoRedoService);

    Project project;
    Project projectB;

    @BeforeEach
    void populate() {
        project = new Project(0, "Casa M", 2000, 1500, false);
        projectB = new Project(1, "Casa M", 2000, 1500, false);

        projectService.add(project.getTitle(),
                project.getYear(),
                project.getPrice(), project.getIsInProgress());
    }

    @Test
    void addingAValidMovie_should_saveThatMovieToRepository() {
        assertEquals(1, projectIRepository.read().size());
//        assertEquals(project, projectIRepository.read(project.getId()));
        undoRedoService.undo();
        assertEquals(0, projectIRepository.read().size());
        undoRedoService.redo();
        assertEquals(1, projectIRepository.read().size());
//        assertEquals(project, projectIRepository.read(project.getId()));

    }

    @Test
    void addingPriceIncrease_should_increaseOnlyTheProjectsPricesLowerThanGivenNumber(){
        int increase = 1600;
        this.projectService.increasePrice(increase,0);
        assertEquals(1500,projectIRepository.read(project.getId()).getPrice());
        this.projectService.increasePrice(increase,100);
        assertEquals(1600,projectIRepository.read(project.getId()).getPrice());
        this.projectService.increasePrice(increase,3);
        assertEquals(1603,projectIRepository.read(project.getId()).getPrice());
    }

    @Test
    void getAll_should_returnAListOfProjects(){
        Project project1=new Project(project.getId(), project.getTitle(), project.getYear(),
                project.getPrice(), project.getIsInProgress());
        Project project2=new Project(projectB.getId(), projectB.getTitle(), projectB.getYear(),
                projectB.getPrice(), projectB.getIsInProgress());
        this.projectIRepository.create(project1);
        this.projectIRepository.create(project2);
        List<Project> test = new ArrayList<>();
//        test.add(project);
        test.add(project1);
        test.add(project2);
        assertEquals(test,this.projectService.getAll());
    }
}