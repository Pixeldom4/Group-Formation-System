package usecase.searchproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.searchforproject.SearchProjectController;
import usecase.searchforproject.SearchProjectInputBoundary;

import static org.mockito.Mockito.*;

public class SearchProjectControllerTest {
    private SearchProjectController controller;
    private SearchProjectInputBoundary interactor;

    @BeforeEach
    public void setUp() {
        interactor = mock(SearchProjectInputBoundary.class);
        controller = new SearchProjectController(interactor);
    }

    // Tests for searchProjects method
    @Test
    public void testCallInteractor() {
        String keywords = "Java";

        controller.searchProjects(keywords);

        verify(interactor, times(1)).searchProjects(keywords);
    }
}
