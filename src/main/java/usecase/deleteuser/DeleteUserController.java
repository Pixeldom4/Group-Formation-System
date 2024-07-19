package usecase.deleteuser;

public class DeleteUserController implements DeleteUserInputBoundary{
    private final DeleteUserInteractor deleteUserInteractor;

    public DeleteUserController(DeleteUserInteractor deleteUserInteractor){
        this.deleteUserInteractor = deleteUserInteractor;
    }

    @Override
    public void deleteUser(DeleteUserInputData deleteUserData) {
        deleteUserInteractor.deleteUser(deleteUserData);
    }
}
