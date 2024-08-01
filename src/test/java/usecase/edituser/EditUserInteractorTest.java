package usecase.edituser;

import dataaccess.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EditUserInteractorTest {

    private EditUserInteractor editUserInteractor;
    private EditUserOutputBoundary mockPresenter;
    private IUserRepository mockUserRepository;

    @BeforeEach
    public void setUp() {
        mockPresenter = mock(EditUserOutputBoundary.class);
        mockUserRepository = mock(IUserRepository.class);
        editUserInteractor = new EditUserInteractor(mockPresenter, mockUserRepository);
    }

    /**
     * Tests the editUser method when the user update is successful.
     */
    @Test
    public void testEditUserSuccess() {
        int userId = 1;
        String firstName = "John";
        String lastName = "Doe";
        double desiredCompensation = 75000.0;
        HashSet<String> tags = new HashSet<>();
        tags.add("Developer");
        tags.add("Java");

        EditUserInputData inputData = new EditUserInputData(userId, firstName, lastName, desiredCompensation, tags);

        when(mockUserRepository.updateUser(userId, firstName, lastName, desiredCompensation, tags)).thenReturn(true);

        editUserInteractor.editUser(inputData);

        ArgumentCaptor<EditUserOutputData> captor = ArgumentCaptor.forClass(EditUserOutputData.class);
        verify(mockPresenter).prepareSuccessView(captor.capture());

        EditUserOutputData outputData = captor.getValue();
        assertEquals(userId, outputData.getUserId());
        assertEquals(firstName, outputData.getFirstName());
        assertEquals(lastName, outputData.getLastName());
        assertEquals(desiredCompensation, outputData.getDesiredCompensation(), 0.001);
        assertEquals(tags, outputData.getTags());
    }

    /**
     * Tests the editUser method when the user update fails.
     */
    @Test
    public void testEditUserFail() {
        int userId = 1;
        String firstName = "John";
        String lastName = "Doe";
        double desiredCompensation = 75000.0;
        HashSet<String> tags = new HashSet<>();
        tags.add("Developer");
        tags.add("Java");

        EditUserInputData inputData = new EditUserInputData(userId, firstName, lastName, desiredCompensation, tags);

        when(mockUserRepository.updateUser(userId, firstName, lastName, desiredCompensation, tags)).thenReturn(false);

        editUserInteractor.editUser(inputData);

        verify(mockPresenter).prepareFailView("Failed to edit user.");
    }
}
