package ua.com.denisimusIT.SQLCmd.controller;

import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.Console;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.junit.runner.JUnitCore.main;

public class MainControllerTest {
    private  final  MainController mainController = new MainController(new Console(),new PostgresDatabaseManager());
    private static final  String NEWLINE = System.lineSeparator();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayInputStream  byteArrayInputStream = new ByteArrayInputStream(NEWLINE.getBytes());



    @Test
    public void MainControllerTest() {
        //TODO
        //given
        System.setIn(byteArrayInputStream);
        System.setOut(new PrintStream(outContent));

      
        
        //then
        Main.main(new String[0]);
        //wen
        String expected = "Welcome to SQLCmd! =)"+NEWLINE +
                "For connect to database , enter please a database name, user name and the password in a format: connect|database|username|password"+NEWLINE +
                "or help command for a help call"+NEWLINE +
                "You cannot use a command '' be not connected by means of a command yet connect|databaseName|userName|password"+NEWLINE +
                "enter please command or help command for a help call" + NEWLINE;
        String actual = outContent.toString();
        assertEquals("MainControllerTest", expected, actual);




    }
}
