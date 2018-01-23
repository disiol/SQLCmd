package ua.com.denisimusIT.SQLCmd.controller.command;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;
import org.junit.*;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DropTableTest {
    private String newLine = System.lineSeparator();

    private View view;
    private DatabaseManager databaseManager;
    private Command command;

    @Before
    public void setup() {
        view = mock(View.class);
        databaseManager = mock(DatabaseManager.class);
        command = new DropTable(view, databaseManager);

    }

    @Test
    public void canProcessTrue() {
        boolean canProcess = command.canProcess("dropTable|table");
        assertTrue("canProcessTrue", canProcess);
    }

    @Test
    public void canProcessFalse() {
        boolean canProcess = command.canProcess("dropTable");
        assertFalse("canProcessFalse", canProcess);
    }

    @Test
    public void canProcessFalseNoParameters() {
        boolean canProcess = command.canProcess("");
        assertFalse("canProcessFalse", canProcess);
    }

    @Test
    public void canProcessFalseAbs() {
        boolean canProcess = command.canProcess("abs|table");
        assertFalse("canProcessFalse", canProcess);
    }

    @Test
    public void dropTableTest() {
        String tableName = "table";
        command.process("dropTable|" + tableName);
        verify(databaseManager).dropTable("\"" + tableName + "\"");
        verify(view).write("Table  " + "\"" + tableName + "\"" + " deleted successfully");

    }


    @Test
    public void testValidationErrorWhenCountParametersIsLessThan2() {
        // when
        try {
            command.process("clear");
            fail("testValidationErrorWhenCountParametersIsLessThan2");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("testValidationErrorWhenCountParametersIsLessThan2",
                    "The number of parameters partitioned by the character '|' is incorrect, it is expected  2, " +
                            "but is: 1" + newLine +
                            "\tTeam format dropTable|tableName, and you have entered: clear", e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersIsMoreThan2() {
        // when
        try {
            command.process("dropTable|table|table|qwe");
            fail("testValidationErrorWhenCountParametersIsMoreThan2");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("testValidationErrorWhenCountParametersIsMoreThan2",
                    "The number of parameters partitioned by the character '|' is incorrect, it is expected  2, " +
                            "but is: 4" + newLine +
                            "\tTeam format dropTable|tableName, and you have entered: dropTable|table|table|qwe", e.getMessage());
        }
    }


    @Test
    public void description() {
        String description = command.description();
        assertEquals("description", "Delete table", description);
    }

    @Test
    public void format() {
        String format = command.format();
        assertEquals("format", "dropTable|tableName", format);
    }
}
