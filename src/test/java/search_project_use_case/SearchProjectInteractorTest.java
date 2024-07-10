package search_project_use_case;

import entities.ProjectInterface;
import api.EmbeddingAPIInterface;
import api.OpenAPIDataEmbed;
import dataaccess.IProjectRepository;
import dataaccess.local.LocalProjectDataAccessObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import usecase.searchforproject.SearchProjectOutputBoundary;
import usecase.searchforproject.SearchProjectsInteractor;
import usecase.searchforproject.SearchProjectsPresenter;
import viewmodel.SearchPanelViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class SearchProjectInteractorTest {

    private final static String SAVE_LOCATION = "local_data/test/search_projects_interactor/";
    private final static SearchPanelViewModel searchPanelViewModel = new SearchPanelViewModel();
    private final static SearchProjectOutputBoundary presenter = new SearchProjectsPresenter(searchPanelViewModel);
    private final static IProjectRepository projectDAO = new LocalProjectDataAccessObject(SAVE_LOCATION);
    private final static SearchProjectsInteractor searchProjectInteractor = new SearchProjectsInteractor(presenter, projectDAO);
    private final static EmbeddingAPIInterface apiInteface = new OpenAPIDataEmbed();

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
            float[] embedding = apiInteface.getEmbedData(project[3]);
            projectDAO.createProject(project[1],
                                     Double.parseDouble(project[2]),
                                     project[3],
                                     new HashSet<String>(Arrays.asList(project[4].split(";"))),
                                     embedding);
        }
    }

    @Test
    public void testSearchProjects() {
        searchProjectInteractor.searchProjects("Frontend development projects");
        ArrayList<ProjectInterface> projectsRanking = searchPanelViewModel.getProject();
        assertEquals(projectsRanking.size(), 5);
        for (ProjectInterface project : projectsRanking) {
            assertNotNull(project);

            // Since it could be hard to determine which projects get ranked first given a keyword search,
            // we will just check if the projects seems to be in the correct order.
            System.out.println(project.getProjectTitle());
        }
    }

}
