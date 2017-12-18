package ua.com.denisimusIT.SQLCmd.Integration;

import org.junit.BeforeClass;
import org.junit.*;
import ua.com.denisimusIT.SQLCmd.controller.Main;

import java.io.ByteArrayOutputStream;
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


    @Before
    public void setup() {
        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();
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
                "For connectToDatabase to database , enter please a database name, " +
                "user name and the password in a format: connectToDatabase|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                "See you soon!" + newLine;
        assertEquals("testExit", expected, actual);
    }

    @Test

    public void testHelp() {

        //given
        in.add("help");

        //then
        Main.main(new String[0]);
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connectToDatabase to database , enter please a database name, user name and the password in a format: " +
                "connectToDatabase|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                "The existing command:" + newLine +
                "\tconnect|databaseName|userName|password" + newLine +
                "\t\tfor connection to the database with which we will work" + newLine +
                "\tlist" + newLine +
                "\t\tfor connection to the database with which we will work" + newLine +
                "\tclear|tableName" + newLine +
                "\t\tfor cleaning of all table" + newLine +
                "\tcreate|tableName|column1|value1|column2|value2|...|columnN|valueN" + newLine +
                "\t\tfor creation of record in the table" + newLine +
                "\tfind|tableName" + newLine +
                "\t\tfor receiving contents of the table 'tableName'" + newLine +
                "\thelp" + newLine +
                "\t\tfor an output of this list to the screen" + newLine +
                "\texit" + newLine +
                "\t\tfor an output from the program" + newLine +
                "enter please command or help command for a help call" + newLine +
                "See you soon!" + newLine;
        assertEquals("testHelp", expected, actual);
    }


    @Test

    public void testConnect() {

        //given
        in.add("connect|" + databaseName + "|" + userName + "|" + password);
        //in.add("exit");
        //then
        Main.main(new String[0]);
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connectToDatabase to database , enter please a database name, user name and the password in a format: " +
                "connectToDatabase|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                "Opened database: " + databaseName + " successfully" + newLine +
                "enter please command or help command for a help call" + newLine +
                "See you soon!" + newLine;
        assertEquals("testConnect", expected, actual);
    }

    @Test
    public void testConnectExceptionParameters_3() throws Exception {

        //given
        in.add("connect|" + databaseName + "|" + userName);
        //then
        Main.main(new String[0]);
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)"+ newLine +
                "For connectToDatabase to database , enter please a database name, user name and the password in a format: " +
                "connectToDatabase|database|username|password"+ newLine +
                "or help command for a help call"+ newLine +
                "Failure! for the reason:The number of parameters partitioned by the character '|' is incorrect, it is " +
                "expected  4, but is: 3"+ newLine +
                "Repeat attempt."+ newLine ;
        assertEquals("testConnectExceptionParameters_3", expected, actual);//given

    }

    @Test
    public void testConnectExceptionDatabase() throws Exception {

        //given
        in.add("connect|" + "_" + "|" + userName + "|" + password);
        //in.add("exit");
        //then
        Main.main(new String[0]);
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)"+ newLine +
                "For connectToDatabase to database , enter please a database name, user name and the password in a format: " +
                "connectToDatabase|database|username|password"+ newLine +
                "or help command for a help call"+ newLine +
                "Failure! for the reason:Cant get connection for model:_ user:postgres FATAL: database \"_\" does not exist"+ newLine +
                "Repeat attempt." + newLine;
        assertEquals("testConnectExceptionDatabase", expected, actual);//given

    }

    @Test
    public void testConnectExceptionPassword() throws Exception {

        //given
        in.add("connect|" + databaseName + "|" + userName + "|" + "1");
        //in.add("exit");
        //then
        Main.main(new String[0]);
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)"+ newLine +
                "For connectToDatabase to database , enter please a database name, user name and the password in a " +
                "format: connectToDatabase|database|username|password"+ newLine +
                "or help command for a help call"+ newLine +
                "Failure! for the reason:Cant get connection for model:postgres user:postgres FATAL: " +
                "password authentication failed for user \"postgres\""+ newLine +
                "Repeat attempt." + newLine;
        assertEquals("testConnectExceptionPassword", expected, actual);

    }


    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }
}
