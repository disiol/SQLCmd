package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.*;
import ua.com.denisimusIT.SQLCmd.model.DataSet;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

public class PostgresDatabaseManagerTest {

    private String tableName;
    private final static ByteArrayOutputStream OUT_CONTENT = new ByteArrayOutputStream();
    private final static String NEW_LINE = System.lineSeparator();


    private static final PostgresDatabaseManager POSTGRES_DATABASE_MANAGER = new PostgresDatabaseManager();
    private static final String databaseName = "postgres";
    private static final String userName = "postgres";
    private static final String password = "1111";

    private static final String TEST_TABLE_NAME = "testTable";
    private static final String testDatabaseName = "testdatabase";
    private static final String testUserName = "testUser";
    private static final String testPassword = "password";


    @BeforeClass
    public static void setup() {

        connectToDB();
        POSTGRES_DATABASE_MANAGER.createDatabase(testDatabaseName);
        connectToDB();
        POSTGRES_DATABASE_MANAGER.giveAccessUserToTheDatabase(testDatabaseName, userName);
        connectToTestDatabase();
        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL, PASSWORD  TEXT  NOT NULL";
        POSTGRES_DATABASE_MANAGER.createATable(TEST_TABLE_NAME, columnsValues);
    }


    private static void connectToDB() {
        POSTGRES_DATABASE_MANAGER.connectToDatabase(databaseName, userName, password);
    }

    @Before
    public void connectToTestDatabaseBefore() {
        POSTGRES_DATABASE_MANAGER.connectToDatabase(testDatabaseName, userName, password);
    }

    public static void connectToTestDatabase() {
        POSTGRES_DATABASE_MANAGER.connectToDatabase(testDatabaseName, userName, password);
    }


    @Test
    public void TestGetTableColumns() {

        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL, PASSWORD  TEXT  NOT NULL";
        String tableNameTestColumns = "testtable2";
        POSTGRES_DATABASE_MANAGER.createATable(tableNameTestColumns, columnsValues);

        String actual = POSTGRES_DATABASE_MANAGER.getTableColumns(tableNameTestColumns).toString();
        assertEquals("GetTableColumns","[id, name, password]", actual);
    }

    @Test
    public void testGetTableData() {

        tableName = "company";

        // when
        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL,PASSWORD  TEXT  NOT NULL";

        POSTGRES_DATABASE_MANAGER.createATable(tableName, columnsValues);
        String expected = "[testtable, company]";
        Object[] actual = POSTGRES_DATABASE_MANAGER.getTableNames().toArray();
        assertEquals("сreateTableCompany", expected, Arrays.toString(actual));


        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        POSTGRES_DATABASE_MANAGER.insertData(tableName, input);

        // then
        DataSet[] company = POSTGRES_DATABASE_MANAGER.getTableData(tableName);
        assertEquals("length", 1, company.length);

        DataSet user = company[0];
        assertEquals("[id, name, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, Stiven, pass]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testUpdateTableData() {
        // given
        POSTGRES_DATABASE_MANAGER.clearATable(TEST_TABLE_NAME);


        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        POSTGRES_DATABASE_MANAGER.insertData(TEST_TABLE_NAME, input);

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




    @Test
    public void connectExceptions() throws Exception {
        //not the correct password

        // given
        String actualException = null;

        String database = "sqlCmd";
        String user = "postgres";
        String password = "";
        try {
            POSTGRES_DATABASE_MANAGER.connectToDatabase(database,
                    user, password);
        } catch (Exception e) {
            actualException = e.getCause().toString();
        }


        // when
        Boolean actual = POSTGRES_DATABASE_MANAGER.isConnected();

        assertFalse("connectToDatabase to data base", actual);

        // then

        String expected;
        expected = String.format("org.postgresql.util.PSQLException: FATAL: password authentication failed " +
                "for user \"%s\"", user);
        Assert.assertEquals("not the correct password", expected, actualException);

        //not the correct user


        //given

        database = "sqlCmd";
        user = "a";
        password = "1111";
        try {
            POSTGRES_DATABASE_MANAGER.connectToDatabase(database,
                    user, password);
        } catch (Exception e) {
            actualException = e.getCause().toString();
        }

        // then

        expected = String.format("org.postgresql.util.PSQLException: FATAL: password authentication failed " +
                "for user \"%s\"", user);
        Assert.assertEquals("not the correct password", expected, actualException);


        database = "sq";
        user = "postgres";
        password = "1111";
        try {
            POSTGRES_DATABASE_MANAGER.connectToDatabase(database,
                    user, password);
        } catch (Exception e) {
            actualException = e.getCause().toString();
        }

        // then

        expected = String.format("org.postgresql.util.PSQLException: FATAL: database \"%s\" does not exist", database);
        Assert.assertEquals("not the correct database", expected, actualException);


    }


    @Test
    public void getTableColumnTestWanRow() {


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
    public void GetTableColumnTestTooRow() {

        POSTGRES_DATABASE_MANAGER.clearATable(TEST_TABLE_NAME);
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
    public static void dropDatabase() {
        connectToDB();
        POSTGRES_DATABASE_MANAGER.disconnectOfDatabase(testDatabaseName);
        //DropDatabase
        connectToDB();
        POSTGRES_DATABASE_MANAGER.dropDatabase(testDatabaseName);


    }


}