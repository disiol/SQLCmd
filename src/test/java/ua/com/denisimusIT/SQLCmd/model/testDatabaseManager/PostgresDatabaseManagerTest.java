package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.*;
import ua.com.denisimusIT.SQLCmd.model.DataSetImpl;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

public class PostgresDatabaseManagerTest {

    private String tableName;
    private ByteArrayOutputStream OUT_CONTENT = new ByteArrayOutputStream();
    private final static String NEW_LINE = System.lineSeparator();


    private static final PostgresDatabaseManager POSTGRES_DATABASE_MANAGER = new PostgresDatabaseManager();
    private static final String databaseName = "postgres";
    private static final String userName = "postgres";
    private static final String password = "1111";

    private static final String TEST_TABLE_NAME = "\"testTable\"";
    private static final String TEST_DATABASE_NAME = "testDatabase2";


    @BeforeClass
    public static void setup() {

        connectToDB();
        tryCrateDB("\"" + TEST_DATABASE_NAME + "\"");
        connectToDB();
        POSTGRES_DATABASE_MANAGER.giveAccessUserToTheDatabase("\"" + TEST_DATABASE_NAME + "\"", userName);
        connectToTestDatabase(TEST_DATABASE_NAME, userName, password);
        String columnsValues = "id int  NOT NULL, name TEXT NOT NULL, PASSWORD  TEXT  NOT NULL";
        POSTGRES_DATABASE_MANAGER.createATable(TEST_TABLE_NAME, columnsValues);
    }


    private static void connectToDB() {
        POSTGRES_DATABASE_MANAGER.connectToDatabase(databaseName, userName, password);
    }

    @Before
    public void connectToTestDatabaseBefore() {
        POSTGRES_DATABASE_MANAGER.connectToDatabase(TEST_DATABASE_NAME, userName, password);
    }

    public static void connectToTestDatabase(String testDatabaseName, String userName, String password) {
        POSTGRES_DATABASE_MANAGER.connectToDatabase(testDatabaseName, userName, password);
    }


    @Test
    public void TestGetTableColumns() {

        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL, PASSWORD  TEXT  NOT NULL";
        String tableNameTestColumns = "testtable2";
        POSTGRES_DATABASE_MANAGER.createATable(tableNameTestColumns, columnsValues);

        String actual = POSTGRES_DATABASE_MANAGER.getTableColumns(tableNameTestColumns).toString();
        assertEquals("GetTableColumns", "[id, name, password]", actual);
    }

 @Test
    public void TestGetTableColumns_exehen_table_didon_crate() {

        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL, PASSWORD  TEXT  NOT NULL";
        String tableNameTestColumns = "testtable2";
        POSTGRES_DATABASE_MANAGER.createATable(tableNameTestColumns, columnsValues);

        String actual = POSTGRES_DATABASE_MANAGER.getTableColumns(tableNameTestColumns).toString();
        assertEquals("TestGetTableColumns_exehen_table_didon_crate", "[id, name, password]", actual);
    }

    @Test
    public void testGetTableData() {

        tableName = "company";

        // when
        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL,PASSWORD  TEXT  NOT NULL";

        POSTGRES_DATABASE_MANAGER.createATable(tableName, columnsValues);


        DataSetImpl input = new DataSetImpl();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        POSTGRES_DATABASE_MANAGER.insertData(tableName, input);

        // then
        List<DataSetImpl> company = POSTGRES_DATABASE_MANAGER.getTableData(tableName);
        assertEquals("length", 1, company.size());

        DataSetImpl user = company.get(0);
        assertEquals("[id, name, password]", user.getNames().toString());
        assertEquals("[13, Stiven, pass]", user.getValues().toString());

        //after
        tryDropDB(tableName);
    }

    @Test
    public void testUpdateTableData() {
        // given
        POSTGRES_DATABASE_MANAGER.clearATable(TEST_TABLE_NAME);


        DataSetImpl input = new DataSetImpl();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        POSTGRES_DATABASE_MANAGER.insertData(TEST_TABLE_NAME, input);

        // when
        DataSetImpl newValue = new DataSetImpl();
        newValue.put("password", "pass2");
        newValue.put("name", "Pup");
        POSTGRES_DATABASE_MANAGER.updateTableData(TEST_TABLE_NAME, 13, newValue);

        // then
        List<DataSetImpl> users = POSTGRES_DATABASE_MANAGER.getTableData(TEST_TABLE_NAME);
        assertEquals(1, users.size());

        DataSetImpl user = users.get(0);
        assertEquals("[id, name, password]", user.getNames().toString());
        assertEquals("[13, Pup, pass2]", user.getValues().toString());

    }


