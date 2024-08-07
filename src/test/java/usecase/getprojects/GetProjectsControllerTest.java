package usecase.getprojects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import usecase.manageprojects.getprojects.GetProjectsController;
import usecase.manageprojects.getprojects.GetProjectsInputBoundary;
import usecase.manageprojects.getprojects.GetProjectsInputData;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for the GetProjectsController class.
 */
public class GetProjectsControllerTest {

    private GetProjectsInputBoundary mockGetProjectsInteractor;
    private GetProjectsController getProjectsController;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        mockGetProjectsInteractor = mock(GetProjectsInputBoundary.class);
        getProjectsController = new GetProjectsController(mockGetProjectsInteractor);
    }

    /**
     * Tests the getProjects method of GetProjectsController.
     */
    @Test
    public void testGetProjects() {
        int userId = 12345;
        getProjectsController.getProjects(userId);
        verify(mockGetProjectsInteractor).getProjects(Mockito.any(GetProjectsInputData.class));
    }
}
