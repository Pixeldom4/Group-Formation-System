package usecase.manageprojects.deleteproject;

import dataaccess.ILoginUserDetails;
import dataaccess.IProjectRepository;
import dataaccess.IUserProjectsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class DeleteProjectInteractorTest {

    private DeleteProjectInteractor interactor;
    private DeleteProjectOutputBoundary presenter;
    private IProjectRepository projectRepository;
    private ILoginUserDetails loginUserDetails;
    private IUserProjectsRepository userProjectsRepository;

    @BeforeEach
    public void setUp() {
        presenter = mock(DeleteProjectOutputBoundary.class);
        projectRepository = mock(IProjectRepository.class);
        loginUserDetails = mock(ILoginUserDetails.class);
        userProjectsRepository = mock(IUserProjectsRepository.class);
        interactor = new DeleteProjectInteractor(presenter, projectRepository, loginUserDetails, userProjectsRepository);
    }

    @Test
    public void testSuccessDeleteProject() {
        DeleteProjectInputData inputData = new DeleteProjectInputData(10);

        when(loginUserDetails.getUserId()).thenReturn(1);
        when(projectRepository.getOwnerId(10)).thenReturn(1);

        interactor.deleteProject(inputData);
        verify(userProjectsRepository, times(1)).removeProjectFromAllUsers(10);
        verify(presenter, times(1)).prepareSuccessView(any());
    }

    @Test
    public void testFailDeleteProject() {
        DeleteProjectInputData inputData = new DeleteProjectInputData(10);

        when(loginUserDetails.getUserId()).thenReturn(1);
        when(projectRepository.getOwnerId(10)).thenReturn(2);

        interactor.deleteProject(inputData);
        verify(userProjectsRepository, never()).removeProjectFromAllUsers(10);
        verify(presenter, times(1)).prepareFailView(any());
    }
}
