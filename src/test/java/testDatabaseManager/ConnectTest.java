package testDatabaseManager;

import controller.DatabaseManager;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Denis Oleynyk on 25.09.17.
 * mail: deoniisii@gmail.com
 */
public class ConnectTest {


    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    DatabaseManager databaseManager = new DatabaseManager();
    final String newline = System.lineSeparator();


    @Test
    public void ConnectTest() {
        System.setOut(new PrintStream(outContent));

        String database = "sqlCmd";
        String user = "postgres";
        String password = "1111";
        databaseManager.connect(database,
                user, password);
        String expected = "Opened database successfully" + newline;
        String actual = outContent.toString();

        assertEquals("connect to data base", expected, actual);

    }

    @Test
    public void theDatabaseManagerDoesNotExist() throws SQLException {
        System.setOut(new PrintStream(outContent));

        String database = " q";
        String user = "postgres";
        String password = "1111";
        String message = String.format("Cant get connection for database:%s user:%s", database, user);
        databaseManager.connect(database,
                user, password);
        String expected = message + " ,database does not exist" + newline;
        String actual = outContent.toString();
        assertEquals("The databaseManager does not exist", expected, actual);

    }

    @Test
    public void notTheCorrectPassword() throws Exception {
        System.setOut(new PrintStream(outContent));

        String database = "sqlCmd";
        String user = "postgres";
        String password = "";
        databaseManager.connect(database,
                user, password);

        String expected;
        String actual;
        expected = String.format("Cant get connection for database:%s user:%s", database, user) +
                ", not the correct password or user name" + newline;
        actual = outContent.toString();
        assertEquals("not the correct password", expected, actual.toString());

    }

    @Test
    public void theUserDoesNotExist() throws SQLException {
        System.setOut(new PrintStream(outContent));

        String database = "sqlCmd";
        String user = "q";
        String password = "1111";
        databaseManager.connect(database, user, password);
        String expected;
        String actual = outContent.toString();
        expected = String.format("Cant get connection for database:%s user:%s", database, user) +
                ", not the correct password or user name" + newline;
        assertEquals("the user does not exist", expected, actual.toString());
    }


}
