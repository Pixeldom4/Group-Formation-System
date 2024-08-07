package usecase.createproject;

import api.embeddingapi.EmbeddingAPIInterface;
import dataaccess.IProjectRepository;
import dataaccess.IUserProjectsRepository;
import entities.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageprojects.createproject.CreateProjectInputData;
import usecase.manageprojects.createproject.CreateProjectInteractor;
import usecase.manageprojects.createproject.CreateProjectOutputBoundary;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the CreateProjectInteractor class.
 */
public class CreateProjectInteractorTest {

    private IProjectRepository mockProjectRepository;
    private IUserProjectsRepository mockUserProjectsRepository;
    private CreateProjectOutputBoundary mockProjectPresenter;
    private EmbeddingAPIInterface mockEmbeddingAPI;
    private CreateProjectInteractor interactor;

    /**
     * Sets up the test environment before each test.
     * Initializes the mock objects and the interactor instance.
     */
    @BeforeEach
    void setUp() {
        mockProjectRepository = mock(IProjectRepository.class);
        mockUserProjectsRepository = mock(IUserProjectsRepository.class);
        mockProjectPresenter = mock(CreateProjectOutputBoundary.class);
        mockEmbeddingAPI = mock(EmbeddingAPIInterface.class);
        interactor = new CreateProjectInteractor(mockProjectRepository, mockUserProjectsRepository,
                                                 mockProjectPresenter, mockEmbeddingAPI);
    }

    /**
     * Tests the scenario where the project creation fails because the repository returns null.
     * Verifies that the failure view is prepared with the appropriate message.
     */
    @Test
    void createProjectFailsWhenRepositoryReturnsNull() {
        CreateProjectInputData inputData = new CreateProjectInputData("Title", 1000.0, "Description", new HashSet<>(Arrays.asList("tag1", "tag2")), 1);
        float[] embeddings = new float[]{0.1f, 0.2f, 0.3f};

        when(mockEmbeddingAPI.getEmbedData("Description")).thenReturn(embeddings);
        when(mockProjectRepository.createProject("Title", 1000.0, "Description", new HashSet<>(Arrays.asList("tag1", "tag2")), embeddings, 1)).thenReturn(null);

        interactor.createProject(inputData);

        verify(mockProjectPresenter).prepareFailView("Failed to create project.");
    }

    /**
     * Tests the scenario where the project creation is successful.
     * Verifies that the success view is prepared with the appropriate output data.
     */
    @Test
    void createProjectSucceeds() {
        CreateProjectInputData inputData = new CreateProjectInputData("Title", 1000.0, "Description", new HashSet<>(Arrays.asList("tag1", "tag2")), 1);
        float[] embeddings = new float[]{0.1f, 0.2f, 0.3f};
        Project project = new Project(1, "Title", 1000.0, "Description", new HashSet<>(Arrays.asList("tag1", "tag2")));

        when(mockEmbeddingAPI.getEmbedData("Description")).thenReturn(embeddings);
        when(mockProjectRepository.createProject("Title", 1000.0, "Description", new HashSet<>(Arrays.asList("tag1", "tag2")), embeddings, 1)).thenReturn(project);

        interactor.createProject(inputData);

        verify(mockUserProjectsRepository).addUserToProject(1, 1);
        //capture the argument passed to the prepareSuccessView method
        verify(mockProjectPresenter).prepareSuccessView(argThat(outputData -> outputData.getProjectId() == 1 &&
                outputData.getTitle().equals("Title") &&
                outputData.getBudget() == 1000.0 &&
                outputData.getDescription().equals("Description") &&
                outputData.getTags().equals(new HashSet<>(Arrays.asList("tag1", "tag2")))));
    }
}