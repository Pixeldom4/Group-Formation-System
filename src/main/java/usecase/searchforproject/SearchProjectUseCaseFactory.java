package usecase.searchforproject;

import view.SearchPanel;
import viewmodel.SearchPanelViewModel;

public class SearchProjectUseCaseFactory {

    private SearchProjectUseCaseFactory(){}

    /**
     * Creates a search panel for search project use case.
     * @param searchPanelViewModel the view model of the search panel
     * @return the search panel
     */
    public static SearchPanel createSearchProjectPanel(SearchPanelViewModel searchPanelViewModel){
        SearchProjectController searchProjectController = createSearchProjectController(searchPanelViewModel);
        return new SearchPanel(searchPanelViewModel, searchProjectController);
    }

    private static SearchProjectController createSearchProjectController(SearchPanelViewModel searchPanelViewModel){
        SearchProjectOutputBoundary presenter = new SearchProjectsPresenter(searchPanelViewModel);
        SearchProjectInputBoundary interactor = new SearchProjectsInteractor(presenter);
        return new SearchProjectController(interactor);
    }

}
