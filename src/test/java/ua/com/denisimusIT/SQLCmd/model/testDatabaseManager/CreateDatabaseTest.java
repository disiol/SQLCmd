package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.*;

import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class CreateDatabaseTest {
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


    @Test

    public void createDatabaseTest() {
        System.setOut(new PrintStream(outContent));

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


        Object expectedMessage = "Creating database testdatabase"+newline +
                "Database created testdatabase successfully"+newline;
        String actual = outContent.toString();
        assertEquals("Database created successfully...", expectedMessage, actual);


    }

    @After
    public void dropDatabase() {
        connectToDataBase();
        postgresDatabaseManager.dropDatabase(this.dataBaseName);
    }
}
