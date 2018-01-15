package ua.com.denisimusIT.SQLCmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by indigo on 01.09.2015.
 */
public class DisconnectOfDatabaseTest {
    //TODO
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new DisconnectOfDatabase(view, manager);
    }

    @Test
    public void DisconnectOfDatabaseTest() {
        // given

        // when
        String databaseName = "testDatabase";
        command.process("disconnect" + "|" + databaseName);

        // then
        verify(manager).disconnectOfDatabase(databaseName);
        verify(view).write(String.format("Disconnect of database %s is successfully", databaseName));
    }


    @Test
    public void testCanProcessClearWithParametersString() {
        // when
        boolean canProcess = command.canProcess("disconnect|testDatabase");

        // then
        assertTrue("testCanProcessClearWithParametersString", canProcess);
    }

    @Test
    public void testCantProcessClearWithoutParametersString() {
        // when
        boolean canProcess = command.canProcess("disconnect");

        // then
        assertFalse("testCantProcessClearWithoutParametersString", canProcess);
    }

    @Test
    public void testValidationErrorWhenCountParametersIsLessThan2() {
        // when
        try {
            command.process("disconnect");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("testValidationErrorWhenCountParametersIsLessThan2",
                    "The number of parameters partitioned by the character '|' is incorrect, it is expected  2, but is: 1\n" +
                            "\tTeam format disconnect|databaseName, and you have entered: disconnect", e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersIsMoreThan2() {
        // when
        try {
            command.process("disconnect|table|qwe");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("testValidationErrorWhenCountParametersIsMoreThan2",
                    "The number of parameters partitioned by the character '|' is incorrect, it is expected  2, but is: 3\n" +
                            "\tTeam format disconnect|databaseName, and you have entered: disconnect|table|qwe", e.getMessage());
        }
    }

    @Test
    public void descriptionTest() {
        // when
        view.write(command.description());
        // then
        verify(view).write("Disconnect of database");
    }

    @Test
    public void formatTest() {
        // when
        view.write(command.format());
        // then
        verify(view).write("disconnect|databaseName");
    }

}
