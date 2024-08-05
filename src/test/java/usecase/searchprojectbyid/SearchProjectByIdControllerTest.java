package usecase.searchprojectbyid;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the SearchProjectByIdController class.
 */
public class SearchProjectByIdControllerTest {

    /**
     * Tests that the searchProjectById method calls the interactor with the correct ID.
     */
    @Test
    void searchProjectByIdCallsInteractorWithCorrectId() {
        SearchProjectByIdInputBoundary mockInteractor = mock(SearchProjectByIdInputBoundary.class);
        SearchProjectByIdController controller = new SearchProjectByIdController(mockInteractor);

        controller.searchProjectById(1);

        verify(mockInteractor).searchProjectById(1);
    }

    /**
     * Tests that the searchProjectById method handles an ID of zero.
     */
    @Test
    void searchProjectByIdHandlesZeroId() {
        SearchProjectByIdInputBoundary mockInteractor = mock(SearchProjectByIdInputBoundary.class);
        SearchProjectByIdController controller = new SearchProjectByIdController(mockInteractor);

        controller.searchProjectById(0);

        verify(mockInteractor).searchProjectById(0);
    }

    /**
     * Tests that the searchProjectById method handles a negative ID.
     */
    @Test
    void searchProjectByIdHandlesNegativeId() {
        SearchProjectByIdInputBoundary mockInteractor = mock(SearchProjectByIdInputBoundary.class);
        SearchProjectByIdController controller = new SearchProjectByIdController(mockInteractor);

        controller.searchProjectById(-1);

        verify(mockInteractor).searchProjectById(-1);
    }
}