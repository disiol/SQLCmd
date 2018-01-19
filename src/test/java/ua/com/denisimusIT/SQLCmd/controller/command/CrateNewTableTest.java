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
public class CrateNewTableTest {
    private String newLine = System.lineSeparator();


    private DatabaseManager manager;
    private View view;
    private Command command;


    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new CreateNewTable(view, manager);
    }

    @Test
    public void testClearTable() {
        // given
        String columnsValues = "id int  NOT NULL, name TEXT NOT NULL, PASSWORD  TEXT  NOT NULL";
        String tableName = "user";
        // when
        command.process("create|" + tableName + "|" + columnsValues);
        // then
        verify(view).write(String.format("The table: %s is created successfully", tableName));
    }


    @Test
    public void testCanProcessClearWithParametersString() {
        // given
        String columnsValues = "id int  NOT NULL, name TEXT NOT NULL, PASSWORD  TEXT  NOT NULL";
        String tableName = "user";
        // when
        boolean canProcess = command.canProcess("create|" + tableName + "|" + columnsValues);

        // then
        assertTrue("testCanProcessClearWithParametersString", canProcess);
    }

    @Test
    public void testCantProcessClearWithoutParametersString() {
        // when
        boolean canProcess = command.canProcess("create");

        // then
        assertFalse("testCantProcessClearWithoutParametersString", canProcess);
    }

    @Test
    public void testValidationErrorWhenCountParametersIsLessThan2() {
        // when
        try {
            command.process("create|");
            fail();
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("testValidationErrorWhenCountParametersIsLessThan2",
                    "The number of parameters partitioned by the character '|' is incorrect, " +
                            "it is expected  3, but is: 1" + newLine +
                            "\texample: create|tableName|column1 column type, column2 column type,...,columnN column typ",
                    e.getMessage());
        }
    }


    @Test
    public void descriptionTest() {
        // when
        view.write(command.description());
        // then
        verify(view).write("For create table:");
    }

    @Test
    public void formatTest() {
        // when

        view.write(command.format());

        verify(view).write("create|tableName|column1 column type, column2 column type,...,columnN column type"
        );
    }

}
