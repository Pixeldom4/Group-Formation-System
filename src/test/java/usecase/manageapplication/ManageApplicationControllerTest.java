package usecase.manageapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageapplications.ManageApplicationsController;
import usecase.manageapplications.acceptapplication.AcceptApplicationInputBoundary;
import usecase.manageapplications.getapplications.GetApplicationsInputBoundary;
import usecase.manageapplications.rejectapplication.RejectApplicationInputBoundary;

import static org.mockito.Mockito.*;

public class ManageApplicationControllerTest {
    private ManageApplicationsController controller;
    private GetApplicationsInputBoundary getApplicationsInteractor;
    private AcceptApplicationInputBoundary acceptApplicationInteractor;
    private RejectApplicationInputBoundary rejectApplicationInteractor;

    @BeforeEach
    public void setUp() {
        getApplicationsInteractor = mock(GetApplicationsInputBoundary.class);
        acceptApplicationInteractor = mock(AcceptApplicationInputBoundary.class);
        rejectApplicationInteractor = mock(RejectApplicationInputBoundary.class);
        controller = new ManageApplicationsController(getApplicationsInteractor, acceptApplicationInteractor,
                                                      rejectApplicationInteractor);
    }

    @Test
    public void testCallGetApplicationsInteractor() {
        controller.getApplicationsForProject(1);
        verify(getApplicationsInteractor, times(1)).getApplicationsForProject(any());
    }

    @Test
    public void testCallRejectApplicationInteractor() {
        controller.rejectApplicant(1, 1);
        verify(rejectApplicationInteractor, times(1)).rejectApplicant(any());
    }

    @Test
    public void testCallAcceptApplicationInteractor() {
        controller.acceptApplicant(1, 1);
        verify(acceptApplicationInteractor, times(1)).acceptApplicant(any());
    }
}
