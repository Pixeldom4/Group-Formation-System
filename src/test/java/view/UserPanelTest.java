package view;

import org.junit.Before;
import org.junit.Test;
import usecase.manageusers.getusers.UserData;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class UserPanelTest {

    private UsersPanel usersPanel;

    @Before
    public void setUp() {
        usersPanel = new UsersPanel();
    }

    @Test
    public void testDisplayUsers() {
        // Create a sample set of users using LinkedHashSet to maintain order
        LinkedHashSet<UserData> users = new LinkedHashSet<>();
        users.add(new UserData(1, "John", "Doe", "john.doe@example.com",
                new LinkedHashSet<>(Set.of("Java", "Spring")), 1000.0, true));
        users.add(new UserData(2, "Jane", "Smith", "jane.smith@example.com",
                new LinkedHashSet<>(Set.of("Python", "Django")), 1500.0, false));

        // Display the users in the UsersPanel
        usersPanel.displayUsers(users);

        // Get the JTable from the UsersPanel
        JTable usersTable = usersPanel.getUsersTable();

        // Verify that the table has the correct number of rows
        assertEquals(2, usersTable.getRowCount());

        // Verify the data in the first row
        assertEquals("John", usersTable.getValueAt(0, 0));
        assertEquals("Doe", usersTable.getValueAt(0, 1));
        assertEquals("john.doe@example.com", usersTable.getValueAt(0, 2));

        // Check the tags set (ignoring order)
        HashSet<String> expectedTags = new HashSet<>(Arrays.asList("Java", "Spring"));
        HashSet<String> actualTags = new HashSet<>(Arrays.asList(((String) usersTable.getValueAt(0, 3)).split(", ")));
        assertEquals(expectedTags, actualTags);

        assertEquals(1000.0, usersTable.getValueAt(0, 4));
        assertEquals(true, usersTable.getValueAt(0, 5));

        // Verify the data in the second row
        assertEquals("Jane", usersTable.getValueAt(1, 0));
        assertEquals("Smith", usersTable.getValueAt(1, 1));
        assertEquals("jane.smith@example.com", usersTable.getValueAt(1, 2));

        // Check the tags set (ignoring order)
        expectedTags = new HashSet<>(Arrays.asList("Python", "Django"));
        actualTags = new HashSet<>(Arrays.asList(((String) usersTable.getValueAt(1, 3)).split(", ")));
        assertEquals(expectedTags, actualTags);

        assertEquals(1500.0, usersTable.getValueAt(1, 4));
        assertEquals(false, usersTable.getValueAt(1, 5));
    }
}
