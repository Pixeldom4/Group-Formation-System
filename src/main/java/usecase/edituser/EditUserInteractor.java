package usecase.edituser;

import dataaccess.IUserRepository;
import dataaccess.database.UserRepository;

import java.util.HashSet;

public class EditUserInteractor implements EditUserInputBoundary{
    private final EditUserOutputBoundary editUserPresenter;
    private final IUserRepository userRepository;

    EditUserInteractor(EditUserOutputBoundary editUserPresenter, IUserRepository userRepository) {
        this.editUserPresenter = editUserPresenter;
        this.userRepository = userRepository;
    }

    @Override
    public void editUser(EditUserInputData inputData) {
        int userId = inputData.getUserId();
        String firstName = inputData.getFirstName();
        String lastName = inputData.getLastName();
        double desiredCompensation = inputData.getDesiredCompensation();
        HashSet<String> tags = inputData.getTags();

        if (userRepository.updateUser(userId, firstName, lastName, desiredCompensation, tags)) {
            EditUserOutputData outputData = new EditUserOutputData(userId, firstName, lastName, desiredCompensation, tags);
            editUserPresenter.prepareSuccessView(outputData);
        } else {
            editUserPresenter.prepareFailView("Failed to edit user.");
        }
    }
}
