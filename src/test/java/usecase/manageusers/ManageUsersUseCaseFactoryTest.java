package usecase.manageusers;

import org.junit.jupiter.api.Test;
import viewmodel.CreateUserPanelViewModel;
import viewmodel.EditProfileViewModel;
import viewmodel.MyProjectsPanelViewModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class ManageUsersUseCaseFactoryTest {

    @Test
    void testCreateController(){
        CreateUserPanelViewModel createUserPanelViewModel = mock(CreateUserPanelViewModel.class);
        EditProfileViewModel editProfileViewModel = mock(EditProfileViewModel.class);
        MyProjectsPanelViewModel myProjectsPanelViewModel = mock(MyProjectsPanelViewModel.class);
        ManageUsersController controller = ManageUsersUseCaseFactory.create(createUserPanelViewModel,
                                                                            editProfileViewModel,
                                                                            myProjectsPanelViewModel);

        assertNotNull(controller);
    }
}
