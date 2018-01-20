package ua.com.denisimusIT.SQLCmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.DataSet;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class InsertALineIntoTheTableTest {
    private String newLine = System.lineSeparator();
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new InsertALineIntoTheTable(view, manager);
    }

    @Test
    public void insertALineIntoTheTableMassageTest() {
        // when
        String tableName = "User";

        DataSet user1 = new DataSet();
        user1.put("id", 12);
        user1.put("name", "Stiven");
        user1.put("password", "*****");
        command.process("insert|" + tableName + "|id|12|name|Stiven|password|*****");
        // then
        verify(view).write(String.format("The record %s was successfully created in the table: %s'.", user1, tableName));

    }

    @Test
    public void insert_A_Line_Into_The_Table_Insert_Data_Test() {
        // when

        String tableName = "User";

        DataSet user1 = new DataSet();
        user1.put("id", 12);
        user1.put("name", "Stiven");
        user1.put("password", "*****");
        command.process("insert|" + tableName + "|id|12|name|Stiven|password|*****");
        // then
        verify(manager).insertData("\"" + tableName + "\"", user1);  //TODO

    }

    private void shouldPrint(String expected, String message) {
        ;
    }

    @Test
    public void testCanProcessClearWithParametersString() {
        // when
        boolean canProcess = command.canProcess("insert|user");

        // then
        assertTrue("testCanProcessClearWithParametersString", canProcess);
    }

    @Test
    public void testCanProcessClearWithParametersAdbs() {
        // when
        boolean canProcess = command.canProcess("adbs|user");

        // then
        assertFalse("testCanProcessClearWithParametersAdbs", canProcess);
    }

    @Test
    public void testCantProcessClearWithoutParametersString() {
        // when
        boolean canProcess = command.canProcess("");

        // then
        assertFalse("testCantProcessClearWithoutParametersString", canProcess);
    }

    @Test
    public void testValidationErrorWhenCountParametersIsLessThan2() {
        // when
        try {
            command.process("clear|");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("testValidationErrorWhenCountParametersIsLessThan2",
                    "Team format clear|tableName, and you have entered: clear|", e.getMessage());
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