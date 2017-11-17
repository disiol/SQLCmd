package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager.doBeforeTests;

import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class ConnectBeforeTests {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    DatabaseManager postgresDatabaseManager = new PostgresDatabaseManager();
    final String newline = System.lineSeparator();



    @Test
    public void ConnectTest() {
        System.setOut(new PrintStream(outContent));

        String database = "postgres";
        String user = "postgres";
        String password = "1111";
        postgresDatabaseManager.connect(database,
                user, password);
        String expected = "Opened database successfully" + newline;
        String actual = outContent.toString();

        assertEquals("connect to data base", expected, actual);

    }
}
