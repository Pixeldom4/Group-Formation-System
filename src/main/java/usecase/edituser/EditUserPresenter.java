package usecase.edituser;

import viewmodel.AddProjectPanelViewModel;
import viewmodel.EditUserViewModel;
import viewmodel.MyProfileViewModel;

public class EditUserPresenter implements EditUserOutputBoundary{
    private final MyProfileViewModel myProfileViewModel;

    public EditUserPresenter(MyProfileViewModel myProfileViewModel){
        this.myProfileViewModel = myProfileViewModel;
    }

    @Override
    public void prepareSuccessView(EditUserOutputData outputData) {

    }

    @Override
    public void prepareFailView(String error) {

    }
}
