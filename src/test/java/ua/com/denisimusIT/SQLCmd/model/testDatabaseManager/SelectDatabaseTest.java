package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.*;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import static junit.framework.TestCase.assertEquals;


public class SelectDatabaseTest {
    private static final DatabaseManager POSTGRES_DATABASE_MANAGER = new PostgresDatabaseManager();
    private final static String DATA_BASE = "postgres";
    private final static String USER = "postgres";
    private final static String PASSWORD = "1111";
    private final static String TEST_DATABASE_NAME = "testdatabase";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @BeforeClass
    public static void setUpBeforClass() throws Exception {
        connectToDataBase();
        POSTGRES_DATABASE_MANAGER.createDatabase(TEST_DATABASE_NAME);
        POSTGRES_DATABASE_MANAGER.connect(TEST_DATABASE_NAME, USER, PASSWORD);

    }
    private static void connectToDataBase() {
        POSTGRES_DATABASE_MANAGER.connect(DATA_BASE, USER, PASSWORD);

    }

    @Ignore("SelectDatabase сделаю в конце")
    @Test
    public void selectDatabaseTest() throws Exception {
        System.setOut(new PrintStream(outContent));
        POSTGRES_DATABASE_MANAGER.selectDatabase(TEST_DATABASE_NAME);

        String actual = POSTGRES_DATABASE_MANAGER.currentDatabase().toString();
        String expected = "[" + TEST_DATABASE_NAME + "]";
        assertEquals("selectDatabase", expected, actual);

        String expectedMessage = TEST_DATABASE_NAME;
        String actualMessage = outContent.toString();
        assertEquals("selectDatabaseMassage", expectedMessage, actualMessage);


    }

    @AfterClass
    public static void dropDatabase() {
        POSTGRES_DATABASE_MANAGER.disconnectOfDatabase(TEST_DATABASE_NAME);
        connectToDataBase();
        POSTGRES_DATABASE_MANAGER.dropDatabase(TEST_DATABASE_NAME);
    }


}