    @Test
    public void clearATableTest() {
        //given
        tableName = "company3";

        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL,PASSWORD  TEXT  NOT NULL";
        POSTGRES_DATABASE_MANAGER.createATable(tableName, columnsValues);

        DataSetImpl input = new DataSetImpl();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        POSTGRES_DATABASE_MANAGER.insertData(tableName, input);

        // when
        List<DataSetImpl> company = POSTGRES_DATABASE_MANAGER.getTableData(tableName);
        assertEquals("lengthBeforeClear", 1, company.size());

        DataSetImpl user = company.get(0);
        assertEquals("[id, name, password]", user.getNames().toString());
        assertEquals("[13, Stiven, pass]", user.getValues().toString());

        // then
        POSTGRES_DATABASE_MANAGER.clearATable(tableName);
        List<String> companyColumns = POSTGRES_DATABASE_MANAGER.getTableColumns(tableName);

        assertEquals("testClearATableNames", "[id, name, password]", companyColumns.toString());

        company = POSTGRES_DATABASE_MANAGER.getTableData(tableName);
        assertEquals("lengthAfterClear", 0, company.size());


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
        DataSetImpl input = new DataSetImpl();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        POSTGRES_DATABASE_MANAGER.insertData(TEST_TABLE_NAME, input);

        // then
        //принимает данные для выдачи
        String dataGetId = "id";
        List<DataSetImpl> users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);


        DataSetImpl user = users.get(0);
        assertEquals("[id]", user.getNames().toString());
        assertEquals("[13]", user.getValues().toString());


        dataGetId = "name";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users.get(0);
        assertEquals("[name]", user.getNames().toString());
        assertEquals("[Stiven]", user.getValues().toString());


        dataGetId = "password";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users.get(0);
        assertEquals("[password]", user.getNames().toString());
        assertEquals("[pass]", user.getValues().toString());

        dataGetId = "id,password";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users.get(0);
        assertEquals("[id, password]", user.getNames().toString());
        assertEquals("[13, pass]", user.getValues().toString());

