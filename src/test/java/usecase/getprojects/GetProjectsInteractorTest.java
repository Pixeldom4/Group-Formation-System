package usecase.getprojects;

import dataaccess.IProjectRepository;
import dataaccess.IUserProjectsRepository;
import entities.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashSet;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the GetProjectsInteractor class.
 */
public class GetProjectsInteractorTest {

    private IUserProjectsRepository mockUserProjectsRepository;
    private IProjectRepository mockProjectRepository;
    private GetProjectsOutputBoundary mockGetProjectsPresenter;
    private GetProjectsInteractor interactor;

    /**
     * Sets up the test environment by initializing mocks and the interactor.
     */
    @BeforeEach
    public void setUp() {
        mockUserProjectsRepository = mock(IUserProjectsRepository.class);
        mockProjectRepository = mock(IProjectRepository.class);
        mockGetProjectsPresenter = mock(GetProjectsOutputBoundary.class);
        interactor = new GetProjectsInteractor(mockGetProjectsPresenter);

    }

    /**
     * Tests the getProjects method for a successful retrieval of projects.
     */
    @Test
    public void testGetProjectsSuccess() {
        int userId = 1;
        HashSet<Integer> projectIds = new HashSet<>();
        projectIds.add(101);
        projectIds.add(102);

        Project project1 = new Project(101, "Project 1", 1000.0, "Description 1", new HashSet<>());
        Project project2 = new Project(102, "Project 2", 2000.0, "Description 2", new HashSet<>());

        when(mockUserProjectsRepository.getProjectIdsForUser(userId)).thenReturn(projectIds);
        when(mockProjectRepository.getProjectById(101)).thenReturn(project1);
        when(mockProjectRepository.getProjectById(102)).thenReturn(project2);
        when(mockProjectRepository.getOwnerId(anyInt())).thenReturn(userId);

        GetProjectsInputData inputData = new GetProjectsInputData(userId);
        interactor.getProjects(inputData);

        verify(mockGetProjectsPresenter).prepareSuccessView(any(GetProjectsOutputData.class));
    }

    /**
     * Tests the getProjects method when the user is not logged in.
     */
    @Test
    public void testGetProjectsUserNotLoggedIn() {
        GetProjectsInputData inputData = new GetProjectsInputData(0);
        interactor.getProjects(inputData);

    }

    /**
     * Tests the getProjects method when the project repository fails to retrieve projects.
     */
    @Test
    public void testGetProjectsRepositoryFailure() {
        int userId = 1;
        HashSet<Integer> projectIds = new HashSet<>();
        projectIds.add(101);

        when(mockUserProjectsRepository.getProjectIdsForUser(userId)).thenReturn(projectIds);
        when(mockProjectRepository.getProjectById(101)).thenReturn(null);

        GetProjectsInputData inputData = new GetProjectsInputData(userId);
        interactor.getProjects(inputData);

        verify(mockGetProjectsPresenter).prepareFailView(anyString());
    }
}
