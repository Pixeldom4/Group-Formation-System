package usecase.deleteuser;

import dataaccess.DataAccessConfig;
import dataaccess.IUserProjectsRepository;

public class DeleteUserInteractor implements DeleteUserInputBoundary {
    private final IUserProjectsRepository userProjectsRepository;
    private final DeleteUserPresenter deleteUserPresenter;

    public DeleteUserInteractor(DeleteUserPresenter deleteUserPresenter) {
        this.deleteUserPresenter = deleteUserPresenter;
        this.userProjectsRepository = DataAccessConfig.getUserProjectsRepository();
    };

    @Override
    public void deleteUser(DeleteUserInputData deleteUserData) {
        int userId = deleteUserData.getUserId();
        int projectId = deleteUserData.getProjectId();
        userProjectsRepository.removeUserFromProject(userId, projectId);

        deleteUserPresenter.prepareSuccessView(new DeleteUserOutputData());
    }
}
