package repository.test;

import model.Project;
import org.junit.jupiter.api.Test;
import repository.IRepository;
import repository.InMemoryRepository;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepositoryTest {

    @Test
    void CreatingProject_should_saveThatProjectToRepository() {
        IRepository<Project> projectInMemoryRepository = new InMemoryRepository<>();
        Project project = new Project( 0, "Casa M", 2000, 1500, false);
        projectInMemoryRepository.create(project);
        assertEquals(1, projectInMemoryRepository.read().size());
    }

    @Test
    void ReadingIdProject_should_returnThatProject() {
        IRepository<Project> projectInMemoryRepository = new InMemoryRepository<>();
        Project project = new Project( 0, "Casa M", 2000, 1500, false);
        projectInMemoryRepository.create(project);
        assertEquals(1, projectInMemoryRepository.read().size());
        assertEquals(project, projectInMemoryRepository.read(project.getId()));
    }

    @Test
    void ReadingAllProjects_should_returnAListOfProjects() {
        IRepository<Project> projectInMemoryRepository = new InMemoryRepository<>();
        Project project = new Project( 0, "Casa M", 2000, 1500, false);
        Project project1 = new Project( 1, "Casa B", 2015, 2000, false);
        List<Project> test = new ArrayList<>();
        test.add(project);
        test.add(project1);
        projectInMemoryRepository.create(project);
        projectInMemoryRepository.create(project1);
        assertEquals(2, projectInMemoryRepository.read().size());
        assertEquals(test, projectInMemoryRepository.read());
    }

    @Test
    void DeleteProject_should_DeleteThatProjectFromRepository() {
        IRepository<Project> projectInMemoryRepository = new InMemoryRepository<>();
        Project project = new Project( 1, "Casa B", 2015, 2000, false);
        projectInMemoryRepository.create(project);
        try {
            projectInMemoryRepository.delete(project.getId());
        } catch (KeyException e) {
            e.printStackTrace();
        }
        assertEquals(0, projectInMemoryRepository.read().size());
    }

    @Test
    void UpdateProject_should_updateThatProjectFromRepository() {
        IRepository<Project> projectInMemoryRepository = new InMemoryRepository<>();
        Project project = new Project( 1, "Casa B", 2015, 2000, false);
        projectInMemoryRepository.create(project);
        project.setPrice(3000);
        project.setInProgress(false);
        try {
            projectInMemoryRepository.update(project);
        } catch (KeyException e) {
            e.printStackTrace();
        }
        assertEquals(3000,projectInMemoryRepository.read(project.getId()).getPrice());
        assertEquals(false,projectInMemoryRepository.read(project.getId()).getIsInProgress());
        assertEquals(project,projectInMemoryRepository.read(project.getId()));
    }
}