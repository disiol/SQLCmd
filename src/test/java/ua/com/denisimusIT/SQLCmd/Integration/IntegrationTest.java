package ua.com.denisimusIT.SQLCmd.Integration;

import org.junit.BeforeClass;
import org.junit.*;
import ua.com.denisimusIT.SQLCmd.controller.Main;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    private String newLine = System.lineSeparator();
    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;


    private final String databaseName = "postgres";
    private final String userName = "postgres";
    private final String password = "1111";


    private static final String TEST_TABLE_NAME = "testTable";
    private static final String TEST_database_NAME = "testDatabase";


    @Before
    public void setup() {
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));

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
                "For connect to database to database , enter please a database name, " +
                "user name and the password in a format: connect|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                "See you soon!" + newLine;
        assertEquals("testExit", expected, actual);
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
                "For connect to database to database , enter please a database name, user name and the password in " +
                "a format: connect|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                "The existing command: " + newLine +
                "\texit" + newLine +
                "\t\tfor an output from the program" + newLine +
                "\thelp" + newLine +
                "\t\tfor an output of this list to the screen" + newLine +
                "\tconnect|databaseName|userName|password" + newLine +
                "\t\tfor connection to the database with which we will work" + newLine +
                "\tcreateDatabase|DatabaseName" + newLine +
                "\t\tcreated database" + newLine +
                "\tshows the list of tables" + newLine +
                "\t\ttables" + newLine +
                "\tfind|tableName " + newLine +
                "\t\tfor receiving contents of the table tableName" + newLine +
                "\tdropDatabase|DatabaseName" +newLine+
                "\t\tDelete database"+ newLine+
                "enter please command or help command for a help call" + newLine +
                "See you soon!" + newLine;
        assertEquals("testHelpBeforeConnect", expected, actual);
    }


    @Test

    public void testConnect() {

        //given
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        //then
        Main.main(new String[0]);
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a format: " +
                "connect|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                "Opened database: " + databaseName + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
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
                "a format: connect|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                "Opened database: postgres successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "The existing command: " + newLine +
                "\texit" + newLine +
                "\t\tfor an output from the program" + newLine +
                "\thelp" + newLine +
                "\t\tfor an output of this list to the screen" + newLine +
                "\tconnect|databaseName|userName|password" + newLine +
                "\t\tfor connection to the database with which we will work" + newLine +
                "\tcreateDatabase|DatabaseName" + newLine +
                "\t\tcreated database" + newLine +
                "\tshows the list of tables" + newLine +
                "\t\ttables" + newLine +
                "\tfind|tableName " + newLine +
                "\t\tfor receiving contents of the table tableName" + newLine +
                "\tdropDatabase|DatabaseName" + newLine +
                "\t\tDelete database" + newLine +
                "enter please command or help command for a help call" + newLine +
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
                "For connect to database to database , enter please a database name, user name and the password in a format: " +
                "connect|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                "Failure! for the reason:The number of parameters partitioned by the character '|' is incorrect, it is " +
                "expected  4, but is: 3" + newLine +
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
                "connect|database|username|password" + newLine +
                "or help command for a help call" + newLine +
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
                "format: connect|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                "Failure! for the reason:Cant get connection for model:postgres user:postgres FATAL: " +
                "password authentication failed for user \"" + userName + "\"" + newLine +
                "Repeat attempt." + newLine;
        assertEquals("testConnectExceptionPassword", expected, actual);

    }


    @Test
    public void testConnectExceptionUser() throws Exception {

        //given
        in.add("connect|" + databaseName + "|" + "_" + "|" + password);
        // in.add("exit");
        //then
        Main.main(new String[0]);
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a format: " +
                "connect|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                "Failure! for the reason:Cant get connection for model:postgres user:_ FATAL: password authentication failed " +
                "for user \"_\"" + newLine +
                "Repeat attempt." + newLine;
        assertEquals("testConnectExceptionPassword", expected, actual);

    }


    @Test

    public void createDatabase() {
        //given
        String testDatabaseName2 = "testDatabaseName2";
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("createDatabase|" + testDatabaseName2);
        in.add("exit");
        //then
        Main.main(new String[0]);

        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a format: " +
                "connect|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                "Opened database: " + databaseName + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "Database created " + testDatabaseName2 + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine
                + "See you soon!" + newLine;
        assertEquals("createDatabase", expected, actual);

        //TODO получить список баз данных
        // TODO удалитьбазу данных
    }


    @Test

    public void dropDatabase() {
        //given
        String testDatabaseName2 = "testDatabaseName2";
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("createDatabase|" + testDatabaseName2);
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        in.add("dropDatabase|" + "testDatabaseName2");
        in.add("exit");
        //then
        Main.main(new String[0]);

        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database to database , enter please a database name, user name and the password in a format: " +
                "connect|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                //connect
                "Opened database: " + databaseName + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                //createDatabase
                "Database created " + testDatabaseName2 + " successfully" + newLine +
                "enter please command or help command for a help call\n" +
                "Opened database: postgres successfully\n" +
                "enter please command or help command for a help call" + newLine +
                //drop database
                "Database  " + testDatabaseName2 + " deleted successfully" + newLine +
                "enter please command or help command for a help call" + newLine
                + "See you soon!" + newLine;
        assertEquals("dropDatabase", expected, actual);

        //TODO получить список баз данных
        // TODO удалитьбазу данных
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
}
