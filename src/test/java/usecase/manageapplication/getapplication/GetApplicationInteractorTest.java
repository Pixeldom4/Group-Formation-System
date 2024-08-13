package usecase.manageapplication.getapplication;

import dataaccess.IApplicationRepository;
import dataaccess.IUserRepository;
import entities.Application;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageapplications.getapplications.*;

import java.util.HashSet;

import static org.mockito.Mockito.*;

public class GetApplicationInteractorTest {
    private GetApplicationsInputBoundary interactor;
    private GetApplicationsOutputBoundary presenter;
    private IApplicationRepository applicationRepository;
    private IUserRepository userRepository;

    @BeforeEach
    public void setUp() {
        applicationRepository = mock(IApplicationRepository.class);
        userRepository = mock(IUserRepository.class);
        presenter = mock(GetApplicationsOutputBoundary.class);
        interactor = new GetApplicationsInteractor(presenter, applicationRepository, userRepository);
    }

    @Test
    void testNoApplicationsFound() {
        when(applicationRepository.getApplicationsForProject(1)).thenReturn(new HashSet<>());
        interactor.getApplicationsForProject(new GetApplicationsInputData(1));
        verify(presenter).prepareSuccessView(argThat(data -> data.applications().length == 0));
    }

    @Test
    void testApplicationsFound() {
        HashSet<Application> applications = new HashSet<>();
        applications.add(new Application(1, 1, "Application text", new byte[]{1, 2, 3}));
        when(applicationRepository.getApplicationsForProject(1)).thenReturn(applications);
        when(userRepository.getUserById(1)).thenReturn(new User(1, "John", "Doe",
                                                                "john@doe.com", null, 111));
        interactor.getApplicationsForProject(new GetApplicationsInputData(1));
        verify(presenter).prepareSuccessView(argThat(data -> data.applications().length == 1));
    }
}
