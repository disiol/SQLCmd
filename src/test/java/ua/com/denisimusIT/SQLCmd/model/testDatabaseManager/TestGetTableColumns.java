package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.*;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 06.10.17.
 * mail: deoniisii@gmail.com
 */
public class TestGetTableColumns {
    private static final DatabaseManager POSTGRES_DATABASE_MANAGER = new PostgresDatabaseManager();
    private final static String DATA_BASE = "postgres";
    private final static String USER = "postgres";
    private final static String PASSWORD = "1111";
    private final static String TEST_DATABASE_NAME = "testdatabase";
    private final static String TEST_TABLE_NAME = "testtable";

    @BeforeClass
    public static void setUpBeforClass() {
        connectTodB();
        POSTGRES_DATABASE_MANAGER.connectToDatabase(DATA_BASE, USER, PASSWORD);
        POSTGRES_DATABASE_MANAGER.createDatabase(TEST_DATABASE_NAME);
        POSTGRES_DATABASE_MANAGER.connectToDatabase(TEST_DATABASE_NAME, USER, PASSWORD);

        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL,PASSWORD  TEXT  NOT NULL";
        POSTGRES_DATABASE_MANAGER.createATable(TEST_TABLE_NAME, columnsValues);

    }

    private static void connectTodB(){
        POSTGRES_DATABASE_MANAGER.connectToDatabase(DATA_BASE, USER, PASSWORD);
    }
    @Test
    public void TestGetTableColumns() {

        String actual = POSTGRES_DATABASE_MANAGER.getTableColumns(TEST_TABLE_NAME).toString();
        assertEquals("[id, name, password]", actual);
    }




    @AfterClass
    public static void deleteTable() {
        POSTGRES_DATABASE_MANAGER.disconnectOfDatabase(TEST_DATABASE_NAME);
        connectTodB();
        POSTGRES_DATABASE_MANAGER.dropDatabase(TEST_DATABASE_NAME);
    }
}
