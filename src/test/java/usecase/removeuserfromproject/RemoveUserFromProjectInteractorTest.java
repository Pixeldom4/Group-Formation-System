package usecase.removeuserfromproject;

import dataaccess.IProjectRepository;
import dataaccess.IUserProjectsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class RemoveUserFromProjectInteractorTest {
    private RemoveUserFromProjectInteractor interactor;
    private IUserProjectsRepository userProjectsRepository;
    private IProjectRepository projectRepository;
    private RemoveUserFromProjectPresenter presenter;

    @BeforeEach
    public void setUp() {
        userProjectsRepository = mock(IUserProjectsRepository.class);
        projectRepository = mock(IProjectRepository.class);
        presenter = mock(RemoveUserFromProjectPresenter.class);
        interactor = new RemoveUserFromProjectInteractor(userProjectsRepository, projectRepository, presenter);
    }

    @Test
    public void testInsufficientPermission() {
        RemoveUserFromProjectInputData inputData = new RemoveUserFromProjectInputData(1, 2, 3);
        when(projectRepository.getOwnerId(1)).thenReturn(4);
        interactor.removeUserFromProject(inputData);
        verify(presenter).prepareFailView(any(String.class));
    }

    @Test
    public void testSuccess() {
        RemoveUserFromProjectInputData inputData = new RemoveUserFromProjectInputData(1, 2, 3);
        when(projectRepository.getOwnerId(1)).thenReturn(3);
        when(userProjectsRepository.removeUserFromProject(2, 1)).thenReturn(true);
        interactor.removeUserFromProject(inputData);
        verify(presenter).prepareSuccessView(any(RemoveUserFromProjectOutputData.class));
    }

    @Test
    public void testFail() {
        RemoveUserFromProjectInputData inputData = new RemoveUserFromProjectInputData(1, 2, 3);
        when(projectRepository.getOwnerId(1)).thenReturn(3);
        when(userProjectsRepository.removeUserFromProject(2, 1)).thenReturn(false);
        interactor.removeUserFromProject(inputData);
        verify(presenter).prepareFailView(any(String.class));
    }

}
