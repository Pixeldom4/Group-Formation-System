package usecase.getapplications;

import viewmodel.DisplayProjectApplicationViewModel;

public class GetApplicationsUseCaseFactory {

    private GetApplicationsUseCaseFactory(){

    }

    public static GetApplicationsController createController(DisplayProjectApplicationViewModel displayProjectApplicationViewModel){
        GetApplicationsOutputBoundary presenter = new GetApplicationsPresenter(displayProjectApplicationViewModel);
        GetApplicationsInputBoundary interactor = new GetApplicationsInteractor(presenter);
        return new GetApplicationsController(interactor);
    }
}
