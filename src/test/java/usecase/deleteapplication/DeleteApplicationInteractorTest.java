package usecase.deleteapplication;

import dataaccess.IApplicationRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DeleteApplicationInteractorTest {

    @Test
    void deleteApplicationWithValidIdsCallsRepository() {
        int senderUserId = 1;
        int projectId = 1;

        IApplicationRepository mockRepository = mock(IApplicationRepository.class);
        DeleteApplicationPresenter mockPresenter = mock(DeleteApplicationPresenter.class);
        DeleteApplicationInteractor interactor = new DeleteApplicationInteractor(mockPresenter);
        interactor.applicationRepository = mockRepository;

        DeleteApplicationInputData inputData = new DeleteApplicationInputData(senderUserId, projectId);
        interactor.deleteApplication(inputData);

        verify(mockRepository).deleteApplication(senderUserId, projectId);
    }

    @Test
    void deleteApplicationWithValidIdsCallsPresenter() {
        int senderUserId = 1;
        int projectId = 1;

        IApplicationRepository mockRepository = mock(IApplicationRepository.class);
        DeleteApplicationPresenter mockPresenter = mock(DeleteApplicationPresenter.class);
        DeleteApplicationInteractor interactor = new DeleteApplicationInteractor(mockPresenter);
        interactor.applicationRepository = mockRepository;

        DeleteApplicationInputData inputData = new DeleteApplicationInputData(senderUserId, projectId);
        interactor.deleteApplication(inputData);

        verify(mockPresenter).prepareSuccessView(argThat(outputData ->
                outputData.getSenderUserId() == senderUserId && outputData.getProjectId() == projectId));
    }

}
