package usecase.manageusers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageusers.createuser.CreateUserInputBoundary;
import usecase.manageusers.deleteuser.DeleteUserInputBoundary;
import usecase.manageusers.edituser.EditUserInputBoundary;
import usecase.manageusers.getusers.GetUsersInputBoundary;

import static org.mockito.Mockito.*;

public class ManageUsersControllerTest {
    private ManageUsersController controller;
    private CreateUserInputBoundary createUserInteractor;
    private DeleteUserInputBoundary deleteUserInteractor;
    private EditUserInputBoundary editUserInteractor;
    private GetUsersInputBoundary getUsersInteractor;

    @BeforeEach
    public void setUp() {
        createUserInteractor = mock(CreateUserInputBoundary.class);
        deleteUserInteractor = mock(DeleteUserInputBoundary.class);
        editUserInteractor = mock(EditUserInputBoundary.class);
        getUsersInteractor = mock(GetUsersInputBoundary.class);
        controller = new ManageUsersController(createUserInteractor, deleteUserInteractor,
                                               editUserInteractor, getUsersInteractor);
    }

    @Test
    public void testCallCreateUserInteractor() {
        controller.createUser("name", "last", "email", 1, null, "password");
        verify(createUserInteractor, times(1)).createUser(any());
    }

    @Test
    public void testCallDeleteUserInteractor() {
        controller.deleteUser(1);
        verify(deleteUserInteractor, times(1)).deleteUser(any());
    }

    @Test
    public void testCallEditUserInteractor() {
        controller.editUser(1, "newName", "newLast", "newEmail", 1, null);
        verify(editUserInteractor, times(1)).editUser(any());
    }

    @Test
    public void testCallGetUsersInteractor() {
        controller.getUsers(1);
        verify(getUsersInteractor, times(1)).getUsers(any());
    }
}
