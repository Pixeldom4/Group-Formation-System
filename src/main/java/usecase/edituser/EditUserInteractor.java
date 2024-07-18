package usecase.edituser;

public class EditUserInteractor implements EditUserInputBoundary{
    private final EditUserOutputBoundary editUserPresenter;

    EditUserInteractor(EditUserOutputBoundary editUserPresenter) {
        this.editUserPresenter = editUserPresenter;
    }

    @Override
    public void editUser(EditUserInputBoundary inputData) {
        //TODO implement this method
    }
}
