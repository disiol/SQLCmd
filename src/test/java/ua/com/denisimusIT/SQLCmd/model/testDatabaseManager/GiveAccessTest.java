package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.*;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class GiveAccessTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final String newline = System.lineSeparator();
    private static final DatabaseManager POSTGRES_DATABASE_MANAGER = new PostgresDatabaseManager();
    private final static String DATA_BASE = "postgres";
    private final static String USER = "postgres";
    private final static String PASSWORD = "1111";
    private final static String TEST_DATABASE_NAME = "testdatabase";
    private final static String TEST_USER = "den";


    @BeforeClass
    public static void setUpBeforClass() throws Exception {
        conectToDb();
        POSTGRES_DATABASE_MANAGER.createDatabase(TEST_DATABASE_NAME);
        conectToDb();
        POSTGRES_DATABASE_MANAGER.createUser(TEST_USER,PASSWORD);
       conectToTestDb();


    }

    private static void conectToDb() {
        POSTGRES_DATABASE_MANAGER.connectToDatabase(DATA_BASE, USER, PASSWORD);
    }

    private static void conectToTestDb() {
        POSTGRES_DATABASE_MANAGER.connectToDatabase(TEST_DATABASE_NAME, TEST_USER, PASSWORD);
    }


    @Test

    public void GiveAccessTest() {
        System.setOut(new PrintStream(outContent));
        POSTGRES_DATABASE_MANAGER.giveAccessUserToTheDatabase(TEST_DATABASE_NAME, TEST_USER);

        String expected = "Access user: " + TEST_USER + " to the database: "+ TEST_DATABASE_NAME +" it is allow";
        String actual = outContent.toString();
        assertEquals("GiveAccess", expected, actual);

    }

    @AfterClass
    public static void dropDatabase() throws Exception {
        conectToDb();
        POSTGRES_DATABASE_MANAGER.dropDatabase(TEST_DATABASE_NAME);
        conectToDb();
        POSTGRES_DATABASE_MANAGER.dropUser(TEST_USER);

    }

}
