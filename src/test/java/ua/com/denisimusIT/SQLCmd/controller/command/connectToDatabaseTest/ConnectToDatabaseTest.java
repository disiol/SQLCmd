package ua.com.denisimusIT.SQLCmd.controller.command.connectToDatabaseTest;

import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.controller.command.Command;
import ua.com.denisimusIT.SQLCmd.controller.command.connektToDatabase.ConnectToDatabase;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by indigo on 01.09.2015.
 */
public class ConnectToDatabaseTest {
    private DatabaseManager manager;
    private View view;
    private Command command;
    private String newLine = System.lineSeparator();


    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new ConnectToDatabase(view, manager);
    }

    @Test
    public void ConnectToDatabaseTest() {
        // given
        String databaseName = "databaseName";
        String userName = "userName";
        String password = "password";
        // when
        command.process("connect|" + databaseName + "|" + userName + "|" + password);

        // then

        verify(manager).connectToDatabase(databaseName, userName, password);
        verify(view).write(String.format("Opened database: \"" + databaseName + "\" successfully", databaseName));
    }


    @Test
    public void testCanProcessClearWithParametersString() {
        // when
        boolean canProcess = command.canProcess("connect|databaseName|userName|password");

        // then
        assertTrue("testCanProcessClearWithParametersString", canProcess);
    }

    @Test
    public void testCantProcessClearWithoutParametersString() {
        // when
        boolean canProcess = command.canProcess("");

        // then
        assertFalse("testCantProcessClearWithoutParametersString", canProcess);
    }

    @Test
    public void testValidationErrorWhenCountParametersIsLessThan4() {
        // when
        try {
            command.process("disconnect");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("testValidationErrorWhenCountParametersIsLessThan4",
                    "The number of parameters partitioned by the character '|' is incorrect, " +
                            "it is expected 4, but is: 1" + newLine +
                            "\tTeam format connect|databaseName|userName|password, and you have entered: disconnect", e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersIsMoreThan4() {
        // when
        try {
            command.process("connect|table|qwe|555|555d");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("testValidationErrorWhenCountParametersIsMoreThan4",
                    "The number of parameters partitioned by the character '|' is incorrect, it is expected 4," +
                            " but is: 5" + newLine +
                            "\tTeam format connect|databaseName|userName|password, " +
                            "and you have entered: connect|table|qwe|555|555d", e.getMessage());
        }
    }

    @Test
    public void descriptionTest() {
        // when
        view.write(command.description());
        // then
        verify(view).write("for connection to the database with which we will work");
    }

    @Test
    public void formatTest() {
        // when
        view.write(command.format());
        // then
        verify(view).write("connect|databaseName|userName|password");
    }

}
