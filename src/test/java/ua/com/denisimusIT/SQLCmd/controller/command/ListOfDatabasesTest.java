package ua.com.denisimusIT.SQLCmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

/**
 * Created by indigo on 01.09.2015.
 */
public class ListOfDatabasesTest {

    private DatabaseManager manager;
    private View view;
    private Command command;
    private String newLine = System.lineSeparator();


    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new ListOfDatabasesNames(view, manager);
    }

    @Test
    public void ListOfDatabasesTest() {
        //given
        List<String> listDatabases = new LinkedList<>(Arrays.asList("Databases", "Databases2"));
        when(manager.getDatabaseNames()).thenReturn(listDatabases);

        // when
        command.process("ListDatabase");

        // then
        verify(view).write(String.format("List of databases : %s", listDatabases.toString()));
    }

    @Test
    public void emptyListOfDatabasesTest() {

        // when
        command.process("ListDatabase");

        // then
        verify(view).write(String.format("List of databases : %s", "[]"));
    }


    @Test
    public void testCanProcessClearWithParametersString() {
        // when
        boolean canProcess = command.canProcess("databases");

        // then
        assertTrue("testCanProcessClearWithParametersString", canProcess);
    }

    @Test
    public void testCantProcessClearWithoutParametersString() {
        // when
        boolean canProcess = command.canProcess(" ");

        // then
        assertFalse("testCantProcessClearWithoutParametersString", canProcess);
    }


    @Test
    public void descriptionTest() {
        // when
        view.write(command.description());
        // then
        verify(view).write("To display the list of databases");
    }

    @Test
    public void formatTest() {
        // when
        view.write(command.format());
        // then
        verify(view).write("databases");
    }

}
