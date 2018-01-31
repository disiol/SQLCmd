package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.*;
import ua.com.denisimusIT.SQLCmd.model.DataSet;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
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
        POSTGRES_DATABASE_MANAGER.createDatabase("\"" + TEST_DATABASE_NAME + "\"");
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
        POSTGRES_DATABASE_MANAGER.connectToDatabase(testDatabaseName, userName, password); //TODO
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
    public void testGetTableData() {

        tableName = "company";

        // when
        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL,PASSWORD  TEXT  NOT NULL";

        POSTGRES_DATABASE_MANAGER.createATable(tableName, columnsValues);
        String expected = "[testTable, company]";
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
    public void testClearATable() {

        tableName = "company2";

        // when
        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL,PASSWORD  TEXT  NOT NULL";

        POSTGRES_DATABASE_MANAGER.createATable(tableName, columnsValues);
        String expected = "[testTable, company2]";
        Object[] actual = POSTGRES_DATABASE_MANAGER.getTableNames().toArray();
        assertEquals("сreateTableCompany", expected, Arrays.toString(actual));


        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        POSTGRES_DATABASE_MANAGER.insertData(tableName, input);


        DataSet[] company = POSTGRES_DATABASE_MANAGER.getTableData(tableName);
        assertEquals("lengthBeforeClear", 1, company.length);

        DataSet user = company[0];
        assertEquals("[id, name, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, Stiven, pass]", Arrays.toString(user.getValues()));

        // then
        POSTGRES_DATABASE_MANAGER.clearATable(tableName);
        List<String> companyColumns = POSTGRES_DATABASE_MANAGER.getTableColumns(tableName);

        assertEquals("testClearATableNames", "[id, name, password]", companyColumns.toString());

        company = POSTGRES_DATABASE_MANAGER.getTableData(tableName);
        assertEquals("lengthAfterClear", 0, company.length);

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

    @Test
    public void сreateTableCompany() {
        System.setOut(new PrintStream(OUT_CONTENT));

        // создает таблицу и проверает создана ли она
        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL, PASSWORD  TEXT  NOT NULL";


        String tableName = "company2";
        POSTGRES_DATABASE_MANAGER.createATable(tableName, columnsValues);


        String expected_1 = "[id, name, password]";
        String actual_1 = POSTGRES_DATABASE_MANAGER.getTableColumns(tableName).toString();
        assertEquals("getTableColumns", expected_1, actual_1);


    }


    @Test
    public void createDatabaseTest() {
        System.setOut(new PrintStream(OUT_CONTENT));

        //before
        String dataBaseName = "testdatabase2";
        List<String> dataBaseNames = POSTGRES_DATABASE_MANAGER.getDatabaseNames();
        dataBaseNames.add(dataBaseName);
        Collections.sort(dataBaseNames);
        Object[] expected = dataBaseNames.toArray();


        //then
        POSTGRES_DATABASE_MANAGER.createDatabase(dataBaseName);
        connectToTestDatabase(TEST_DATABASE_NAME, userName, password);
        List<String> actualDatabaseNames = POSTGRES_DATABASE_MANAGER.getDatabaseNames();
        Collections.sort(actualDatabaseNames);
        Object[] actualDatabaseNamesSorted = actualDatabaseNames.toArray();
        assertEquals("getDatabaseNames", Arrays.toString(expected), Arrays.toString(actualDatabaseNamesSorted));


        //after
        connectToTestDatabase(TEST_DATABASE_NAME, userName, password);
        POSTGRES_DATABASE_MANAGER.dropDatabase(dataBaseName);


    }

    @Ignore("сделаю после рефактроринга ексепшенов")
    @Test
    public void createUserTest() {
        //todo
        System.setOut(new PrintStream(OUT_CONTENT));


        String user = "den2";
        String password1 = "test";
        POSTGRES_DATABASE_MANAGER.createUser(user, password1);


        connectToDB();
        String testDatabaseName2 = "test2";
        POSTGRES_DATABASE_MANAGER.createDatabase("test2");
        connectToDB();
        POSTGRES_DATABASE_MANAGER.giveAccessUserToTheDatabase(testDatabaseName2, user);
        String expected = "Creating user: " + user + NEW_LINE +
                "It is created user: " + user + " with the password: test" + NEW_LINE +
                "Access user: " + user + " to the database: test2 it is allow";
        String actual = OUT_CONTENT.toString();
        Assert.assertEquals("created user", expected, actual);

        connectToDB();
        POSTGRES_DATABASE_MANAGER.createUser(user, password1);
        String expected1 = "The user : " + user + " already is created";
        String actual2 = OUT_CONTENT.toString();
        Assert.assertEquals("The " + user + " already is created", expected1, actual2);


        connectToDB();
        POSTGRES_DATABASE_MANAGER.dropDatabase(testDatabaseName2);
        connectToDB();
        POSTGRES_DATABASE_MANAGER.dropUser(user);


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
//TODO  after refactoring exe
        POSTGRES_DATABASE_MANAGER.disconnectOfDatabase(TEST_DATABASE_NAME);

        boolean connected = POSTGRES_DATABASE_MANAGER.isConnected();
        assertFalse("disconnect", connected);


    }


    @Test
    public void dropDatabaseTest() {
        System.setOut(new PrintStream(OUT_CONTENT));

        connectToDB();
        String dataBaseName = "testdrop";
        POSTGRES_DATABASE_MANAGER.createDatabase(dataBaseName);
        connectToDB();
        POSTGRES_DATABASE_MANAGER.dropDatabase(dataBaseName);


        connectToDB();

        List<String> dataBaseNames = POSTGRES_DATABASE_MANAGER.getDatabaseNames();
        dataBaseNames.remove(dataBaseName);
        Collections.sort(dataBaseNames);

        String expected = dataBaseNames.toString();
        List<String> actualDatabaseNames = POSTGRES_DATABASE_MANAGER.getDatabaseNames();
        Collections.sort(actualDatabaseNames);
        Object[] actualDatabaseNamesSorted = actualDatabaseNames.toArray();
        assertEquals("dropDatabaseNames", expected, Arrays.toString(actualDatabaseNamesSorted));
    }

    @Test
    public void dropTableCompany() {
        String tableName = "company2";
        //defore
        //get tables names
        List<String> expectedTables = POSTGRES_DATABASE_MANAGER.getTableNames();
        POSTGRES_DATABASE_MANAGER.createATable(tableName, "");
        expectedTables.add(tableName);
        Collections.sort(expectedTables);

        List<String> actual = POSTGRES_DATABASE_MANAGER.getTableNames();
        Collections.sort(actual);

        assertEquals("сreateTableCompany", expectedTables.toString(), actual.toString());

        System.setOut(new PrintStream(OUT_CONTENT));


        //тест удаления таблицы
        POSTGRES_DATABASE_MANAGER.dropTable(tableName);
        String expected_1 = "Table " + tableName + " deleted in given database..." + NEW_LINE;
        String actual_1 = OUT_CONTENT.toString();

        assertEquals("dropTableCompanyMessage", expected_1, actual_1);
        expectedTables.remove(tableName);

        String expected_2 = expectedTables.toString();
        List<String> actual_2 = POSTGRES_DATABASE_MANAGER.getTableNames();
        Collections.sort(actual_2);
        assertEquals("dropTableCompany", expected_2, actual_2.toString());

        // создает таблицу и проверает создана ли она
        // вытаскивает значение данных  и сравнивает
    }


    @Test
    public void ShowDatabaseTest() {
        String databaseName1 = "test3";
        connectToDB();
        List<String> databaseNames = POSTGRES_DATABASE_MANAGER.getDatabaseNames();
        databaseNames.add(databaseName1);
        Collections.sort(databaseNames);
        connectToDB();
        POSTGRES_DATABASE_MANAGER.createDatabase(databaseName1);


        connectToDB();
        String expected = databaseNames.toString();
        List<String> actualDatabaseNames = POSTGRES_DATABASE_MANAGER.getDatabaseNames();
        Collections.sort(actualDatabaseNames);


        assertEquals("getDatabaseNames", expected, actualDatabaseNames.toString());

        connectToDB();
        POSTGRES_DATABASE_MANAGER.dropDatabase(databaseName1);

    }

    @Test

    public void GiveAccessTest() {
        System.setOut(new PrintStream(OUT_CONTENT));
        String testDatabaseName = "testdatabase4";
        String testUser = "den";
        String testPassword = "testPassword";


        connectToDB();
        POSTGRES_DATABASE_MANAGER.createDatabase(testDatabaseName);
        connectToDB();
        POSTGRES_DATABASE_MANAGER.createUser(testUser, testPassword);

        connectToDB();
        POSTGRES_DATABASE_MANAGER.giveAccessUserToTheDatabase(testDatabaseName, testUser);

        String expected = "Creating user: " + testUser + NEW_LINE +
                "It is created user: den with the password: testPassword" + NEW_LINE +
                "Access user: den to the database: " + testDatabaseName + " it is allow";
        String actual = OUT_CONTENT.toString();
        Assert.assertEquals("GiveAccess", expected, actual);

        POSTGRES_DATABASE_MANAGER.connectToDatabase(testDatabaseName, testUser, testPassword);
        assertTrue("connect", POSTGRES_DATABASE_MANAGER.isConnected());


        POSTGRES_DATABASE_MANAGER.disconnectOfDatabase(testDatabaseName);
        connectToDB();
        POSTGRES_DATABASE_MANAGER.giveAccessUserToTheDatabase(testDatabaseName, userName);
        connectToDB();
        POSTGRES_DATABASE_MANAGER.dropDatabase(testDatabaseName);
        connectToDB();
        POSTGRES_DATABASE_MANAGER.dropUser(testUser);

    }

    @AfterClass
    public static void dropDatabase() {
        connectToDB();
        POSTGRES_DATABASE_MANAGER.disconnectOfDatabase(TEST_DATABASE_NAME);
        //DropDatabase
        connectToDB();
        POSTGRES_DATABASE_MANAGER.giveAccessUserToTheDatabase("\"" + TEST_DATABASE_NAME + "\"", userName);
        connectToDB();
        POSTGRES_DATABASE_MANAGER.dropDatabase("\"" + TEST_DATABASE_NAME + "\"");


    }

}