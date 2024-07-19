package usecase.removeuser;

import dataaccess.DataAccessConfig;
import dataaccess.IUserProjectsRepository;

public class RemoveUserInteractor implements RemoveUserInputBoundary {
    private final IUserProjectsRepository userProjectsRepository;
    private final RemoveUserPresenter deleteUserPresenter;

    public RemoveUserInteractor(RemoveUserPresenter deleteUserPresenter) {
        this.deleteUserPresenter = deleteUserPresenter;
        this.userProjectsRepository = DataAccessConfig.getUserProjectsRepository();
    };

    @Override
    public void deleteUser(RemoveUserInputData deleteUserData) {
        int userId = deleteUserData.getUserId();
        int projectId = deleteUserData.getProjectId();
        userProjectsRepository.removeUserFromProject(userId, projectId);

        deleteUserPresenter.prepareSuccessView(new RemoveUserOutputData());
    }
}