        dataGetId = "name,id,password";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users.get(0);
        assertEquals("[name, id, password]", user.getNames().toString());
        assertEquals("[Stiven, 13, pass]", user.getValues().toString());


    }


    @Test
    public void GetTableColumnTestTooRow() {

        POSTGRES_DATABASE_MANAGER.clearATable(TEST_TABLE_NAME);
        // given
        DataSetImpl input = new DataSetImpl();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        POSTGRES_DATABASE_MANAGER.insertData(TEST_TABLE_NAME, input);

        DataSetImpl input2 = new DataSetImpl();
        input2.put("id", 14);
        input2.put("name", "Stiven2");
        input2.put("password", "pass2");
        POSTGRES_DATABASE_MANAGER.insertData(TEST_TABLE_NAME, input2);

        // then
        //принимает данные для выдачи
        String dataGetId = "id";
        List<DataSetImpl> users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);


        DataSetImpl user = users.get(0);
        assertEquals("[id]", user.getNames().toString());
        assertEquals("[13]", user.getValues().toString());

        user = users.get(1);
        assertEquals("[id]", user.getNames().toString());
        assertEquals("[14]", user.getValues().toString());


        dataGetId = "name";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users.get(0);
        assertEquals("[name]", user.getNames().toString());
        assertEquals("[Stiven]", user.getValues().toString());

        user = users.get(1);
        assertEquals("[name]", user.getNames().toString());
        assertEquals("[Stiven2]", user.getValues().toString());


        dataGetId = "password";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users.get(0);
        assertEquals("[password]", user.getNames().toString());
        assertEquals("[pass]", user.getValues().toString());

        user = users.get(1);
        assertEquals("[password]", user.getNames().toString());
        assertEquals("[pass2]", user.getValues().toString());

        dataGetId = "id,password";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users.get(0);
        assertEquals("[id, password]", user.getNames().toString());
        assertEquals("[13, pass]", user.getValues().toString());

        user = users.get(1);
        assertEquals("[id, password]", user.getNames().toString());
        assertEquals("[14, pass2]", user.getValues().toString());

        dataGetId = "name,id,password";
        users = POSTGRES_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);

        user = users.get(0);
        assertEquals("[name, id, password]", user.getNames().toString());
        assertEquals("[Stiven, 13, pass]", user.getValues().toString());

        user = users.get(1);
        assertEquals("[name, id, password]", user.getNames().toString());
        assertEquals("[Stiven2, 14, pass2]", user.getValues().toString());


    }

    @Test
    public void createTableCompany() {

        // given
        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL, PASSWORD  TEXT  NOT NULL";

        //wen
        String tableName = "company2";
        POSTGRES_DATABASE_MANAGER.createATable(tableName, columnsValues);

        //then
        String expected_1 = "[id, name, password]";
        String actual_1 = POSTGRES_DATABASE_MANAGER.getTableColumns(tableName).toString();
        assertEquals("getTableColumns", expected_1, actual_1);


    }


    @Test
    public void createDatabaseTest() {

        //before
        String dataBaseName = "testdatabase2";
        tryDropDB(dataBaseName);
        connectToDB();
        Set<String> dataBaseNames = POSTGRES_DATABASE_MANAGER.getDatabaseNames();
        dataBaseNames.add(dataBaseName);
        Object[] expected = dataBaseNames.toArray();
        //Arrays.sort(expected);


        //then
        tryCrateDB(dataBaseName);
        connectToTestDatabase(TEST_DATABASE_NAME, userName, password);
        Set<String> actualDatabaseNames = POSTGRES_DATABASE_MANAGER.getDatabaseNames();

        Object[] actualDatabaseNamesSorted = actualDatabaseNames.toArray();
        String actual = Arrays.toString(actualDatabaseNamesSorted);
        assertEquals("createDatabaseTest", Arrays.toString(expected), actual);


        //after
        connectToDB();
        tryDropDB(dataBaseName);


    }

    @Test

    public void create_Database_Database_Already_Exist_Test() {
        String actual = null;
        String dataBaseName = null;

//TODO
        try {
            //before
            dataBaseName = "testdatabase2";
            POSTGRES_DATABASE_MANAGER.createDatabase(dataBaseName);


            //then

            POSTGRES_DATABASE_MANAGER.createDatabase(dataBaseName);
        } catch (RuntimeException e) {
            actual = getMessageError(e.getMessage(), e.getCause());

        }


        String expected = "Cant create database: " + dataBaseName + " ERROR: database \"" + dataBaseName + "\" already exists";


        assertEquals("create_Database_Database_Already_Exist_Test", expected, actual);


        //after
        connectToTestDatabase(TEST_DATABASE_NAME, userName, password);
        POSTGRES_DATABASE_MANAGER.dropDatabase(dataBaseName);


    }

    private String getMessageError(String message, Throwable cause2) {
        String actual;
        actual = message;
        Throwable cause = cause2;
        if (cause != null) {
            if (actual.toString().equals(cause.toString())) {

            } else {
                actual += " " + cause.getMessage();
            }
        }
        return actual;
    }


    @Test
    public void currentDatabaseTest() {

        String expected = "[" + TEST_DATABASE_NAME + "]";
        String actual = POSTGRES_DATABASE_MANAGER.currentDatabase().toString();

        assertEquals("currentDatabase", expected, actual);
    }


    @Test
    public void disconnectOfDatabaseTest() {
        System.setOut(new PrintStream(OUT_CONTENT));
        POSTGRES_DATABASE_MANAGER.disconnectOfDatabase(TEST_DATABASE_NAME);

        boolean connected = POSTGRES_DATABASE_MANAGER.isConnected();
        assertFalse("disconnect", connected);


    }


    @Test
    public void dropTableCompany() {
        String tableName = "company2";
        //get tables names
        Set<String> expectedTables = POSTGRES_DATABASE_MANAGER.getTableNames();
        POSTGRES_DATABASE_MANAGER.createATable(tableName, "");
        expectedTables.add(tableName);

        Set<String> actual = POSTGRES_DATABASE_MANAGER.getTableNames();

        assertEquals("сreateTableCompany", expectedTables.toString(), actual.toString());

        System.setOut(new PrintStream(OUT_CONTENT));


        //тест удаления таблицы
        POSTGRES_DATABASE_MANAGER.dropTable(tableName);
        String expected_1 = "Table " + tableName + " deleted in given database..." + NEW_LINE;
        String actual_1 = OUT_CONTENT.toString();

        assertEquals("dropTableCompanyMessage", expected_1, actual_1);
        expectedTables.remove(tableName);

        String expected_2 = expectedTables.toString();
        Set<String> actual_2 = POSTGRES_DATABASE_MANAGER.getTableNames();
        assertEquals("dropTableCompany", expected_2, actual_2.toString());


    }


    @Test
    public void ShowDatabaseTest() {
        String databaseName1 = "test3";
        connectToDB();
        Set<String> databaseNames = POSTGRES_DATABASE_MANAGER.getDatabaseNames();
        databaseNames.add(databaseName1);

        connectToDB();
        tryCrateDB(databaseName1);

        connectToDB();
        String expected = databaseNames.toString();
        Set<String> actualDatabaseNames = POSTGRES_DATABASE_MANAGER.getDatabaseNames();


        assertEquals("getDatabaseNames", expected, actualDatabaseNames.toString());

        connectToDB();
        tryDropDB(databaseName1);

    }


    @Test
    public void DropDatabaseTest() {
        String databaseName1 = "test3";
        //Before
        connectToDB();
        tryCrateDB(databaseName1);

        connectToDB();
        Set<String> expected = POSTGRES_DATABASE_MANAGER.getDatabaseNames();
        expected.remove(databaseName1);

        //then
        POSTGRES_DATABASE_MANAGER.disconnectOfDatabase(databaseName1);
        connectToDB();
        POSTGRES_DATABASE_MANAGER.dropDatabase(databaseName1);


        Set<String> actual = POSTGRES_DATABASE_MANAGER.getDatabaseNames();
        assertEquals("DropDatabaseTest", expected, actual);


    }

    @Test
    public void DropDatabase_Test_Exephen_Database_does_not_exist() {
        String databaseName1 = "test3";
        String actual = null;
        String expected;
        //Before
        expected = "Cant not drop database :test3 ERROR: database \"test3\" does not exist";
        connectToDB();
        tryCrateDB(databaseName1);
        connectToDB();
        POSTGRES_DATABASE_MANAGER.dropDatabase(databaseName1);

        //then
        try {
            POSTGRES_DATABASE_MANAGER.dropDatabase(databaseName1);
        } catch (Exception e) {

            actual = getMessageError(e.getMessage(), e.getCause());
        }


        assertEquals("database " + databaseName1 + " does not exist", expected, actual);


    }

    @Test
    public void changeDatabaseTest() {
        String databaseName1 = "test3";
        //before
        tryCrateDB(databaseName1);
        connectToDB();
        POSTGRES_DATABASE_MANAGER.giveAccessUserToTheDatabase(databaseName1, userName);

        //then

        connectToTestDatabase(databaseName1, userName, password);

        String expected = "[test3]";

        List<String> actual = POSTGRES_DATABASE_MANAGER.currentDatabase();
        assertEquals("changeDatabaseTest", expected, actual.toString());

        //after
        connectToDB();
        POSTGRES_DATABASE_MANAGER.giveAccessUserToTheDatabase(databaseName1, userName);
        POSTGRES_DATABASE_MANAGER.disconnectOfDatabase(databaseName1);
        tryDropDB(databaseName1);

    }

    private static void tryCrateDB(String databaseName) {
        boolean haveBase = false;
        try {
            connectToTestDatabase(databaseName, userName, password);
            haveBase = true;
        } catch (Exception e) {
//нет подключения
            haveBase = false;
        }
        if (!haveBase) {
            connectToDB();
            try {
                POSTGRES_DATABASE_MANAGER.createDatabase(databaseName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void tryDropDB(String databaseName) {
        boolean haveBase = false;
        try {
            POSTGRES_DATABASE_MANAGER.giveAccessUserToTheDatabase(databaseName, userName);
            connectToTestDatabase(databaseName, userName, password);
            haveBase = true;
        } catch (Exception e) {
//нет подключения
            haveBase = false;
        }
        if (haveBase) {
            connectToDB();

            POSTGRES_DATABASE_MANAGER.giveAccessUserToTheDatabase(databaseName, userName);
            if (POSTGRES_DATABASE_MANAGER.isConnected()) {
                POSTGRES_DATABASE_MANAGER.disconnectOfDatabase(databaseName);
            }
            connectToDB();
            POSTGRES_DATABASE_MANAGER.dropDatabase(databaseName);

        }
    }

    @AfterClass
    public static void dropDatabase() {
        connectToDB();
        POSTGRES_DATABASE_MANAGER.disconnectOfDatabase(TEST_DATABASE_NAME);
        //DropDatabase
        connectToDB();
        //   POSTGRES_DATABASE_MANAGER.giveAccessUserToTheDatabase("\"" + TEST_DATABASE_NAME + "\"", userName);
        connectToDB();
        POSTGRES_DATABASE_MANAGER.dropDatabase("\"" + TEST_DATABASE_NAME + "\"");


    }

}