package usecase.loginuser;

import org.junit.jupiter.api.Test;
import viewmodel.LoginPanelViewModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginUserUseCaseFactoryTest {

    @Test
    public void testReturnController() {
        LoginPanelViewModel viewModel = mock(LoginPanelViewModel.class);
        LoginUserController controller = LoginUserUseCaseFactory.create(viewModel);
        assertNotNull(controller);
    }

}