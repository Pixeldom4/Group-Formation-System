package usecase.searchproject;

import api.embeddingapi.EmbeddingAPIInterface;
import api.embeddingapi.OpenAPIDataEmbed;
import dataaccess.IProjectRepository;
import dataaccess.local.LocalProjectRepository;
import entities.Project;
import entities.ProjectInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import usecase.searchforproject.SearchProjectOutputBoundary;
import usecase.searchforproject.SearchProjectsInteractor;
import usecase.searchforproject.SearchProjectsPresenter;
import viewmodel.SearchPanelViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the SearchProjectInteractor class.
 */
@SuppressWarnings("FieldCanBeLocal")
public class SearchProjectInteractorTest {

    private final static SearchPanelViewModel searchPanelViewModel = new SearchPanelViewModel();
    private final static SearchProjectOutputBoundary presenter = new SearchProjectsPresenter(searchPanelViewModel);
    private static IProjectRepository projectDAO;
    private static SearchProjectsInteractor searchProjectInteractor;
    private static EmbeddingAPIInterface apiInteface;
    private static final HashMap<Integer, float[]> embeddings = new HashMap<>();

    private final static String[][] dummyprojects = new String[][]{
            {"1", "Java Project", "1000.0", "A project about Java development, focusing on building robust applications.", "Java;Programming"},
            {"2", "Python Automation", "1500.5", "A project centered around automating tasks with Python scripts.", "Python;Automation"},
            {"3", "Web Development", "2000.0", "This project involves creating responsive websites using HTML, CSS, and JavaScript.", "Web Design;JavaScript"},
            {"4", "Database Management", "1200.0", "A project to enhance database performance and security.", "SQL;Database;Security"},
            {"5", "Machine Learning", "2500.0", "Exploring machine learning algorithms to predict data trends.", "Machine Learning;Data Science"}
    };

    /**
     * Sets up the test environment before all tests.
     */
    @BeforeAll
    public static void setUp() throws IOException {
        projectDAO = mock(LocalProjectRepository.class);
        apiInteface = mock(OpenAPIDataEmbed.class);
        searchProjectInteractor = new SearchProjectsInteractor(presenter, projectDAO, apiInteface);
        for (String[] project : dummyprojects) {
            float[] embedding = randomEmbedding();
            when(apiInteface.getEmbedData(project[3])).thenReturn(embedding);
            embeddings.put(Integer.parseInt(project[0]), embedding);

            Project newProject = new Project(Integer.parseInt(project[0]),
                                             project[1],
                                             Double.parseDouble(project[2]),
                                             project[3],
                                             new HashSet<>(Arrays.asList(project[4].split(";"))));
            when(projectDAO.getProjectById(Integer.parseInt(project[0]))).thenReturn(newProject);
        }

        when(projectDAO.getAllEmbeddings()).thenReturn(embeddings);
    }

    /**
     * Tests the search functionality of the SearchProjectsInteractor.
     */
    @Test
    public void testSearchProjects() {
        String searchQuery = "Frontend development projects";
        when(apiInteface.getEmbedData(searchQuery)).thenReturn(randomEmbedding());
        searchProjectInteractor.searchProjects(searchQuery);
        ArrayList<ProjectInterface> projectsRanking = searchPanelViewModel.getProject();
        assertEquals(projectsRanking.size(), 5);
        for (ProjectInterface project : projectsRanking) {
            assertNotNull(project);
        }
    }

    private static float[] randomEmbedding() {
        int size = 6;
        float[] embedding = new float[size];
        for (int i = 0; i < size; i++) {
            embedding[i] = (float) Math.random();
        }
        return embedding;
    }
}
