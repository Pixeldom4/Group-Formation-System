package usecase.manageapplication.createapplication.createapplication;

import dataaccess.IApplicationRepository;
import dataaccess.IUserProjectsRepository;
import entities.Application;
import org.junit.jupiter.api.Test;
import usecase.manageapplications.createapplication.CreateApplicationInputData;
import usecase.manageapplications.createapplication.CreateApplicationInteractor;
import usecase.manageapplications.createapplication.CreateApplicationOutputBoundary;
import usecase.manageapplications.createapplication.CreateApplicationOutputData;

import java.util.Collections;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the CreateApplicationInteractor class.
 */
public class CreateApplicationInteractorTest {

    /**
     * Tests that a valid application creation request results in a successful application creation.
     */
    @Test
    void createApplicationWithValidDataCreatesApplication() {
        CreateApplicationInputData inputData = new CreateApplicationInputData(1, 1, "Application text", new byte[]{1, 2, 3});
        IApplicationRepository mockApplicationRepository = mock(IApplicationRepository.class);
        IUserProjectsRepository mockUserProjectsRepository = mock(IUserProjectsRepository.class);
        CreateApplicationOutputBoundary mockPresenter = mock(CreateApplicationOutputBoundary.class);
        CreateApplicationInteractor interactor = new CreateApplicationInteractor(mockApplicationRepository, mockUserProjectsRepository, mockPresenter);

        // Mocking the repository responses
        when(mockApplicationRepository.createApplication(1, 1, "Application text", new byte[]{1, 2, 3})).thenReturn(new Application(1, 1, "Application text", new byte[]{1, 2, 3}));

        interactor.createApplication(inputData);

        // Verifying that the presenter is called with a success view
        verify(mockPresenter).prepareSuccessView(argThat(CreateApplicationOutputData::isSuccess));
    }

    /**
     * Tests that an application creation request with an empty PDF file results in a failure.
     */
    @Test
    void createApplicationWithEmptyPdfFails() {
        CreateApplicationInputData inputData = new CreateApplicationInputData(1, 1, "Application text", new byte[]{});
        IApplicationRepository mockApplicationRepository = mock(IApplicationRepository.class);
        IUserProjectsRepository mockUserProjectsRepository = mock(IUserProjectsRepository.class);
        CreateApplicationOutputBoundary mockPresenter = mock(CreateApplicationOutputBoundary.class);
        CreateApplicationInteractor interactor = new CreateApplicationInteractor(mockApplicationRepository, mockUserProjectsRepository, mockPresenter);

        interactor.createApplication(inputData);

        // Verifying that the presenter is called with a failure view
        verify(mockPresenter).prepareFailView("You must submit a PDF file!");
    }

    /**
     * Tests that an application creation request with an empty PDF file results in a failure.
     */
    @Test
    void createApplicationWithNullPdfFails() {
        CreateApplicationInputData inputData = new CreateApplicationInputData(1, 1, "Application text", null);
        IApplicationRepository mockApplicationRepository = mock(IApplicationRepository.class);
        IUserProjectsRepository mockUserProjectsRepository = mock(IUserProjectsRepository.class);
        CreateApplicationOutputBoundary mockPresenter = mock(CreateApplicationOutputBoundary.class);
        CreateApplicationInteractor interactor = new CreateApplicationInteractor(mockApplicationRepository, mockUserProjectsRepository, mockPresenter);

        interactor.createApplication(inputData);

        // Verifying that the presenter is called with a failure view
        verify(mockPresenter).prepareFailView("You must submit a PDF file!");
    }

    /**
     * Tests that an application creation request with a large PDF file results in a failure.
     */
    @Test
    void createApplicationWithLargePdfFails() {
        byte[] largePdf = new byte[3 * 1024 * 1024 + 1];
        CreateApplicationInputData inputData = new CreateApplicationInputData(1, 1, "Application text", largePdf);
        IApplicationRepository mockApplicationRepository = mock(IApplicationRepository.class);
        IUserProjectsRepository mockUserProjectsRepository = mock(IUserProjectsRepository.class);
        CreateApplicationOutputBoundary mockPresenter = mock(CreateApplicationOutputBoundary.class);
        CreateApplicationInteractor interactor = new CreateApplicationInteractor(mockApplicationRepository, mockUserProjectsRepository, mockPresenter);

        interactor.createApplication(inputData);

        // Verifying that the presenter is called with a failure view
        verify(mockPresenter).prepareFailView("The PDF file must be less than 3MB!");
    }

    /**
     * Tests that a repository failure during application creation results in a failure.
     */
    @Test
    void createApplicationWithRepositoryFailureFails() {
        CreateApplicationInputData inputData = new CreateApplicationInputData(1, 1, "Application text", new byte[]{1, 2, 3});
        IApplicationRepository mockApplicationRepository = mock(IApplicationRepository.class);
        IUserProjectsRepository mockUserProjectsRepository = mock(IUserProjectsRepository.class);
        CreateApplicationOutputBoundary mockPresenter = mock(CreateApplicationOutputBoundary.class);
        CreateApplicationInteractor interactor = new CreateApplicationInteractor(mockApplicationRepository, mockUserProjectsRepository, mockPresenter);

        // Mocking the repository responses
        when(mockApplicationRepository.createApplication(1, 1, "Application text", new byte[]{1, 2, 3})).thenReturn(null);

        interactor.createApplication(inputData);

        // Verifying that the presenter is called with a failure view
        verify(mockPresenter).prepareFailView("Failed to create application.");
    }

    /**
     * Tests that an application creation request for a project the user already has access to results in a failure.
     */
    @Test
    void createApplicationWithAccessToProjectFails() {
        CreateApplicationInputData inputData = new CreateApplicationInputData(1, 1, "Application text", new byte[]{1, 2, 3});
        IApplicationRepository mockApplicationRepository = mock(IApplicationRepository.class);
        IUserProjectsRepository mockUserProjectsRepository = mock(IUserProjectsRepository.class);
        CreateApplicationOutputBoundary mockPresenter = mock(CreateApplicationOutputBoundary.class);
        CreateApplicationInteractor interactor = new CreateApplicationInteractor(mockApplicationRepository, mockUserProjectsRepository, mockPresenter);

        // Mocking the repository responses
        when(mockUserProjectsRepository.getUserIdsForProject(1)).thenReturn(new HashSet<>(Collections.singletonList(1)));

        interactor.createApplication(inputData);

        // Verifying that the presenter is called with a failure view
        verify(mockPresenter).prepareFailView("You already have access to project.");
    }
}