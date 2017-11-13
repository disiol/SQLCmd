package ua.com.denisimusIT.SQLCmd.testDatabaseManager;

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


public class dropDatabaseTest {

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
        postgresDatabaseManager.connect(dataBase, user, password);
    }

    @Before
    public void createDatabase() {
        dataBaseName = "testdatabase";
        postgresDatabaseManager.createDatabase(dataBaseName);
        String expected = "[postgres, template0, template1, " + this.dataBaseName + "]";

        connectToDataBase();
        List<String> actualDatabaseNames = postgresDatabaseManager.getDatabaseNames();
        Collections.sort(actualDatabaseNames);
        Object[] actualDatabaseNamesSorted = actualDatabaseNames.toArray();
        assertEquals("getDatabaseNames", expected, Arrays.toString(actualDatabaseNamesSorted));
    }


    @Test
    public void dropDatabaseTest() {
        System.setOut(new PrintStream(outContent));

        connectToDataBase();
        postgresDatabaseManager.dropDatabase(dataBaseName);

        connectToDataBase();
        String expected = "[postgres, template0, template1]";
        List<String> actualDatabaseNames = postgresDatabaseManager.getDatabaseNames();
        Collections.sort(actualDatabaseNames);
        Object[] actualDatabaseNamesSorted = actualDatabaseNames.toArray();
        assertEquals("dropDatabaseNames", expected, Arrays.toString(actualDatabaseNamesSorted));

        String actualMessage = outContent.toString();
        Object expectedMessage = "Opened database successfully" + newline +
                "Creating database..." + newline +
                "Database drop successfully..." + newline +
                "Opened database successfully" + newline;
        assertEquals("Database drop successfully..." + newline, expectedMessage, actualMessage);

    }


}
