package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 07.10.17.
 * mail: deoniisii@gmail.com
 */
public class DisconnectOfDatabaseTest {
    private final static ByteArrayOutputStream OUT_CONTENT = new ByteArrayOutputStream();
    private final static String NEW_LINE = System.lineSeparator();

    private static final DatabaseManager POSTGRES_DATABASE_MANAGER = new PostgresDatabaseManager();
    private final static String DATA_BASE = "postgres";
    private final static String USER = "postgres";
    private final static String PASSWORD = "1111";
    private final static String TEST_DATABASE_NAME = "testdatabase";


    @BeforeClass
    public static void setUpBeforClass() throws Exception {
        POSTGRES_DATABASE_MANAGER.connectToDatabase(DATA_BASE, USER, PASSWORD);
        POSTGRES_DATABASE_MANAGER.createDatabase(TEST_DATABASE_NAME);
        POSTGRES_DATABASE_MANAGER.connectToDatabase(TEST_DATABASE_NAME, USER, PASSWORD);

    }



    @Test
    public void disconnectOfDatabaseTest() {
        //TODO

        System.setOut(new PrintStream(OUT_CONTENT));

        POSTGRES_DATABASE_MANAGER.disconnectOfDatabase(TEST_DATABASE_NAME);

        String expected = "Disconnect of database: " + TEST_DATABASE_NAME + " successfully" + NEW_LINE;
        String actual = OUT_CONTENT.toString();
        assertEquals("disconnect of database", expected, actual);




    }


    @AfterClass
    public static void dropDatabase() throws Exception {
        POSTGRES_DATABASE_MANAGER.connectToDatabase(DATA_BASE, USER, PASSWORD);
        POSTGRES_DATABASE_MANAGER.dropDatabase(TEST_DATABASE_NAME);

    }
}
