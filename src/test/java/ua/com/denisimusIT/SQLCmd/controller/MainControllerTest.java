package ua.com.denisimusIT.SQLCmd.controller;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

public class MainControllerTest {
    final String newline = System.lineSeparator();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    MainController mainController = new MainController();

    @Test
    public void MainControllerTest() {
        System.setOut(new PrintStream(outContent));

        String expected = "Welcome to SQLCmd! =)" + newline +
                "For connection to the bazedanykh you veddit the connect | database | username | password " +
                "or help command for a help call";
        String actual = outContent.toString();
        assertEquals("MainControllerTest", expected, actual);

    }
}
