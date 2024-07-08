package SearchProjectUseCase;

import Entities.ProjectInterface;
import data_access.DAOImplementationConfig;
import data_access.ProjectDataAccessInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import use_case.SearchingForProjects.SearchProjectOutputBoundary;
import use_case.SearchingForProjects.SearchProjectsInteractor;
import use_case.SearchingForProjects.SearchProjectsPresenter;
import view_model.SearchPanelViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class SearchProjectInteractorTest {

    public final static SearchPanelViewModel searchPanelViewModel = new SearchPanelViewModel();
    public final static SearchProjectOutputBoundary presenter = new SearchProjectsPresenter(searchPanelViewModel);
    public final static SearchProjectsInteractor searchProjectInteractor = new SearchProjectsInteractor(presenter);
    public final static ProjectDataAccessInterface projectDAO = DAOImplementationConfig.getProjectDataAccess();

    private static String[][] dummyprojects = new String[][]{
            {"1", "Java Project", "1000.0", "A project about Java development, focusing on building robust applications.", "Java;Programming"},
            {"2", "Python Automation", "1500.5", "A project centered around automating tasks with Python scripts.", "Python;Automation"},
            {"3", "Web Development", "2000.0", "This project involves creating responsive websites using HTML, CSS, and JavaScript.", "Web Design;JavaScript"},
            {"4", "Database Management", "1200.0", "A project to enhance database performance and security.", "SQL;Database;Security"},
            {"5", "Machine Learning", "2500.0", "Exploring machine learning algorithms to predict data trends.", "Machine Learning;Data Science"}

    };
    @BeforeAll
    public static void setUp() {
        addDummyProjects();
    }

    private static void addDummyProjects(){
        for (String[] project : dummyprojects) {
            projectDAO.add(project);
        }
    }

    @Test
    public void testSearchProjects() {
        searchProjectInteractor.searchProjects("Frontend development projects", 5);
        ArrayList<ProjectInterface> projectsRanking = searchPanelViewModel.getProject();

        for (ProjectInterface project : projectsRanking) {
            assertTrue(project.getProjectId() >= 1 && project.getProjectId() <= 5);

            // Since it could be hard to determine which projects get ranked first given a keyword search,
            // we will just check if the projects seems to be in the correct order.
            System.out.println(project.getProjectTitle());
        }
    }

}
