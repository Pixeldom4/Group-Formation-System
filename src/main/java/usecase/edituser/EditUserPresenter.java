package usecase.edituser;

import viewmodel.EditProfileViewModel;
import viewmodel.EditProfileViewModel;

public class EditUserPresenter implements EditUserOutputBoundary{
    private final EditProfileViewModel editProfileViewModel;

    public EditUserPresenter(EditProfileViewModel editProfileViewModel){
        this.editProfileViewModel = editProfileViewModel;
    }

    @Override
    public void prepareSuccessView(EditUserOutputData outputData) {

    }

    @Override
    public void prepareFailView(String error) {

    }
}
