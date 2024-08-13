package usecase.createverification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateVerificationControllerTest {


    @Test
    public void checkCallsInputBoundaryCreateVerification() {
        CreateVerificationInputBoundary inputBoundary = mock(CreateVerificationInputBoundary.class);
        CreateVerificationController controller = new CreateVerificationController(inputBoundary);
        controller.createVerification();
        verify(inputBoundary).createVerification();
    }

}
