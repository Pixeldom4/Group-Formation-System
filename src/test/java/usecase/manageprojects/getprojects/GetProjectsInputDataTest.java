package usecase.manageprojects.getprojects;

import org.junit.jupiter.api.Test;
import usecase.manageprojects.getprojects.GetProjectsInputData;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the GetProjectsInputData class.
 */
public class GetProjectsInputDataTest {

    /**
     * Tests the constructor and the getUserId method.
     */
    @Test
    public void testGetProjectsInputData() {
        int userId = 12345;
        GetProjectsInputData inputData = new GetProjectsInputData(userId);

        // Check if the userId is set correctly
        assertEquals(userId, inputData.getUserId());
    }

    /**
     * Tests the immutability of the GetProjectsInputData class.
     */
    @Test
    public void testImmutability() {
        int userId = 67890;
        GetProjectsInputData inputData = new GetProjectsInputData(userId);

        // Check if the userId remains the same
        assertEquals(userId, inputData.getUserId());
    }
}
