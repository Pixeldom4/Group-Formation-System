package usecase.edituser;

import dataaccess.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageusers.edituser.EditUserController;
import usecase.manageusers.edituser.EditUserUseCaseFactory;
import viewmodel.EditProfileViewModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Tests for the EditUserUseCaseFactory class.
 */
public class EditUserUseCaseFactoryTest {

    private EditProfileViewModel editProfileViewModel;
    private IUserRepository mockUserRepository;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        editProfileViewModel = mock(EditProfileViewModel.class);
        mockUserRepository = mock(IUserRepository.class);


    }

    /**
     * Tests the creation of EditUserController.
     */
    @Test
    public void testCreate() {
        EditUserController editUserController = EditUserUseCaseFactory.create(editProfileViewModel);
        assertNotNull(editUserController);
    }
}
