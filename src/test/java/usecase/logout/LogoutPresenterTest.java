package usecase.logout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import viewmodel.SwitchViewButtonPanelViewModel;

import static org.mockito.Mockito.*;

public class LogoutPresenterTest {
    private LogoutPresenter presenter;
    private SwitchViewButtonPanelViewModel viewModel;

    @BeforeEach
    public void setUp() {
        viewModel = mock(SwitchViewButtonPanelViewModel.class);
        presenter = new LogoutPresenter(viewModel);
    }

    @Test
    void testLogoutSuccess() {
        presenter.logoutSuccess();
        verify(viewModel, times(1)).logout();
    }

    @Test
    void testLogoutFail() {
        presenter.logoutFailed();
        verify(viewModel, times(1)).logoutFail();
    }
}
