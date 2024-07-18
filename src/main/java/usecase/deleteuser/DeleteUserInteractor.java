package usecase.deleteuser;

import dataaccess.DAOImplementationConfig;
import dataaccess.IUserProjectsRepository;
import dataaccess.database.UserProjectsRepository;

public class DeleteUserInteractor implements DeleteUserInputBoundary {
    private final IUserProjectsRepository userProjectsRepository;
    private final DeleteUserPresenter deleteUserPresenter;

    public DeleteUserInteractor(DeleteUserPresenter deleteUserPresenter) {
        this.deleteUserPresenter = deleteUserPresenter;
        this.userProjectsRepository = DAOImplementationConfig.getUserProjectsRepository();
    };

    @Override
    public void deleteUser(DeleteUserInputData deleteUserData) {
        int userId = deleteUserData.getUserId();
        int projectId = deleteUserData.getProjectId();
        userProjectsRepository.removeUserFromProject(userId, projectId);

        deleteUserPresenter.prepareSuccessView(new DeleteUserOutputData());
    }
}
