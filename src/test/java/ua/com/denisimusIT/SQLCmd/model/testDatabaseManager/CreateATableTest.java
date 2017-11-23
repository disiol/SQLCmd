package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.*;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 07.10.17.
 * mail: deoniisii@gmail.com
 */
public class CreateATableTest {
    private final static ByteArrayOutputStream OUT_CONTENT = new ByteArrayOutputStream();
    private final static String NEW_LINE = System.lineSeparator();

    private static final DatabaseManager POSTGRES_DATABASE_MANAGER = new PostgresDatabaseManager();
    private final static String DATA_BASE = "postgres";
    private final static String USER = "postgres";
    private final static String PASSWORD = "1111";
    private final static String TEST_DATABASE_NAME = "testdatabase";


    @BeforeClass
    public static void setUpBeforClass() throws Exception {
        POSTGRES_DATABASE_MANAGER.connect(DATA_BASE, USER, PASSWORD);
        POSTGRES_DATABASE_MANAGER.createDatabase(TEST_DATABASE_NAME);
        POSTGRES_DATABASE_MANAGER.connect(TEST_DATABASE_NAME, USER, PASSWORD);

    }


    @Test
    public void сreateTableCompany() {
        //TODO тест создания таблицы

        System.setOut(new PrintStream(OUT_CONTENT));

        // создает таблицу и проверает создана ли она
        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL,PASSWORD  TEXT  NOT NULL";


        String tableName = "company";
        POSTGRES_DATABASE_MANAGER.createATable(tableName, columnsValues);

        String expected = "Table " + tableName + " created successfully" + NEW_LINE;
        String actual = OUT_CONTENT.toString();
        assertEquals("сreateTableCompany", expected, actual);


        String expected_1 = "[id, name, password]";
        String actual_1 = POSTGRES_DATABASE_MANAGER.getTableColumns(tableName).toString();
        assertEquals("getTableColumns", expected_1, actual_1);


    }


    @AfterClass
    public static void dropDatabase() throws Exception {
        POSTGRES_DATABASE_MANAGER.disconnectOfDatabase(TEST_DATABASE_NAME);
        POSTGRES_DATABASE_MANAGER.connect(DATA_BASE, USER, PASSWORD);
        POSTGRES_DATABASE_MANAGER.dropDatabase(TEST_DATABASE_NAME);

    }
}
