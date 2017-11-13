package ua.com.denisimusIT.SQLCmd.testDatabaseManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;


public class dropDatabaseTest {

    private PostgresDatabaseManager postgresDatabaseManager;
    private String dataBaseName;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


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
        String expected = "[template1, template0, postgres, sqlCmd, " + dataBaseName + "]";
        connectToDataBase();
        Object[] actualDatabaseNames = postgresDatabaseManager.getDatabaseNames().toArray();
        assertEquals("getDatabaseNames", expected, Arrays.toString(actualDatabaseNames));
    }


    @Test
    public void dropDatabaseTest() {
        System.setOut(new PrintStream(outContent));


        postgresDatabaseManager.dropDatabase(dataBaseName);

        connectToDataBase();
        String expected = "[template1, template0, postgres, sqlCmd]";
        Object[] actualDatabaseNames = postgresDatabaseManager.getDatabaseNames().toArray();
        assertEquals("dropDatabaseNames", expected, Arrays.toString(actualDatabaseNames));

        String actualMessage = outContent.toString();
        Object expectedMessage = "Creating database...\n" +
                                 "Database drop successfully...\n" +
                                 "Opened database successfully\n";
        assertEquals("Database drop successfully...\n", expectedMessage, actualMessage);

    }


}
