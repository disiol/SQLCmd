package ua.com.denisimusIT.SQLCmd.testDatabaseManager;

import org.junit.*;

import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class createDatabaseTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PostgresDatabaseManager postgresDatabaseManager;
    private String dataBaseName;
    private Object[] actualDatabaseNamesSorted;

    @Before
    public void connectToDataBase() {
        String dataBase = "postgres";
        String user = "postgres";
        String password = "1111";

        postgresDatabaseManager = new PostgresDatabaseManager();
        postgresDatabaseManager.connect(dataBase, user, password);
    }

    @Before
    public void DatabaseNames() {
        String expected = "[postgres, template0, template1]";
        List<String> actualDatabaseNames =postgresDatabaseManager.getDatabaseNames();

        Collections.sort(actualDatabaseNames);


        actualDatabaseNamesSorted = actualDatabaseNames.toArray();


        assertEquals("getDatabaseNames", expected, Arrays.toString(this.actualDatabaseNamesSorted));
    }

    @Test

    public void createDatabaseTest() {
        System.setOut(new PrintStream(outContent));

        dataBaseName = "testDatabase";
        postgresDatabaseManager.createDatabase(dataBaseName);

        Object expectedDatabaseNames = this.actualDatabaseNamesSorted;
        assertEquals("createDatabaseTest", expectedDatabaseNames, this.actualDatabaseNamesSorted);

        Object expected = "Creating database...\n" +
                          "Database created successfully...\n";
        String actual = outContent.toString();
        assertEquals("Database created successfully...", expected, actual);


    }

    @After
    public void dropDatabase() {
        connectToDataBase();
        postgresDatabaseManager.dropDatabase(this.dataBaseName);
    }
}
