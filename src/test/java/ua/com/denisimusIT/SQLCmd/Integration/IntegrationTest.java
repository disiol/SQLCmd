package ua.com.denisimusIT.SQLCmd.Integration;

import org.junit.BeforeClass;
import org.junit.*;
import ua.com.denisimusIT.SQLCmd.controller.Main;

import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    private String newLine = System.lineSeparator();
    private static ConfigurableInputStream in;
    private static LogOutputStream out;

    @BeforeClass
    public static void setup() {
        in = new ConfigurableInputStream();
        out = new LogOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test

    public void testExit() {

        //given
        in.add("Help");
        in.add("Exit");
        //then
        Main.main(new String[0]);
        //wen
        String actual = out.getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database , enter please a database name, " +
                "user name and the password in a format: connect|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                "You cannot use a command '' be not connected by means of " +
                "a command yet connect|databaseName|userName|password" + newLine +
                "enter please command or help command for a help call" + newLine +
                "See you soon!" + newLine;
        assertEquals("testExit", expected, actual);
    }

    @Test

    public void testHelp() {

        //given
        in.add("Help");
        //then
        Main.main(new String[0]);
        //wen
        String actual = out.getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database , enter please a database name, " +
                "user name and the password in a format: connect|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                "You cannot use a command '' be not connected by means of " +
                "a command yet connect|databaseName|userName|password" + newLine +
                "enter please command or help command for a help call" + newLine +
                "See you soon!" + newLine;
        assertEquals("testExit", expected, actual);
    }
}
