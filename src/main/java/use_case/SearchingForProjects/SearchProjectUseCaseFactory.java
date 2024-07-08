package use_case.SearchingForProjects;

import view.SearchPanel;
import view_model.SearchPanelViewModel;

public class SearchProjectUseCaseFactory {

    private SearchProjectUseCaseFactory(){}

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
