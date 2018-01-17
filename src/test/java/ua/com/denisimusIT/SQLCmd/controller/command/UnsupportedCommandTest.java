package ua.com.denisimusIT.SQLCmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.view.View;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

public class UnsupportedCommandTest {
    View view;
    Command unsupportedCommand;

    @Before
    public void setup() {
        view = mock(View.class);
        unsupportedCommand = new UnsupportedCommand(view);
    }

    @Test
    public void canProcessTrueTest() {
        //wen
        boolean canProcess = unsupportedCommand.canProcess("ffff");

        //then
        assertTrue("canProcessTrueTest", canProcess);
    }


    @Test
    public void processTest() {
        String command = "ygygiygi";
        String message = String.format("Unsupported command:%s", command);
        //wen
        unsupportedCommand.canProcess(command);
        unsupportedCommand.process(command);
        //then
        verify(view).write(message);

    }

    @Test
    public void descriptionTest() {
        assertNull("descriptionTest", unsupportedCommand.description());
    }

    @Test
    public void formatTest() {
        assertNull("descriptionTest", unsupportedCommand.format());

    }
}
