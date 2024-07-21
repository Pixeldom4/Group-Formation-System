package usecase.create_user_use_case;

import org.junit.jupiter.api.Test;
import usecase.createuser.CreateUserController;
import usecase.createuser.CreateUserUseCaseFactory;
import viewmodel.CreateUserPanelViewModel;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CreateUserUseCaseFactory class.
 */
class CreateUserUseCaseFactoryTest {

    private CreateUserPanelViewModel viewModel;

    /**
     * Tests that the create method returns a valid CreateUserController instance.
     */
    @Test
    void createReturnsValidController() {
        CreateUserController controller = CreateUserUseCaseFactory.create(viewModel);

        assertNotNull(controller);
    }

}
