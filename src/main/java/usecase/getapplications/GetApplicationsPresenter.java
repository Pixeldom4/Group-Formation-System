package usecase.getapplications;

import view.DisplayProjectApplicationView;

public class GetApplicationsPresenter implements GetApplicationsOutputBoundary {
    private final DisplayProjectApplicationView applicationView;

    public GetApplicationsPresenter(DisplayProjectApplicationView applicationView){
        this.applicationView = applicationView;
    }

    @Override
    public void prepareSuccessView(GetApplicationsOutputData outputData){
        applicationView.displayApplicants(outputData.getApplications());
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
