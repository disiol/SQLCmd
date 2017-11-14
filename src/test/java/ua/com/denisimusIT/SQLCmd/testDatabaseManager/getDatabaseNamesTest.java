package ua.com.denisimusIT.SQLCmd.testDatabaseManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class getDatabaseNamesTest {
    final String newline = System.lineSeparator();
    PostgresDatabaseManager postgresDatabaseManager = new PostgresDatabaseManager();
    private String dataBaseName;

    @Before
    public void connectToDataBase() {
        String dataBase = "postgres";
        String user = "postgres";
        String password = "1111";
        postgresDatabaseManager.connect(dataBase, user, password);

    }

    @Before
    public void createDatabase() {
        dataBaseName = "testdatabase";
        postgresDatabaseManager.createDatabase(dataBaseName);

    }


    @Test
    public void ShowDatabaseTest() {

        connectToDataBase();
        List<String> databaseNames = postgresDatabaseManager.getDatabaseNames();
        Collections.sort(databaseNames);


        String expected = databaseNames.toString();
        List<String> actualDatabaseNames = postgresDatabaseManager.getDatabaseNames();
        Collections.sort(actualDatabaseNames);
        Object[] actualDatabaseNamesSorted = actualDatabaseNames.toArray();
        assertEquals("getDatabaseNames", expected, Arrays.toString(actualDatabaseNamesSorted));
    }

    @After

    public void drop(){
        postgresDatabaseManager.dropDatabase(dataBaseName);
    }
}
