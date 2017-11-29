package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.After;
import org.junit.Before;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Denis Oleynyk on 25.09.17.
 * mail: deoniisii@gmail.com
 */
public class ConnectTest {


    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    DatabaseManager postgresDatabaseManager = new PostgresDatabaseManager();
    final String newline = System.lineSeparator();

    @Before
    public void createDatabase() {
        //TODO
    }

    @Test
    public void ConnectTest() {
        System.setOut(new PrintStream(outContent));

        String database = "postgres";
        String user = "postgres";
        String password = "1111";
        postgresDatabaseManager.connect(database,
                user, password);
        String expected = "Opened database: " + database + " successfully" + newline;
        Boolean actual = postgresDatabaseManager.isConnected();

        assertTrue("connect to data base", actual);

    }

    @org.junit.Test(expected = RuntimeException.class)
    public void notTheCorrectPassword() throws Exception {
        System.setOut(new PrintStream(outContent));

        String database = "sqlCmd";
        String user = "postgres";
        String password = "";
        postgresDatabaseManager.connect(database,
                user, password);
        Boolean actual = postgresDatabaseManager.isConnected();

        assertFalse("connect to data base", actual);

        String expected;
        String actual2;
        expected = String.format("Cant get connection for database:%s user:%s", database, user) +
                ", not the correct password or user name" + newline;
        actual2 = outContent.toString();
        assertEquals("not the correct password", expected, actual2.toString());

    }


    @After
    public void dropDatabase() {

    }


}
