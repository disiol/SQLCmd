package ua.com.denisimusIT.SQLCmd.controller.command;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;


import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Created by indigo on 01.09.2015.
 */
public class ClearTableTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new ClearTable(view, manager);
    }

    @Test
    public void testClearTable() {
        // given

        // when
        String tableName = "user";
        command.process("clear|" + tableName);

        // then
        verify(manager).clearATable("\"" + tableName + "\"");
        verify(view).write(String.format("The table: \"%s\"  is cleared successfully", tableName));
    }


    @Test
    public void testCanProcessClearWithParametersString() {
        // when
        boolean canProcess = command.canProcess("clear|user");

        // then
        assertTrue("testCanProcessClearWithParametersString", canProcess);
    }

    @Test
    public void testCantProcessClearWithoutParametersString() {
        // when
        boolean canProcess = command.canProcess("clear");

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
                    "Team format clear|tableName, and you have entered: clear", e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersIsMoreThan2() {
        // when
        try {
            command.process("clear|table|qwe");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("testValidationErrorWhenCountParametersIsMoreThan2",
                    "Team format clear|tableName, and you have entered: clear|table|qwe", e.getMessage());
        }
    }

    @Test
    public void descriptionTest() {
        // when
        view.write(command.description());
        // then
        verify(view).write("for cleaning of all table");
    }

    @Test
    public void formatTest() {
        // when

        view.write(command.format());

        verify(view).write("clear|tableName");
    }

}
