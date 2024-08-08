package usecase.logout;

import org.junit.jupiter.api.Test;
import viewmodel.SwitchViewButtonPanelViewModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class LogoutUseCaseFactoryTest {

    @Test
    void testCreateController(){
        // Arrange
        SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel = mock(SwitchViewButtonPanelViewModel.class);

        // Act
        LogoutController logoutController = LogoutUseCaseFactory.create(switchViewButtonPanelViewModel);

        // Assert
        assertNotNull(logoutController);
    }
}
