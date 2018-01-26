package ua.com.denisimusIT.SQLCmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.denisimusIT.SQLCmd.controller.command.connektToDatabase.ConnectToDatabase;
import ua.com.denisimusIT.SQLCmd.controller.command.connektToDatabase.IsConnect;
import ua.com.denisimusIT.SQLCmd.controller.command.exit.Exit;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HelpTest {
    private View view;
    private Help help;
    private Command[] commands;
    private String newLine = System.lineSeparator();
    private DatabaseManager manager;


    @Before
    public void Help() {
        view = mock(View.class);
        manager = mock(DatabaseManager.class);
        help = new Help(view);

        this.commands = new Command[]{
                new Exit(view),
                help,
                new ConnectToDatabase(view, manager),
                new CreateDatabase(view, manager),
                new GifAccessToDatabase(view, manager),
                new DropDatabase(view, manager),
                new IsConnect(view, manager),
                new ListOfTablesNames(view, manager),
                new ContentsOfTheTable(view, manager),
                new CreateNewTable(view, manager),
                new InsertALineIntoTheTable(view, manager),
                new ClearTable(view, manager),
                new DropTable(view, manager),
                new DisconnectOfDatabase(view, manager),
                new UnsupportedCommand(view)
        };
    }


    @Test
    public void canProcessTrue() {
        boolean canProcess = help.canProcess("help");
        assertTrue("canProcessTrue", canProcess);
    }

    @Test
    public void canProcessFalse() {
        boolean canProcess = help.canProcess("sadfa");
        assertFalse("canProcessFalse", canProcess);
    }


    @Test
    public void description() {
        view.write(help.description());
        verify(view).write("for an output of this list to the screen");

    }

    @Test
    public void format() {
        view.write(help.format());
        verify(view).write("help");
    }


    @Test
    public void process() {
        //wen
        help.setCommands(commands);

        help.process(view.read());


        shouldPrint("[The existing command: " + newLine +
                ", \texit, \t\tfor an output from the program" + newLine +
                ", \thelp, \t\tfor an output of this list to the screen" + newLine +
                ", \tconnect|databaseName|userName|password, \t\tfor connection to the database with which we " +
                "will work" + newLine +
                ", \tcreateDatabase|DatabaseName, \t\tcreated database" + newLine +
                ", \tgiveAccess|databaseName|userName, \t\tGive access user to database" + newLine +
                ", \tdropDatabase|DatabaseName, \t\tDelete database" + newLine +
                ", \ttables, \t\tshows the list of tables" + newLine +
                ", \tfind|tableName, \t\tfor receiving contents of the table tableName" + newLine +
                ", \tcreate|tableName|column1 column type, column2 column type,...,columnN column type, \t\tFor " +
                "create table:" + newLine +
                ", \tinsert|tableName|column1|value1|column2|value2| ... |columnN | valueN, \t\tCommand for an " +
                "insertion of one line in the given table " + newLine +
                "  \t• where: tableName - a table name" + newLine +
                "  \t• column1 - a name of the first column of record" + newLine +
                "  \t• value1 - value of the first column of record" + newLine +
                "  \t• column2 - a name of the second column of record" + newLine +
                "  \t• value2 - value of the second column of record" + newLine +
                "  \t• columnN - the record column name n-go" + newLine +
                "  \t• valueN - n-go value of a column of record" + newLine +
                ", \tclear|tableName, \t\tfor cleaning of all table" + newLine +
                ", \tdropTable|tableName, \t\tDelete table" + newLine +
                ", \tdisconnect|databaseName, \t\tDisconnect of database" + newLine +
                "]", "process");

    }

    private void shouldPrint(String expected, String message) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(message, expected, captor.getAllValues().toString());
    }

}

