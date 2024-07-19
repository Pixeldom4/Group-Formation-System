package usecase.removeuser;

public class RemoveUserController implements RemoveUserInputBoundary {
    private final RemoveUserInteractor removeUserInteractor;

    public RemoveUserController(RemoveUserInteractor removeUserInteractor){
        this.removeUserInteractor = removeUserInteractor;
    }

    @Override
    public void deleteUser(RemoveUserInputData deleteUserData) {
        removeUserInteractor.deleteUser(deleteUserData);
    }
}
