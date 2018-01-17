package ua.com.denisimusIT.SQLCmd.controller.command.connectToDatabaseTest;

import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.controller.command.Command;
import ua.com.denisimusIT.SQLCmd.controller.command.connektToDatabase.IsConnect;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

public class IsConnectTest {
    View view;
    DatabaseManager databaseManager;
    Command isConnect;

    @Before
    public void setup() {
        view = mock(View.class);
        databaseManager = mock(DatabaseManager.class);
        isConnect = new IsConnect(view, databaseManager);
    }

    @Test
    public void canProcessTrueTest() {
        //wen
        boolean canProcess = isConnect.canProcess("ffff");

        //then
        assertTrue("canProcessTrueTest", canProcess);
    }

    @Test
    public void canProcessFalseTest() {
        when(databaseManager.isConnected()).thenReturn(true);
        boolean canProcess = isConnect.canProcess("ffff");
        assertFalse("canProcessFalseTest", canProcess);
    }

    @Test
    public void processTest() {
        String command = "help";
        String message = String.format("You cannot use a command '%s' be not connected by means of a command yet " +
                "connect|databaseName|userName|password", command);
        //wen
        isConnect.canProcess(command);
        isConnect.process(command);
        //then
        verify(view).write(message);

    }

    @Test
    public void descriptionTest() {
        assertNull("descriptionTest", isConnect.description());
    }

    @Test
    public void formatTest() {
        assertNull("descriptionTest", isConnect.format());

    }
}
