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
public class DropDatabaseTest {
    //TODO
    private DatabaseManager manager;
    private View view;
    private Command command;
    private String newLine = System.lineSeparator();


    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new DropDatabase(view, manager);
    }

    @Test
    public void DropDatabaseMassageTest() {
        // given

        // when
        String databaseName = "testDatabase";
        command.process("dropDatabase" + "|" + databaseName);

        // then
        verify(manager).dropDatabase(databaseName);
        verify(view).write(String.format("Database  %s deleted successfully", databaseName));
    }



    @Test
    public void DropDatabaseManagerTest() {
        // given

        // when
        String databaseName = "testDatabase";
        command.process("dropDatabase" + "|" + databaseName);

        // then
        verify(manager).dropDatabase(databaseName);
    }


    @Test
    public void testCanProcessClearWithParametersString() {
        // when
        boolean canProcess = command.canProcess("dropDatabase|testDatabase");

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
                    "The number of parameters partitioned by the character '|' is incorrect, " +
                             "it is expected  2, but is: 1" + newLine +
                            "\tTeam format dropDatabase|DatabaseName, and you have entered: disconnect", e.getMessage());
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
                    "The number of parameters partitioned by the character '|' is incorrect, it is expected  2," +
                            " but is: 3"+ newLine +
                            "\tTeam format dropDatabase|DatabaseName, and you have entered: disconnect|table|qwe", e.getMessage());
        }
    }

    @Test
    public void descriptionTest() {
        // when
        view.write(command.description());
        // then
        verify(view).write("Delete database");
    }

    @Test
    public void formatTest() {
        // when
        view.write(command.format());
        // then
        verify(view).write("dropDatabase|DatabaseName");
    }

}
