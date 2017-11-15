package ua.com.denisimusIT.SQLCmd.controller;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

public class MainControllerTest {
    private static final  String NEWLINE = System.lineSeparator();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();




    @Test
    public void MainControllerTest() {
        //TODO
        String [] testAreae;
        MainController mainController = new MainController();
        System.setOut(new PrintStream(outContent));
        String expected = "Welcome to SQLCmd! =)" + NEWLINE +
                "For connection to the database you enter the command : " + NEWLINE +
                "connect | database | username | password " +
                "or help command for a help call";
        String actual = outContent.toString();
        assertEquals("MainControllerTest", expected, actual);

    }
}
