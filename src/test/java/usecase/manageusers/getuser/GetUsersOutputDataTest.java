package usecase.manageusers.getuser;

import org.junit.jupiter.api.Test;
import usecase.manageusers.getusers.GetUsersOutputData;
import usecase.manageusers.getusers.UserData;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetUsersOutputDataTest {

    @Test
    void testReturnData(){
        HashSet<UserData> users = new HashSet<>();
        HashSet<String> tags = new HashSet<>(Arrays.asList("Java", "Python"));
        UserData testUser = new UserData(1, "John", "Doe", "john@doe.com",
                                         tags, 1, true);
        users.add(testUser);
        GetUsersOutputData outputData = new GetUsersOutputData(users);
        assertTrue(outputData.getUsers().contains(testUser));
    }
}
