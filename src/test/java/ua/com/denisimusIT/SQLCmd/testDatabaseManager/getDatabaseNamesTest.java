package ua.com.denisimusIT.SQLCmd.testDatabaseManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import static junit.framework.TestCase.assertEquals;

public class getDatabaseNamesTest {
    final String newline = System.lineSeparator();
    PostgresDatabaseManager postgresDatabaseManager = new PostgresDatabaseManager();

    @Before
    public void connectToDataBase() {
        String dataBase = "sqlCmd";
        String user = "postgres";
        String password = "1111";
        postgresDatabaseManager.connect(dataBase, user, password);

    }


    @Test
    public void ShowDatabaseTest() {
        postgresDatabaseManager.getDatabaseNames();

        String expected = " datname  "+newline +
                "-----------"+newline +
                " template1"+newline +
                " template0"+newline +
                " postgres"+newline +
                " sqlCmd"+newline;
        String[] actual = postgresDatabaseManager.getDatabaseNames();

        assertEquals("getDatabaseNames", expected, actual);
    }

}
