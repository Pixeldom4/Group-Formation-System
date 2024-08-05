package usecase.loginuser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import viewmodel.LoginPanelViewModel;

import java.util.HashSet;

import static org.mockito.Mockito.*;

public class LoginUserPresenterTest {

    private LoginPanelViewModel loginPanelViewModel;
    private LoginUserPresenter loginUserPresenter;

    @BeforeEach
    public void setUp() {
        loginPanelViewModel = mock(LoginPanelViewModel.class);
        loginUserPresenter = new LoginUserPresenter(loginPanelViewModel);
    }

    @Test
    public void testValidOutputData() {
        LoginUserOutputData outputData = new LoginUserOutputData(1, "John", "Doe", "john.doe@example.com", 1000.0, new HashSet<>(), true);
        loginUserPresenter.prepareSuccessView(outputData);
        verify(loginPanelViewModel).setLoginUser(1);
        verify(loginPanelViewModel).setLoginName("John Doe");
        verify(loginPanelViewModel).setSuccess(true);
        verify(loginPanelViewModel).firePropertyChanged();
    }

    @Test
    public void testErrorMessage() {
        String errorMessage = "Login failed";
        loginUserPresenter.prepareFailView(errorMessage);
        verify(loginPanelViewModel).setErrorMessage(errorMessage);
        verify(loginPanelViewModel).setSuccess(false);
        verify(loginPanelViewModel).firePropertyChanged();
    }
}