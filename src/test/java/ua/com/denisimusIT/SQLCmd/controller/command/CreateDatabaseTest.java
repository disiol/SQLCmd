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
public class CreateDatabaseTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new CreateDatabase(view, manager);
    }

    @Test
    public void testCreateDatabase() {
        // given

        // when
        String databaseName = "testDatabase";
        command.process("createDatabase" +"|" + databaseName);

        // then
        verify(manager).createDatabase("\"" + databaseName + "\"");
        verify(view).write(String.format("The database: %s successfully", databaseName));
    }


    @Test
    public void testCanProcessClearWithParametersString() {
        // when
        boolean canProcess = command.canProcess("createDatabase|user");

        // then
        assertTrue("testCanProcessClearWithParametersString", canProcess);
    }

    @Test
    public void testCantProcessClearWithoutParametersString() {
        // when
        boolean canProcess = command.canProcess("createDatabase");

        // then
        assertFalse("testCantProcessClearWithoutParametersString", canProcess);
    }

    @Test
    public void testValidationErrorWhenCountParametersIsLessThan2() {
        // when
        try {
            command.process("clear");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("testValidationErrorWhenCountParametersIsLessThan2",
                    "Team format createDatabase|DatabaseName, and you have entered: clear", e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersIsMoreThan2() {
        // when
        try {
            command.process("createDatabase|table|qwe");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("testValidationErrorWhenCountParametersIsMoreThan2",
                    "Team format createDatabase|DatabaseName, and you have entered: createDatabase|table|qwe", e.getMessage());
        }
    }

    @Test
    public void descriptionTest() {
        // when
        view.write(command.description());
        // then
        verify(view).write("created database");
    }

    @Test
    public void formatTest() {
        // when
        view.write(command.format());
        // then
        verify(view).write("createDatabase|DatabaseName");
    }

}
