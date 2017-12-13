package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.*;
import ua.com.denisimusIT.SQLCmd.model.DataSet;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 06.10.17.
 * mail: deoniisii@gmail.com
 */
public class TestUpdateTableData {



    private static final DatabaseManager POSTGRES_DATABASE_MANAGER = new PostgresDatabaseManager();
    private final static String DATA_BASE = "postgres";
    private final static String USER = "postgres";
    private final static String PASSWORD = "1111";
    private final static String TEST_DATABASE_NAME = "testdatabase";
    private static String TEST_TABLE_NAME = "company";



    @BeforeClass
    public static void setUpBeforClass() throws Exception {
        connectToDb();
        POSTGRES_DATABASE_MANAGER.createDatabase(TEST_DATABASE_NAME);
        POSTGRES_DATABASE_MANAGER.connectToDatabase(TEST_DATABASE_NAME, USER, PASSWORD);


        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL,PASSWORD  TEXT  NOT NULL";
        POSTGRES_DATABASE_MANAGER.createATable(TEST_TABLE_NAME, columnsValues);

    }

    private static void connectToDb() {
        POSTGRES_DATABASE_MANAGER.connectToDatabase(DATA_BASE, USER, PASSWORD);
    }

    @Test
    public void ChangeOfTheTable() {



        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        POSTGRES_DATABASE_MANAGER.insertData(TEST_TABLE_NAME,input);

        // when
        DataSet newValue = new DataSet();
        newValue.put("password", "pass2");
        newValue.put("name", "Pup");
        POSTGRES_DATABASE_MANAGER.updateTableData(TEST_TABLE_NAME, 13, newValue);

        // then
        DataSet[] users = POSTGRES_DATABASE_MANAGER.getTableData(TEST_TABLE_NAME);
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[id, name, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, Pup, pass2]", Arrays.toString(user.getValues()));

    }


    @AfterClass
    public static void deleteTable() {
        // drop  таблицу с данами
        POSTGRES_DATABASE_MANAGER.disconnectOfDatabase(TEST_DATABASE_NAME);
        connectToDb();
        POSTGRES_DATABASE_MANAGER.dropDatabase(TEST_DATABASE_NAME);
    }
}
