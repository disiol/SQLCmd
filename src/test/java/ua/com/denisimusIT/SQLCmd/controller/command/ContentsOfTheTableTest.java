package ua.com.denisimusIT.SQLCmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.denisimusIT.SQLCmd.model.DataSetImpl;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class ContentsOfTheTableTest {
    private String newLine = System.lineSeparator();

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new ContentsOfTheTable(view, manager);
    }


    @Test
    public void testPrintTableData() {
        // given
        String tableName = "user";
        when(manager.getTableColumns(tableName))
                .thenReturn(Arrays.asList(new String[]{"id", "name", "password"}));

        DataSetImpl user1 = new DataSetImpl();
        user1.put("id", 12);
        user1.put("name", "Stiven");
        user1.put("password", "*****");

        DataSetImpl user2 = new DataSetImpl();
        user2.put("id", 13);
        user2.put("name", "Eva");
        user2.put("password", "+++++");

        DataSetImpl[] data = new DataSetImpl[]{user1, user2};
        when(manager.getTableData("\"" + tableName + "\""))
                .thenReturn(Arrays.asList(data));

        // when
        command.process("find|" + tableName);

        // then
        shouldPrint("[•+--------------------------------------------------," +
                " •+ id + name + password + , " +
                "•+--------------------------------------------------, " +
                "•+ 12 + Stiven + ***** + , " +
                "•+--------------------------------------------------," +
                " •+ 13 + Eva + +++++ + , " +
                "•+--------------------------------------------------]", "testPrintTableData");
    }

    private void shouldPrint(String expected, String message) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(message, expected, captor.getAllValues().toString());
    }

    @Test
    public void testCanProcessFindWithParametersString() {
        // when
        boolean canProcess = command.canProcess("find|user");

        // then
        assertTrue(canProcess);
    }

    @Test
    public void testCantProcessFindWithoutParametersString() {
        // when
        boolean canProcess = command.canProcess("find");

        // then
        assertFalse(canProcess);
    }

    @Test
    public void testCantProcessQweString() {
        // when
        boolean canProcess = command.canProcess("qwe|user");

        // then
        assertFalse(canProcess);
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
            command.process("find");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("testValidationErrorWhenCountParametersIsLessThan2",
                    "Team format find|tableName, and you have entered: 2" + newLine +
                            "Team format find|tableName, and you have entered: find", e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersIsMoreThan2() {
        // when
        try {
            command.process("find|table|qwe");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("testValidationErrorWhenCountParametersIsMoreThan2",
                    "Team format find|tableName, and you have entered: 2" + newLine +
                             "Team format find|tableName, and you have entered: find|table|qwe", e.getMessage());
        }
    }

    @Test
    public void testPrintEmptyTableData() {
        // given
        String tableName = "user";
        when(manager.getTableColumns(tableName))
                .thenReturn(Arrays.asList(new String[]{"id", "name", "password"}));

        when(manager.getTableData("\"" + tableName + "\"")).thenReturn(Arrays.asList(new DataSetImpl[0]));

        // when
        command.process("find|" + tableName);

        // then
        shouldPrint("[•+--------------------------------------------------," +
                        " •+ id + name + password + ," +
                        " •+--------------------------------------------------]",
                "testPrintEmptyTableData");
    }

    @Test
    public void testPrintTableDataWithOneColumn() {
        // given
        String test = "test";
        when(manager.getTableColumns(test))
                .thenReturn(Arrays.asList(new String[]{"id"}));

        DataSetImpl user1 = new DataSetImpl();
        user1.put("id", 12);

        DataSetImpl user2 = new DataSetImpl();
        user2.put("id", 13);

        DataSetImpl[] data = new DataSetImpl[]{user1, user2};
        when(manager.getTableData("\"" + test + "\"")).thenReturn(Arrays.asList(data));

        // when
        command.process("find|" + test);

        // then
        shouldPrint("[•+--------------------------------------------------," +
                " •+ id + , " +
                "•+--------------------------------------------------," +
                " •+ 12 + , " +
                "•+--------------------------------------------------," +
                " •+ 13 + , " +
                "•+--------------------------------------------------]", "testPrintTableDataWithOneColumn");
    }

    @Test
    public void descriptionTest() {
        view.write(command.description());
        verify(view).write("for receiving contents of the table tableName");
    }

    @Test
    public void format() {
        view.write(command.format());
        verify(view).write("find|tableName");
    }


}

