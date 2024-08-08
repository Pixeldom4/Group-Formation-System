package usecase.manageusers.getuser;

import dataaccess.IProjectRepository;
import dataaccess.IUserProjectsRepository;
import dataaccess.IUserRepository;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageusers.getusers.GetUsersInputBoundary;
import usecase.manageusers.getusers.GetUsersInputData;
import usecase.manageusers.getusers.GetUsersInteractor;
import usecase.manageusers.getusers.GetUsersOutputBoundary;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetUsersInteractorTest {
    private GetUsersInputBoundary interactor;
    private IUserProjectsRepository userProjectsRepository;
    private IUserRepository userRepository;
    private IProjectRepository projectRepository;
    private GetUsersOutputBoundary presenter;

    @BeforeEach
    public void setUp() {
        userProjectsRepository = mock(IUserProjectsRepository.class);
        userRepository = mock(IUserRepository.class);
        projectRepository = mock(IProjectRepository.class);
        presenter = mock(GetUsersOutputBoundary.class);
        interactor = new GetUsersInteractor(userProjectsRepository, userRepository, projectRepository, presenter);
    }

    @Test
    public void testNoInputProject() {
        GetUsersInputData inputData = new GetUsersInputData(0);
        interactor.getUsers(inputData);
        verify(presenter, times(1)).prepareFailView("Project ID not provided");
    }

    @Test
    public void testNoUsersInProject() {
        GetUsersInputData inputData = new GetUsersInputData(1);
        when(userProjectsRepository.getUserIdsForProject(1)).thenReturn(new java.util.HashSet<>());
        interactor.getUsers(inputData);
        verify(presenter, times(1)).prepareSuccessView(argThat(data -> data.getUsers().isEmpty()));
    }

    @Test
    public void testOneUserInProject() {
        GetUsersInputData inputData = new GetUsersInputData(1);
        HashSet<String> tags = new HashSet<>(Arrays.asList("Java", "Python"));
        when(userProjectsRepository.getUserIdsForProject(1)).thenReturn(new java.util.HashSet<>(List.of(1)));
        when(userRepository.getUserById(1)).thenReturn(new User(1, "John", "Doe",
                                                                "test@email.com",
                                                                tags, 1));
        when(projectRepository.getOwnerId(1)).thenReturn(1);
        interactor.getUsers(inputData);
        verify(presenter, times(1)).prepareSuccessView(argThat(data -> {
            assertEquals(1, data.getUsers().size());
            assertNotSame(tags, data.getUsers().iterator().next().tags());
            return true;
        }));
    }
}
