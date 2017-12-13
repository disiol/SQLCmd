package ua.com.denisimusIT.SQLCmd.controller.testCommands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class currentDatabaseTest {

    private PostgresDatabaseManager postgresDatabaseManager;
    private String dataBaseName;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final String newline = System.lineSeparator();


    @Before
    public void connectToDataBase() {
        String dataBase = "postgres";
        String user = "postgres";
        String password = "1111";


        postgresDatabaseManager = new PostgresDatabaseManager();
        postgresDatabaseManager.connectToDatabase(dataBase, user, password);
    }

    @Before
    public void createDatabase() {
        dataBaseName = "testdatabase";

        List<String> dataBaseNames = postgresDatabaseManager.getDatabaseNames();
        dataBaseNames.add(this.dataBaseName);
        Collections.sort(dataBaseNames);

        Object[] expected = dataBaseNames.toArray();


        postgresDatabaseManager.createDatabase(dataBaseName);

        connectToDataBase();
        List<String> actualDatabaseNames = postgresDatabaseManager.getDatabaseNames();
        Collections.sort(actualDatabaseNames);
        Object[] actualDatabaseNamesSorted = actualDatabaseNames.toArray();
        assertEquals("getDatabaseNames", Arrays.toString(expected), Arrays.toString(actualDatabaseNamesSorted));
    }

    //TODO selectDatabase


    @Test
    public void currentDatabaseTest() {
        connectToDataBase();
        System.setOut(new PrintStream(outContent));

        postgresDatabaseManager.currentDatabase();

        String expected = "current_database " + newline +
                          "------------------" + newline +
                          " postgres" + newline;
        String actual = outContent.toString();
        assertEquals("currentDatabase", expected, actual);
    }

    @After
    public void dropDatabase() {

        connectToDataBase();
        postgresDatabaseManager.dropDatabase(dataBaseName);

    }
}
