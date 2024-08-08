package usecase.manageusers.edituser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import usecase.manageusers.edituser.EditUserOutputData;
import usecase.manageusers.edituser.EditUserPresenter;
import viewmodel.EditProfileViewModel;

import java.util.HashSet;

import static org.mockito.Mockito.verify;

/**
 * Tests for the EditUserPresenter class.
 */
public class EditUserPresenterTest {

    private EditProfileViewModel editProfileViewModel;
    private EditUserPresenter editUserPresenter;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        editProfileViewModel = Mockito.mock(EditProfileViewModel.class);
        editUserPresenter = new EditUserPresenter(editProfileViewModel);
    }

    /**
     * Tests the prepareSuccessView method.
     */
    @Test
    public void testPrepareSuccessView() {
        int userId = 1;
        String firstName = "John";
        String lastName = "Doe";
        double desiredCompensation = 75000.0;
        HashSet<String> tags = new HashSet<>();
        tags.add("Developer");
        tags.add("Java");

        EditUserOutputData outputData = new EditUserOutputData(userId, firstName, lastName, desiredCompensation, tags);

        editUserPresenter.prepareSuccessView(outputData);

        verify(editProfileViewModel).saveSuccess();
    }

    /**
     * Tests the prepareFailView method.
     */
    @Test
    public void testPrepareFailView() {
        String error = "Failed to edit user.";

        editUserPresenter.prepareFailView(error);

        verify(editProfileViewModel).saveFail(error);
    }
}
