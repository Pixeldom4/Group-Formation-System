package usecase.manageapplications;

import view.DisplayProjectApplicationView;

public class ManageApplicationsPresenter implements ManageApplicationsOutputBoundary {
    private final DisplayProjectApplicationView applicationView;

    public ManageApplicationsPresenter(DisplayProjectApplicationView applicationView){
        this.applicationView = applicationView;
    }

    @Override
    public void prepareSuccessProjectApplicationView(ManageApplicationsOutputData outputData) {

        applicationView.displayApplicants(outputData.getApplications());
    }

    public void prepareSuccessSelfApplicationView(ManageApplicationsOutputData outputData) {

    }

    @Override
    public void prepareSuccessDecisionView(String decisionText){

    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
