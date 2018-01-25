package ua.com.denisimusIT.SQLCmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GifAccessToDatabaseTest {

    private String newLine = System.lineSeparator();

    private View view;
    private DatabaseManager manager;
    private Command command;

    @Before

    public void setup() {

        view = mock(View.class);
        manager = mock(DatabaseManager.class);
        command = new GifAccessToDatabase(view, manager);
    }

    @Test
    public void canProcessTrue() {
        boolean canProcess = command.canProcess("giveAccess|");
        assertTrue("canProcessTrue", canProcess);
    }

    @Test
    public void canProcessFalse() {
        boolean canProcess = command.canProcess("giveAccess");
        assertFalse("canProcessFalse", canProcess);
    }

    @Test
    public void canProcessAaaasf() {
        boolean canProcess = command.canProcess("aaaasf|");
        assertFalse("canProcessAaaasf", canProcess);
    }

    @Test
    public void processTest() {
        String databaseName = "databaseName";
        String userName = "userName";

        // when
        command.process("giveAccess" + "|" + databaseName + "|" + userName);

        //then
        verify(manager).giveAccessUserToTheDatabase("\"" + databaseName + "\"", userName);
        verify(view).write("Give access " + userName + " to database : " + databaseName.toString() + " is successfully");

    }

    @Test
    public void testValidationErrorWhenCountParametersIsLessThan3() {
        // when
        try {
            command.process("giveAccess|databaseName");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("testValidationErrorWhenCountParametersIsLessThan3",
                    "The number of parameters partitioned by the character '|' is incorrect, it is expected  " +
                            "3, but is: 2" + newLine +
                            "\texample: giveAccess|databaseName|userName", e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersIsMoreThan3() {
        // when
        try {
            command.process("giveAccess|databaseName|userName|hjhh");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("testValidationErrorWhenCountParametersIsLessThan3",
                    "The number of parameters partitioned by the character '|' is incorrect, it is expected  " +
                            "3, but is: 4" + newLine +
                            "\texample: giveAccess|databaseName|userName", e.getMessage());
        }
    }

    @Test
    public void descriptionTest() {
        // when
        view.write(command.description());
        //then
        verify(view).write("Give access user to database");
    }

    @Test
    public void formatTest() {
        // when
        view.write(command.format());
        //then
        verify(view).write("giveAccess|databaseName|userName");
    }
}
