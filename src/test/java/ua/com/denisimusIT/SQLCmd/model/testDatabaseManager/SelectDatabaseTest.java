package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

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


public class SelectDatabaseTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PostgresDatabaseManager postgresDatabaseManager;
    private String dataBaseName;
    final String newline = System.lineSeparator();

    @Before
    public void connectToDataBase() {
        String dataBase = "postgres";
        String user = "postgres";
        String password = "1111";

        postgresDatabaseManager = new PostgresDatabaseManager();
        postgresDatabaseManager.connect(dataBase, user, password);
    }


    @Before

    public void createDatabase() {
        //before
        dataBaseName = "testdatabase";
        List<String> dataBaseNames = postgresDatabaseManager.getDatabaseNames();
        dataBaseNames.add(this.dataBaseName);
        Collections.sort(dataBaseNames);
        Object[] expected = dataBaseNames.toArray();


        //then
        postgresDatabaseManager.createDatabase(dataBaseName);
        connectToDataBase();
        List<String> actualDatabaseNames = postgresDatabaseManager.getDatabaseNames();
        Collections.sort(actualDatabaseNames);
        Object[] actualDatabaseNamesSorted = actualDatabaseNames.toArray();
        assertEquals("getDatabaseNames", Arrays.toString(expected), Arrays.toString(actualDatabaseNamesSorted));
    }

    @Test
    public void selectDatabaseTest() {
        System.setOut(new PrintStream(outContent));
        postgresDatabaseManager.selectDatabase(dataBaseName);

        String actual = postgresDatabaseManager.currentDatabase().toString();
        String expected = "[" + dataBaseName + "]";
        assertEquals("selectDatabase", expected, actual);

        String expectedMessage = expected;
        String actualMessage = outContent.toString();
        assertEquals("selectDatabaseMassage", expectedMessage, actualMessage);


    }

    @After
    public void dropDatabase() {
        connectToDataBase();
        postgresDatabaseManager.dropDatabase(this.dataBaseName);
    }
}
