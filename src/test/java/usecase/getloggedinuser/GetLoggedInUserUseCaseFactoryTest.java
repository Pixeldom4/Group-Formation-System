package usecase.getloggedinuser;

import org.junit.jupiter.api.Test;
import usecase.manageusers.getloggedinuser.GetLoggedInUserController;
import usecase.manageusers.getloggedinuser.GetLoggedInUserUseCaseFactory;
import usecase.manageusers.getloggedinuser.LoggedInDataAccessViewModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class GetLoggedInUserUseCaseFactoryTest {
    @Test
    public void testMakeGetLoggedInUserUseCase() {
        GetLoggedInUserController controller = GetLoggedInUserUseCaseFactory.create(mock(LoggedInDataAccessViewModel.class));
        assertNotNull(controller);
    }
}
