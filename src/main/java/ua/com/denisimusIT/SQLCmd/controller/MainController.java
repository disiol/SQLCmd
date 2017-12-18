package ua.com.denisimusIT.SQLCmd.controller;

import ua.com.denisimusIT.SQLCmd.controller.command.*;
import ua.com.denisimusIT.SQLCmd.controller.command.Exit.Exit;
import ua.com.denisimusIT.SQLCmd.controller.command.Exit.Exeption.ExitException;
import ua.com.denisimusIT.SQLCmd.controller.command.Exit.connektToDatabase.ConnectToDatabase;
import ua.com.denisimusIT.SQLCmd.controller.command.Exit.connektToDatabase.IsConnected;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;


public class MainController {
    private static final String NEWLINE = System.lineSeparator();
    private final Command[] commands;
    private View view;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.commands = new Command[]{
                new Exit(view),
                new Help(view),
                new ConnectToDatabase(view, manager),
                new IsConnected(view, manager),
                new ListOfTablesNames(view, manager),
                new ContentsOfTheTable(view, manager)
                , new UnsupportedCommand(view)
        };
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
        view.write("For connectToDatabase to database , enter please a database name, user name and the password in a format: " +
                "connectToDatabase|database|username|password" + NEWLINE + "or help command for a help call");


        try {
            while (true) {
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
                view.write("enter please command or help command for a help call");
            }
        } catch (Exception e) {

            if(e.toString() == "ua.com.denisimusIT.SQLCmd.controller.command.Exit.Exeption.ExitException"){
                // do nothing
            }else {
                printError(e);
            }

        }

    }


    private void printError(Exception e) {
        String message = e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            message += " " + cause.getMessage();
        }
        view.write("Failure! for the reason:" + message);
        view.write("Repeat attempt.");
    }

}
