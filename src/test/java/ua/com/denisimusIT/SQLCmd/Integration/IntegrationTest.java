package ua.com.denisimusIT.SQLCmd.Integration;

import org.junit.*;
import ua.com.denisimusIT.SQLCmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    private String newLine = System.lineSeparator();
    private static ConfigurableInputStream in;
    private static ByteArrayOutputStream out;


    private static final String databaseName = "postgres";
    private static final String userName = "postgres";
    private static final String password = "1111";


    private static String testTable = "testTable";
    private static String testDatabaseName = "testDatabase1";


    @BeforeClass
    public static void setup() throws IOException {
        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL, PASSWORD  TEXT  NOT NULL";


        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("createDatabase|" + testDatabaseName);
        in.add("connect|" + testDatabaseName + "|" + userName + "|" + password);
        in.add("create|" + testTable + "|" + columnsValues);
        in.add("exit");
        Main.main(new String[0]);
        //   System.err.println(out.toString());


    }

    @Test
    public void testListWithoutConnect() {
        // given
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a " +
                "format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "list" + newLine +
                "You cannot use a command 'list' be not connected by means of a command yet " +
                "connect|databaseName|userName|password" + newLine +
                "enter please command or help command for a help call" + newLine +
                "exit" + newLine +
                "See you soon!" + newLine;
        assertEquals("testListWithoutConnect", expected, getData());
    }

    @Test
    public void testFindWithoutConnect() {
        // given
        in.add("find|user");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("testFindWithoutConnect", "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in " +
                "a format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "find|user" + newLine +
                "You cannot use a command 'find|user' be not connected by means of a command yet " +
                "connect|databaseName|userName|password" + newLine +
                "enter please command or help command for a help call" + newLine +
                "exit" + newLine +
                "See you soon!" + newLine, getData());
    }

    @Test
    public void testUnsupported() {
        // given
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in " +
                "a format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "unsupported" + newLine +
                "You cannot use a command 'unsupported' be not connected by means of a command yet " +
                "connect|databaseName|userName|password" + newLine +
                "enter please command or help command for a help call" + newLine +
                "exit" + newLine +
                "See you soon!" + newLine;
        assertEquals("testUnsupported", expected, getData());
    }

    @Test
    public void testUnsupportedAfterConnect() {
        // given
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a " +
                "format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|" + databaseName + "|" + userName + "|" + password + newLine +
                "Opened database: " + "\"" + databaseName + "\"" + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "unsupported" + newLine +
                "Unsupported command:unsupported" + newLine +
                "enter please command or help command for a help call" + newLine +
                "exit" + newLine +
                "See you soon!" + newLine;

        assertEquals("testUnsupportedAfterConnect", expected, getData());
    }

    @Test
    public void testListAfterConnect() {
        // given
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("list");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a " +
                "format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|" + databaseName + "|" + userName + "|" + password + newLine +
                "Opened database: " + "\"" + databaseName + "\"" + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "list" + newLine +
                "Unsupported command:list" + newLine +
                "enter please command or help command for a help call" + newLine +
                "exit" + newLine +
                "See you soon!" + newLine;

        assertEquals("testListAfterConnect", expected, getData());
    }

    @Test
    public void testFindAfterConnect() {
        // given
        in.add("connect|" + testDatabaseName + "|" + userName + "|" + password);
        in.add("clear|" + testTable);
        in.add("find|" + testTable);
        in.add("exit");
        // when
        Main.main(new String[0]);

        // then
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a " +
                "format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|" + testDatabaseName + "|" + userName + "|" + password + newLine +
                "Opened database: " + "\"" + testDatabaseName + "\"" + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "clear|" + testTable + newLine +
                "The table: " + "\"" + testTable + "\"" + "  is cleared successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "find|" + testTable + newLine +
                "•+--------------------------------------------------" + newLine +
                "•+ id + name + password + " + newLine +
                "•+--------------------------------------------------" + newLine +
                "enter please command or help command for a help call" + newLine +
                "exit" + newLine +
                "See you soon!" + newLine;
        assertEquals("testFindAfterConnect", expected, getData());
    }


    @Test

    public void testExit() {

        //given
        in.add("exit");
        //then
        Main.main(new String[0]);
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a " +
                "format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "exit" + newLine +
                "See you soon!" + newLine;
        assertEquals("testExit", expected, actual);
    }

    @Before
    public void resetIn() throws IOException {
        in.reset();
    }

    @Test

    public void testHelpBeforeConnect() {

        //given
        in.add("help");
        in.add("exit");

        // when
        Main.main(new String[0]);
        //then
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a " +
                "format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "help" + newLine +
                "The existing command: " + newLine +
                "" + newLine +
                "\texit" + newLine +
                "\t\tfor an output from the program" + newLine +
                "" + newLine +
                "\thelp" + newLine +
                "\t\tfor an output of this list to the screen" + newLine +
                "" + newLine +
                "\tconnect|databaseName|userName|password" + newLine +
                "\t\tfor connection to the database with which we will work" + newLine +
                "" + newLine +
                "\tcreateDatabase|DatabaseName" + newLine +
                "\t\tcreated database" + newLine +
                "" + newLine +
                "\tgiveAccess|databaseName|userName" + newLine +
                "\t\tGive access user to database" + newLine +
                "" + newLine +
                "\tdropDatabase|DatabaseName" + newLine +
                "\t\tDelete database" + newLine +
                "" + newLine +
                "\ttables" + newLine +
                "\t\tshows the list of tables" + newLine +
                "" + newLine +
                "\tfind|tableName" + newLine +
                "\t\tfor receiving contents of the table tableName" + newLine +
                "" + newLine +
                "\tcreate|tableName|column1 column type, column2 column type,...,columnN column type" + newLine +
                "\t\tFor create table:" + newLine +
                "" + newLine +
                "\tinsert|tableName|column1|value1|column2|value2| ... |columnN | valueN" + newLine +
                "\t\tCommand for an insertion of one line in the given table " + newLine +
                "  \t• where: tableName - a table name" + newLine +
                "  \t• column1 - a name of the first column of record" + newLine +
                "  \t• value1 - value of the first column of record" + newLine +
                "  \t• column2 - a name of the second column of record" + newLine +
                "  \t• value2 - value of the second column of record" + newLine +
                "  \t• columnN - the record column name n-go" + newLine +
                "  \t• valueN - n-go value of a column of record" + newLine +
                newLine +
                "\tclear|tableName" + newLine +
                "\t\tfor cleaning of all table" + newLine
                + newLine +
                "\tdisconnect|databaseName" + newLine +
                "\t\tDisconnect of database" + newLine + newLine +
                "enter please command or help command for a help call" + newLine +
                "exit" + newLine +
                "See you soon!" + newLine;
        assertEquals("testHelpBeforeConnect", expected, actual);
    }


    @Test

    public void testConnect() {

        //given
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("exit");
        //then
        Main.main(new String[0]);
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a format: " +
                "connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|" + databaseName + "|" + userName + "|" + password + newLine +
                "Opened database: " + "\"" + databaseName + "\"" + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "exit" + newLine +
                "See you soon!" + newLine;
        assertEquals("testConnect", expected, actual);
    }


    @Test

    public void testHelpAfterConnect() {

        //given
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("help");
        in.add("exit");

        //then
        Main.main(new String[0]);

        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in " +
                "a format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|postgres|postgres|1111" + newLine +
                "Opened database: " + "\"" + databaseName + "\"" + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "help" + newLine +
                "The existing command: " + newLine +
                "" + newLine +
                "\texit" + newLine +
                "\t\tfor an output from the program" + newLine +
                "" + newLine +
                "\thelp" + newLine +
                "\t\tfor an output of this list to the screen" + newLine +
                "" + newLine +
                "\tconnect|databaseName|userName|password" + newLine +
                "\t\tfor connection to the database with which we will work" + newLine +
                "" + newLine +
                "\tcreateDatabase|DatabaseName" + newLine +
                "\t\tcreated database" + newLine +
                "" + newLine +
                "\tgiveAccess|databaseName|userName" + newLine +
                "\t\tGive access user to database" + newLine +
                "" + newLine +
                "\tdropDatabase|DatabaseName" + newLine +
                "\t\tDelete database" + newLine +
                "" + newLine +
                "\ttables" + newLine +
                "\t\tshows the list of tables" + newLine +
                "" + newLine +
                "\tfind|tableName" + newLine +
                "\t\tfor receiving contents of the table tableName" + newLine +
                "" + newLine +
                "\tcreate|tableName|column1 column type, column2 column type,...,columnN column type" + newLine +
                "\t\tFor create table:" + newLine +
                "" + newLine +
                "\tinsert|tableName|column1|value1|column2|value2| ... |columnN | valueN" + newLine +
                "\t\tCommand for an insertion of one line in the given table " + newLine +
                "  \t• where: tableName - a table name" + newLine +
                "  \t• column1 - a name of the first column of record" + newLine +
                "  \t• value1 - value of the first column of record" + newLine +
                "  \t• column2 - a name of the second column of record" + newLine +
                "  \t• value2 - value of the second column of record" + newLine +
                "  \t• columnN - the record column name n-go" + newLine +
                "  \t• valueN - n-go value of a column of record" + newLine +
                newLine +
                "\tclear|tableName" + newLine +
                "\t\tfor cleaning of all table" + newLine +
                newLine +
                "\tdisconnect|databaseName" + newLine +
                "\t\tDisconnect of database" + newLine +
                newLine +
                "enter please command or help command for a help call" + newLine +
                "exit" + newLine +
                "See you soon!" + newLine;
        assertEquals("testHelpBeforeConnect", expected, actual);
    }


    @Test
    public void testConnectExceptionParameters_3() throws Exception {

        //given
        in.add("connect|" + databaseName + "|" + userName);
        //then
        Main.main(new String[0]);
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a " +
                "format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|" + databaseName + "|" + userName + newLine +
                "Failure! for the reason:The number of parameters partitioned by the character '|' is incorrect," +
                " it is expected 4, but is: 3" + newLine +
                "\tTeam format connect|databaseName|userName|password, and you have entered: connect|postgres|postgres"
                + newLine +
                "Repeat attempt." + newLine;
        assertEquals("testConnectExceptionParameters_3", expected, actual);//given

    }

    @Test
    public void testConnectExceptiondatabase() throws Exception {

        //given
        in.add("connect|" + "_" + "|" + userName + "|" + password);
        //in.add("exit");
        //then
        Main.main(new String[0]);
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a format: " +
                "connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|" + "_" + "|" + userName + "|" + password + newLine +
                "Failure! for the reason:Cant get connection for model:_ user:postgres FATAL: database \"_\" does not exist" + newLine +
                "Repeat attempt." + newLine;
        assertEquals("testConnectExceptiondatabase", expected, actual);//given

    }

    @Test
    public void testConnectExceptionPassword() throws Exception {

        //given
        in.add("connect|" + databaseName + "|" + userName + "|" + "_");
        //in.add("exit");
        //then
        Main.main(new String[0]);
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a " +
                "format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|" + databaseName + "|" + userName + "|" + "_" + newLine +
                "Failure! for the reason:Cant get connection for model:postgres user:postgres FATAL: " +
                "password authentication failed for user \"" + userName + "\"" + newLine +
                "Repeat attempt." + newLine;
        assertEquals("testConnectExceptionPassword", expected, actual);

    }


    @Test
    public void testConnectExceptionUser() throws Exception {

        //given
        in.add("connect|" + databaseName + "|" + "_" + "|" + password);
        in.add("exit");
        //then
        Main.main(new String[0]);
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a format: " +
                "connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|" + databaseName + "|" + "_" + "|" + password + newLine +
                "Failure! for the reason:Cant get connection for model:postgres user:_ FATAL: password authentication failed " +
                "for user \"_\"" + newLine +
                "Repeat attempt." + newLine;
        assertEquals("testConnectExceptionPassword", expected, actual);

    }


    @Test
    public void createDatabase() throws IOException {
        //given
        String testDatabaseName5 = "testDatabaseName5";
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("createDatabase|" + testDatabaseName5);
        in.add("exit");
        //then
        Main.main(new String[0]);

        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in " +
                "a format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|" + databaseName + "|" + userName + "|" + password + newLine +
                "Opened database: " + "\"" + databaseName + "\"" + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "createDatabase|" + testDatabaseName5 + newLine +
                "The database: " + testDatabaseName5 + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "exit" + newLine +
                "See you soon!" + newLine;
        assertEquals("createDatabase", expected, actual);


        //dell database
        in.reset();
        out.reset();
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("dropDatabase|" + testDatabaseName5);
        in.add("exit");
        Main.main(new String[0]);

    }

    @Test
    public void createDatabaseExceptionParameters_3() throws IOException {
        //given
        String testDatabaseName5 = "testDatabaseName5";
        in.add("connect|" + databaseName + "|" + userName);
        in.add("createDatabase|" + testDatabaseName5);
        in.add("exit");
        //then
        Main.main(new String[0]);

        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in " +
                "a format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|" + databaseName + "|" + userName + newLine +
                "Failure! for the reason:The number of parameters partitioned by the character '|' is incorrect," +
                " it is expected 4, but is: 3" + newLine +
                "\tTeam format connect|databaseName|userName|password, and you have entered: connect|postgres|postgres"
                + newLine +
                "Repeat attempt." + newLine;
        assertEquals("ExceptionParameters_3", expected, actual);


        //dell database
        in.reset();
        out.reset();
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        Main.main(new String[0]);
        in.add("dropDatabase|" + testDatabaseName5);
        in.add("exit");
        Main.main(new String[0]);

    }


    @Test
    public void createDatabaseExceptionParameters_2() throws IOException {
        //given
        String testDatabaseName5 = "testDatabaseName5";
        in.add("connect|" + databaseName);
        in.add("createDatabase|" + testDatabaseName5);
        in.add("exit");
        //then
        Main.main(new String[0]);

        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in " +
                "a format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|" + databaseName + newLine +
                "Failure! for the reason:The number of parameters partitioned by the character '|' is incorrect," +
                " it is expected 4, but is: 2" + newLine +
                "\tTeam format connect|databaseName|userName|password, and you have entered: connect|postgres" + newLine +
                "Repeat attempt." + newLine;
        assertEquals("ExceptionParameters_2", expected, actual);


        //dell database
        in.reset();
        out.reset();
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        Main.main(new String[0]);
        in.add("dropDatabase|" + testDatabaseName5);
        in.add("exit");
        Main.main(new String[0]);

    }


    @Test
    public void createTable() throws IOException {

        //TODO take  list of tables before test nad add  testTable2 nam to the list
        //given
        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL, PASSWORD  TEXT  NOT NULL";

        String testTable2 = "testTable2";

        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("create|" + testTable2 + "|" + columnsValues);
        in.add("tables");
        in.add("exit");
        //then
        Main.main(new String[0]);

//TODO создать список таблиц
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a " +
                "format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|postgres|postgres|1111" + newLine +
                "Opened database: " + "\"" + databaseName + "\"" + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "create|" + testTable2 + "|" + columnsValues + newLine +
                "The table: testTable2 is created successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "tables" + newLine +
                "List of tablesNames: " + newLine +
                "[testTable2]" + newLine +
                "enter please command or help command for a help call" + newLine +
                "exit" + newLine +
                "See you soon!" + newLine;
        assertEquals("createTable", expected, actual);

        //dell table
        in.reset();
        out.reset();
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("dropTable|" + testTable2);
        Main.main(new String[0]);


    }


    @Test

    public void dropDatabase() {
        //given
        String testDatabaseName4 = "testDatabaseName4";
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("createDatabase|" + testDatabaseName4);
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("dropDatabase|" + testDatabaseName4);
        in.add("exit");
        //then
        Main.main(new String[0]);


        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the" +
                " password in a format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|" + databaseName + "|" + userName + "|" + password + newLine +
                "Opened database: " + "\"" + databaseName + "\"" + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "createDatabase|" + testDatabaseName4 + newLine +
                "The database: " + testDatabaseName4 + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "connect|" + databaseName + "|" + userName + "|" + password + newLine +
                "Opened database: " + "\"" + databaseName + "\"" + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "dropDatabase|" + testDatabaseName4 + newLine +
                "Database  " + testDatabaseName4 + " deleted successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "exit" + newLine +
                "See you soon!" + newLine;
        assertEquals("dropDatabase", expected, actual);

        //TODO получить список баз данных
        // TODO удалитьбазу данных
    }

    @Test

    public void disconnectOfDatabase() {
        //given
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("disconnect|" + databaseName);
        in.add("exit");
        //then
        Main.main(new String[0]);


        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in " +
                "a format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|" + databaseName + "|" + userName + "|" + password + newLine +
                "Opened database: " + "\"" + databaseName + "\"" + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "disconnect|" + databaseName + newLine +
                "Disconnect of database postgres is successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "exit" + newLine +
                "See you soon!" + newLine;
        assertEquals("disconnectOfDatabase", expected, actual);

        //TODO получить список баз данных
        // TODO удалитьбазу данных
    }

    // @Ignore("сделаю после модульного теста insert")
    @Test
    public void testFindAfterConnect_withData() {

        in.add("connect|" + testDatabaseName + "|" + userName + "|" + password);
        in.add("clear|" + testTable);
        in.add("insert|" + testTable + "|id|13|name|Stiven|password|*****");
        in.add("insert|" + testTable + "|id|14|name|Eva|password|+++++");
        in.add("find|" + testTable);
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("testFindAfterConnect_withData", "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a " +
                "format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|" + testDatabaseName + "|" + userName + "|" + password + newLine +
                "Opened database: \"" + testDatabaseName + "\" successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "clear|testTable" + newLine +
                "The table: \"testTable\"  is cleared successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "insert|testTable|id|13|name|Stiven|password|*****" + newLine +
                "The record {names:[id, name, password], values:[13, Stiven, *****]} was successfully created in the " +
                "table' by testTable'." + newLine +
                "enter please command or help command for a help call" + newLine +
                "insert|testTable|id|14|name|Eva|password|+++++" + newLine +
                "The record {names:[id, name, password], values:[14, Eva, +++++]} was successfully created in the " +
                "table' by testTable'." + newLine +
                "enter please command or help command for a help call" + newLine +
                "find|testTable" + newLine +
                "•+--------------------------------------------------" + newLine +
                "•+ id + name + password + " + newLine +
                "•+--------------------------------------------------" + newLine +
                "•+ 13 + Stiven + ***** + " + newLine +
                "•+--------------------------------------------------" + newLine +
                "•+ 14 + Eva + +++++ + " + newLine +
                "•+--------------------------------------------------" + newLine +
                "enter please command or help command for a help call" + newLine +
                "exit" + newLine +
                "See you soon!" + newLine, getData());
    }

    @Test
    public void testClearWithError() {
        // given
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("clear|sadfasd|fsf|fdsf");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("testClearWithError", "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a " +
                "format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|" + databaseName + "|" + userName + "|" + password + newLine +
                "Opened database: " + "\"" + databaseName + "\"" + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "clear|sadfasd|fsf|fdsf" + newLine +
                "Failure! for the reason:Team format clear|tableName, and you have entered: clear|sadfasd|fsf|fdsf" + newLine +
                "Repeat attempt." + newLine, getData());
    }

    @Test
    public void testInsertWithErrors() {
        // given
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("insert|user|error");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("testInsertWithErrors", "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in " +
                "a format: connect|databaseName|userName|password" + newLine +
                "or help command for a help call" + newLine +
                "connect|postgres|postgres|1111" + newLine +
                "Opened database: " + "\"" + databaseName + "\"" + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "insert|user|error" + newLine +
                "Failure! for the reason:Shall be even the number of parameters in a format'create |tableName| column1 " +
                "|value1| of column2 |value2|... |columnN| valueN',and you sent: 'insert|user|error'" + newLine +
                "Repeat attempt." + newLine, getData());
    }


    private String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Before
    public void resetOut() throws IOException {
        out.reset();
    }

    @AfterClass

    public static void dropDatabaseAfter() throws IOException {
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("giveAccess|" + testDatabaseName + "|" + userName);
        in.add("exit");
        Main.main(new String[0]);
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("disconnect|" + testDatabaseName);
        Main.main(new String[0]);
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("dropDatabase|" + testDatabaseName); //TODO dell
        in.add("exit");
        Main.main(new String[0]);
        System.err.println(out.toString());


    }
}
