package usecase.getapplications;

import dataaccess.DAOImplementationConfig;
import dataaccess.IApplicationRepository;
import entities.Application;

import java.util.HashSet;

public class GetApplicationsInteractor implements GetApplicationsInputBoundary {
    private final IApplicationRepository applicationRepository;
    private final GetApplicationsPresenter getApplicationsPresenter;

    public GetApplicationsInteractor(GetApplicationsPresenter getApplicationsPresenter){
       this.getApplicationsPresenter = getApplicationsPresenter;
       this.applicationRepository = DAOImplementationConfig.getApplicationRepository();
    }

    @Override
    public void getApplicationsForProject(int userId, int projectId) {
        HashSet<Application> applications = applicationRepository.getApplicationsForProject(projectId);
        getApplicationsPresenter.prepareSuccessView(new GetApplicationsOutputData(applications));
    }

    @Override
    public void getApplicationsForSelf(int userId) {
        HashSet<Application> applications = applicationRepository.getApplicationsForUser(userId);
        getApplicationsPresenter.prepareSuccessView(new GetApplicationsOutputData(applications));
    }
}
