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
    public void ConnectTest() throws SQLException {
        System.setOut(new PrintStream(outContent));

        databaseManager.connect("sqlCmd",
                "postgres", "1111");
        String expected = "Opened database successfully" + newline;
        String actual = outContent.toString();

        assertEquals("connect to data base", expected, actual);

    }

    @Test
    public void theDatabaseManagerDoesNotExist() throws SQLException {
        databaseManager.connect(" ",
                "postgres", "1111");
        System.setOut(new PrintStream(outContent));

        String expected = "База данных не сушествует";
        String actual = outContent.toString();
        assertEquals("The databaseManager does not exist", expected, actual);

    }

    @Test
    public void notTheCorrectPassword() throws Exception {
        databaseManager.connect("sqlCmd",
                "postgres", "");

        String expected;
        Connection actual;
        expected = "пароль не верный ";
        actual = databaseManager.getConnection();
        assertEquals("not the correct password", expected, actual.toString());
        actual.close();
    }

    @Test
    public void theUserDoesNotExist() throws SQLException {
        databaseManager.connect("sqlCmd", "", "1111");
        String expected;
        Connection actual;
        expected = "Пользователя не сушествует";
        actual = databaseManager.getConnection();
        assertEquals("the user does not exist", expected, actual.toString());
        actual.close();
    }


}
