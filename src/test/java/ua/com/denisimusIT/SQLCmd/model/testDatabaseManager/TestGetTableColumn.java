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
public class TestGetTableColumn {
    final String newline = System.lineSeparator();
    private static final DatabaseManager POSTGRES_DATABASE_MANAGER = new PostgresDatabaseManager();
    private final static String DATA_BASE = "postgres";
    private final static String USER = "postgres";
    private final static String PASSWORD = "1111";
    private final static String TEST_DATABASE_NAME = "testdatabase";
    private final static String TEST_TABLE_NAME = "testtable";

    @BeforeClass
    public static void setUpBeforClass() {
        POSTGRES_DATABASE_MANAGER.connectToDatabase(DATA_BASE, USER, PASSWORD);
        POSTGRES_DATABASE_MANAGER.createDatabase(TEST_DATABASE_NAME);
        POSTGRES_DATABASE_MANAGER.connectToDatabase(TEST_DATABASE_NAME, USER, PASSWORD);

        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL,PASSWORD  TEXT  NOT NULL";
        POSTGRES_DATABASE_MANAGER.createATable(TEST_TABLE_NAME, columnsValues);

    }


    @Test
    public void testWanRow() {


        // given
        POSTGRES_DATABASE_MANAGER.clearATable(TEST_TABLE_NAME);
        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        POSTGRES_DATABASE_MANAGER.insertData(TEST_TABLE_NAME, input);

        // then
        //принимает данные для выдачи
        String dataGetId = "id";
        DataSet[] users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);


        DataSet user = users[0];
        assertEquals("[id]", Arrays.toString(user.getNames()));
        assertEquals("[13]", Arrays.toString(user.getValues()));


        dataGetId = "name";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users[0];
        assertEquals("[name]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven]", Arrays.toString(user.getValues()));


        dataGetId = "password";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users[0];
        assertEquals("[password]", Arrays.toString(user.getNames()));
        assertEquals("[pass]", Arrays.toString(user.getValues()));

        dataGetId = "id,password";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users[0];
        assertEquals("[id, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, pass]", Arrays.toString(user.getValues()));

        dataGetId = "name,id,password";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users[0];
        assertEquals("[name, id, password]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven, 13, pass]", Arrays.toString(user.getValues()));


    }


    @Test
    public void testTooRow() {


        // given
        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        POSTGRES_DATABASE_MANAGER.insertData(TEST_TABLE_NAME, input);

        DataSet input2 = new DataSet();
        input2.put("id", 14);
        input2.put("name", "Stiven2");
        input2.put("password", "pass2");
        POSTGRES_DATABASE_MANAGER.insertData(TEST_TABLE_NAME, input2);

        // then
        //принимает данные для выдачи
        String dataGetId = "id";
        DataSet[] users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);


        DataSet user = users[0];
        assertEquals("[id]", Arrays.toString(user.getNames()));
        assertEquals("[13]", Arrays.toString(user.getValues()));

        user = users[1];
        assertEquals("[id]", Arrays.toString(user.getNames()));
        assertEquals("[14]", Arrays.toString(user.getValues()));


        dataGetId = "name";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users[0];
        assertEquals("[name]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven]", Arrays.toString(user.getValues()));

        user = users[1];
        assertEquals("[name]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven2]", Arrays.toString(user.getValues()));


        dataGetId = "password";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users[0];
        assertEquals("[password]", Arrays.toString(user.getNames()));
        assertEquals("[pass]", Arrays.toString(user.getValues()));

        user = users[1];
        assertEquals("[password]", Arrays.toString(user.getNames()));
        assertEquals("[pass2]", Arrays.toString(user.getValues()));

        dataGetId = "id,password";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users[0];
        assertEquals("[id, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, pass]", Arrays.toString(user.getValues()));

        user = users[1];
        assertEquals("[id, password]", Arrays.toString(user.getNames()));
        assertEquals("[14, pass2]", Arrays.toString(user.getValues()));

        dataGetId = "name,id,password";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);

        user = users[0];
        assertEquals("[name, id, password]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven, 13, pass]", Arrays.toString(user.getValues()));

        user = users[1];
        assertEquals("[name, id, password]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven2, 14, pass2]", Arrays.toString(user.getValues()));


    }


    @AfterClass
    public static void deleteTable() {
        //TODO drop dataBase
        // drop  таблицу с данами
        POSTGRES_DATABASE_MANAGER.dropTable(TEST_TABLE_NAME);
    }
}
