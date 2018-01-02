package ua.com.denisimusIT.SQLCmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.controller.command.exit.Exeption.ExitException;
import ua.com.denisimusIT.SQLCmd.controller.command.exit.Exit;
import ua.com.denisimusIT.SQLCmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ExitTest {


    View view;
    Command command;


    @Before

    public void setup() {

        view = mock(View.class);
        command = new Exit(view);

    }


    @Test
    public void canProcessTest() {
        //wen
        boolean exit = command.canProcess("exit");

        //then
        assertTrue("canProcessTestTrue", exit);

        boolean exi = command.canProcess("exi");
        assertFalse("canProcessTest", exi);
    }


    @Test
    public void processTest() {
        try {
            command.process("exit");
            fail("Exit exception");
        } catch (ExitException e) {

            //do nothing
        }


        verify(view).write("See you soon!");
    }

    @Test
    public void descriptionTest() {
        //wen
        view.write(command.description());
        //TODO

        //then
        verify(view).write("for an output from the program");
    }

    @Test
    public void formatTest() {
        //TODO
        view.write(command.format());
        verify(view).write("exit");
    }

}
