package ua.com.denisimusIT.SQLCmd.controller;

import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.Console;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

public class MainControllerTest {
    private static final  String NEWLINE = System.lineSeparator();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayInputStream  byteArrayInputStream = new ByteArrayInputStream("".getBytes());
    private  final  Main main = new Main();


    @Test
    public void MainControllerTest() {
        //TODO

        System.setIn(byteArrayInputStream);
        System.setOut(new PrintStream(outContent));
        String expected = "Welcome to SQLCmd! =)" + NEWLINE +
                "For connection to the database you enter the command : " + NEWLINE +
                "connect|database|username|password " +
                "or help command for a help call";
        String actual = outContent.toString();
        assertEquals("MainControllerTest", expected, actual);

    }
}
