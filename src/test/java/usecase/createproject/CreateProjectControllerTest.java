package usecase.createproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import usecase.manageprojects.createproject.CreateProjectController;
import usecase.manageprojects.createproject.CreateProjectInputBoundary;
import usecase.manageprojects.createproject.CreateProjectInputData;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the CreateProjectController class.
 */
public class CreateProjectControllerTest {

    private CreateProjectInputBoundary mockInteractor;
    private CreateProjectController controller;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        mockInteractor = mock(CreateProjectInputBoundary.class);
        controller = new CreateProjectController(mockInteractor);
    }

    /**
     * Tests that the createProject method handles an empty title correctly.
     */
    @Test
    void createProjectWithEmptyTitle() {
        String title = "";
        double budget = 1000.0;
        String description = "Description";
        HashSet<String> tags = new HashSet<>(Arrays.asList("tag1", "tag2"));
        int creatorUserId = 1;

        controller.createProject(title, budget, description, tags, creatorUserId);

        ArgumentCaptor<CreateProjectInputData> captor = ArgumentCaptor.forClass(CreateProjectInputData.class);
        verify(mockInteractor).createProject(captor.capture());
        CreateProjectInputData capturedArgument = captor.getValue();

        assertEquals(title, capturedArgument.getTitle());
        assertEquals(budget, capturedArgument.getBudget());
        assertEquals(description, capturedArgument.getDescription());
        assertEquals(creatorUserId, capturedArgument.getCreatorUserId());
    }

    /**
     * Tests that the createProject method handles null tags correctly.
     */
    @Test
    void createProjectWithNullTags() {
        String title = "Title";
        double budget = 1000.0;
        String description = "Description";
        HashSet<String> tags = null;
        int creatorUserId = 1;

        controller.createProject(title, budget, description, tags, creatorUserId);

        ArgumentCaptor<CreateProjectInputData> captor = ArgumentCaptor.forClass(CreateProjectInputData.class);
        verify(mockInteractor).createProject(captor.capture());
        CreateProjectInputData capturedArgument = captor.getValue();

        assertEquals(title, capturedArgument.getTitle());
        assertEquals(budget, capturedArgument.getBudget());
        assertEquals(description, capturedArgument.getDescription());
        assertEquals(creatorUserId, capturedArgument.getCreatorUserId());
    }

    /**
     * Tests that the createProject method calls the interactor with the correct data.
     */
    @Test
    void createProjectCallsInteractorWithCorrectData() {
        String title = "Title";
        double budget = 1000.0;
        String description = "Description";
        HashSet<String> tags = new HashSet<>(Arrays.asList("tag1", "tag2"));
        int creatorUserId = 1;

        controller.createProject(title, budget, description, tags, creatorUserId);

        ArgumentCaptor<CreateProjectInputData> captor = ArgumentCaptor.forClass(CreateProjectInputData.class);
        verify(mockInteractor).createProject(captor.capture());
        CreateProjectInputData capturedArgument = captor.getValue();

        assertEquals(title, capturedArgument.getTitle());
        assertEquals(budget, capturedArgument.getBudget());
        assertEquals(description, capturedArgument.getDescription());
        assertEquals(creatorUserId, capturedArgument.getCreatorUserId());
    }

    /**
     * Tests that the createProject method handles a zero budget correctly.
     */
    @Test
    void createProjectWithZeroBudget() {
        String title = "Title";
        double budget = 0.0;
        String description = "Description";
        HashSet<String> tags = new HashSet<>(Arrays.asList("tag1", "tag2"));
        int creatorUserId = 1;

        controller.createProject(title, budget, description, tags, creatorUserId);

        ArgumentCaptor<CreateProjectInputData> captor = ArgumentCaptor.forClass(CreateProjectInputData.class);
        verify(mockInteractor).createProject(captor.capture());
        CreateProjectInputData capturedArgument = captor.getValue();

        assertEquals(title, capturedArgument.getTitle());
        assertEquals(budget, capturedArgument.getBudget());
        assertEquals(description, capturedArgument.getDescription());
        assertEquals(creatorUserId, capturedArgument.getCreatorUserId());
    }
}