package usecase.edituser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import usecase.manageusers.edituser.EditUserController;
import usecase.manageusers.edituser.EditUserInputBoundary;
import usecase.manageusers.edituser.EditUserInputData;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Test class for EditUserController.
 * This class tests the functionality of the EditUserController class.
 */
public class EditUserControllerTest {

    private EditUserInputBoundary mockInteractor;
    private EditUserController controller;

    /**
     * Setup method to initialize the mock interactor and controller before each test.
     */
    @BeforeEach
    public void setUp() {
        mockInteractor = Mockito.mock(EditUserInputBoundary.class);
        controller = new EditUserController(mockInteractor);
    }

    /**
     * Test method to verify the editUser functionality.
     * This method tests if the editUser method of the controller correctly processes the input data
     * and interacts with the interactor with the correct parameters.
     */
    @Test
    public void testEditUser() {
        int userId = 1;
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        double desiredCompensation = 50000;
        HashSet<String> tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");

        // Call the method to be tested
        controller.editUser(userId, firstName, lastName, email, desiredCompensation, tags);

        // Capture the argument passed to the interactor
        ArgumentCaptor<EditUserInputData> captor = ArgumentCaptor.forClass(EditUserInputData.class);
        verify(mockInteractor).editUser(captor.capture());

        // Retrieve the captured argument
        EditUserInputData capturedInputData = captor.getValue();

        // Assert that the captured data matches the input data
        assertEquals(userId, capturedInputData.getUserId());
        assertEquals(firstName, capturedInputData.getFirstName());
        assertEquals(lastName, capturedInputData.getLastName());
        assertEquals(desiredCompensation, capturedInputData.getDesiredCompensation());
        assertEquals(tags, capturedInputData.getTags());
    }
}
