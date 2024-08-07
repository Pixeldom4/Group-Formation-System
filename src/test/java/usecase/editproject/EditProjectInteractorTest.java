package usecase.editproject;

import api.embeddingapi.EmbeddingAPIInterface;
import api.embeddingapi.OpenAPIDataEmbed;
import dataaccess.IProjectRepository;
import dataaccess.local.LocalProjectRepository;
import entities.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import usecase.manageprojects.editproject.EditProjectInputData;
import usecase.manageprojects.editproject.EditProjectInteractor;
import usecase.manageprojects.editproject.EditProjectOutputBoundary;
import usecase.searchforproject.SearchProjectOutputBoundary;
import usecase.searchforproject.SearchProjectsPresenter;
import viewmodel.SearchPanelViewModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the EditProjectInteractor class.
 */
public class EditProjectInteractorTest {

    private static EditProjectOutputBoundary editPresenter;
    private static IProjectRepository projectDAO;
    private static EmbeddingAPIInterface apiInterface;
    private static EditProjectInteractor editProjectInteractor;
    private static final HashMap<Integer, float[]> embeddings = new HashMap<>();


    private final static String[][] dummyProjects = new String[][]{
            {"1", "Java Project", "1000.0", "A project about Java development, focusing on building robust applications.", "Java;Programming"},
            {"2", "Python Automation", "1500.5", "A project centered around automating tasks with Python scripts.", "Python;Automation"},
            {"3", "Web Development", "2000.0", "This project involves creating responsive websites using HTML, CSS, and JavaScript.", "Web Design;JavaScript"},
            {"4", "Database Management", "1200.0", "A project to enhance database performance and security.", "SQL;Database;Security"},
            {"5", "Machine Learning", "2500.0", "Exploring machine learning algorithms to predict data trends.", "Machine Learning;Data Science"}
    };

    /**
     * Sets up the test environment before all tests.
     */
    @BeforeEach
    public void setUp() throws IOException {
        projectDAO = mock(LocalProjectRepository.class);
        apiInterface = mock(OpenAPIDataEmbed.class);
        editPresenter = mock(EditProjectOutputBoundary.class);
        editProjectInteractor = new EditProjectInteractor(projectDAO, editPresenter, apiInterface);
        for (String[] project : dummyProjects) {
            float[] embedding = randomEmbedding();
            when(apiInterface.getEmbedData(project[3])).thenReturn(embedding);
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
     * Tests editing projects.
     */
    @Test
    public void testEditProjects() {
        // Edit the project with ID 1
        int projectId = 1;

        int ownerId = 0;
        when(projectDAO.getOwnerId(projectId)).thenReturn(ownerId);
        EditProjectInputData inputData = createMockData(projectId);
        when(apiInterface.getEmbedData(ArgumentMatchers.anyString())).thenReturn(randomEmbedding());
        when(projectDAO.update(anyInt(), any(), anyDouble(), any(), any(), any())).thenReturn(true);
        editProjectInteractor.editProject(inputData);

        // capture the output data
        verify(editPresenter).prepareSuccessView(argThat(outputData -> {
            assertEquals(projectId, outputData.getProjectId());
            return true;
        }));
    }

    /**
     * Tests editing projects with insufficient permissions.
     */
    @Test
    public void testEditProjectsWithInsufficientPermissions() {
        // Edit the project with ID 1
        int projectId = 1;

        int ownerId = 1;
        when(projectDAO.getOwnerId(projectId)).thenReturn(ownerId);
        EditProjectInputData inputData = createMockData(projectId);
        editProjectInteractor.editProject(inputData);

        // verify that the presenter prepares a fail view
        verify(editPresenter).prepareFailView(notNull());
    }

    /**
     * Tests editing projects that do not exist.
     */
    @Test
    public void testEditUpdateFail() {
        // Edit the project with ID 1
        int projectId = 1;

        int ownerId = 0;
        when(projectDAO.getOwnerId(projectId)).thenReturn(ownerId);
        EditProjectInputData inputData = createMockData(projectId);
        when(apiInterface.getEmbedData(ArgumentMatchers.anyString())).thenReturn(randomEmbedding());
        when(projectDAO.update(anyInt(), any(), anyDouble(), any(), any(), any())).thenReturn(false);
        editProjectInteractor.editProject(inputData);

        // verify that the presenter prepares a fail view
        verify(editPresenter).prepareFailView(notNull());
    }

    private static float[] randomEmbedding() {
        int size = 6;
        float[] embedding = new float[size];
        for (int i = 0; i < size; i++) {
            embedding[i] = (float) Math.random();
        }
        return embedding;
    }

    private EditProjectInputData createMockData(int projectId) {
        String newTitle = "Updated Java Project";
        double newBudget = 1100.0;
        String newDescription = "An updated project about Java development, focusing on new features.";
        HashSet<String> newTags = new HashSet<>(Arrays.asList("Java", "Programming", "Updated"));
        int editorId = 0;
        return new EditProjectInputData(projectId, newTitle, newBudget, newDescription, newTags, editorId);
    }
}
