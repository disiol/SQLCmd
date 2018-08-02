package ua.com.denisimusIT.SQLCmd.controller;

import ua.com.denisimusIT.SQLCmd.controller.command.*;
import ua.com.denisimusIT.SQLCmd.controller.command.connektToDatabase.*;
import ua.com.denisimusIT.SQLCmd.controller.command.exit.*;
import ua.com.denisimusIT.SQLCmd.controller.command.exit.Exeption.ExitException;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;


public class MainController {
    private static final String NEWLINE = System.lineSeparator();
    private final Command[] commands;
    private View view;
    private String connectFormat;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        Help help = new Help(view);
        connectFormat = new ConnectToDatabase(view, manager).format();

        this.commands = new Command[]{
                new Exit(view),
                help,
                new ConnectToDatabase(view, manager),
                new CreateDatabase(view, manager),
                new GifAccessToDatabase(view, manager),
                new ListOfDatabasesNames(view, manager),
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
        help.setCommands(commands);
    }

    public void run() {
        try {
            doWork();
        } catch (ExitException e) {
            // do nothing
        }

    }

    private void doWork() {
        view.write("Welcome to SQLCmd! =)");
        view.write("For connect to database to database , enter please a database name, user name and the password in a format: " +
                connectFormat + NEWLINE + "or help command for a help call");


        while (true) {
            try {
                String input = view.read();
                if (input == null) {
                    new Exit(view).process(input);//nul if close application
                }

                for (Command command : commands) {
                    if (command.canProcess(input)) {
                        command.process(input);
                        break;
                    }
                }
            } catch (Exception e) {
                if (e instanceof ExitException) {
                    throw e;
                }
                //e.printStackTrace();
                printError(e);

            }
            view.write("enter please command or help command for a help call");
        }


    }


    private void printError(Exception e) {
        String message = e.getMessage();
//        Throwable cause = e.getCause();
//        if (cause != null) {
//            message += " " + cause.getMessage();
//        }
        view.write("Failure! for the reason: " + message);
        view.write("Repeat attempt.");
    }

}
