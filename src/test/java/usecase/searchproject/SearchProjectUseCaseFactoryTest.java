package usecase.searchproject;

import org.junit.jupiter.api.Test;
import usecase.searchforproject.SearchProjectController;
import usecase.searchforproject.SearchProjectUseCaseFactory;
import viewmodel.SearchPanelViewModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class SearchProjectUseCaseFactoryTest {

    /**
     * Test the creation of a search project controller.
     */
    @Test
    public void testCreateSearchProjectUseCase() {
        SearchPanelViewModel searchPanelViewModel = mock(SearchPanelViewModel.class);
        SearchProjectController controller = SearchProjectUseCaseFactory.createSearchProjectController(searchPanelViewModel);
        assertNotNull(controller);
    }
}
