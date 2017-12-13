package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class DropDatabaseTest {

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
    public void dropDatabaseTest() {
        System.setOut(new PrintStream(outContent));

        connectToDataBase();
        postgresDatabaseManager.dropDatabase(dataBaseName);

        connectToDataBase();

        List<String> dataBaseNames = postgresDatabaseManager.getDatabaseNames();
        dataBaseNames.remove(this.dataBaseName);
        Collections.sort(dataBaseNames);

        String expected = dataBaseNames.toString();
        List<String> actualDatabaseNames = postgresDatabaseManager.getDatabaseNames();
        Collections.sort(actualDatabaseNames);
        Object[] actualDatabaseNamesSorted = actualDatabaseNames.toArray();
        assertEquals("dropDatabaseNames", expected, Arrays.toString(actualDatabaseNamesSorted));

        String actualMessage = outContent.toString();
        Object expectedMessage = String.format("Database: %s drop successfully",dataBaseName) + newline;
        
        assertEquals("Database drop successfully" + newline, expectedMessage, actualMessage);

    }


}
