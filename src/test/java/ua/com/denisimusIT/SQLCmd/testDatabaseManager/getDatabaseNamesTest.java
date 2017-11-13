package ua.com.denisimusIT.SQLCmd.testDatabaseManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class getDatabaseNamesTest {
    final String newline = System.lineSeparator();
    PostgresDatabaseManager postgresDatabaseManager = new PostgresDatabaseManager();

    @Before
    public void connectToDataBase() {
        String dataBase = "postgres";
        String user = "postgres";
        String password = "1111";
        postgresDatabaseManager.connect(dataBase, user, password);
        //TODO crate data bese

    }


    @Test
    public void ShowDatabaseTest() {

        String expected = "[template1, template0, postgres, sqlCmd]";
        Object[] actual = postgresDatabaseManager.getDatabaseNames().toArray();

        assertEquals("getDatabaseNames", expected, Arrays.toString(actual));
    }

    @After

    public void drop(){
        //TODO drop data bese
    }
}
