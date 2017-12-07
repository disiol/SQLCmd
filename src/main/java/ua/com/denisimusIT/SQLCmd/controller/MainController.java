package ua.com.denisimusIT.SQLCmd.controller;

import ua.com.denisimusIT.SQLCmd.controller.commands.*;
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
                new Connect(view, manager),
                new IsConnected(view, manager),
                new Tables(view, manager),
                new Find(view, manager)
                , new UnsupportedCommand(view)
        };
    }

    public void run() {
        view.write("Welcome to SQLCmd! =)");
        view.write("For connect to database , enter please a database name, user name and the password in a format: " +
                "connect|database|username|password" + NEWLINE + "or help command for a help call");


        while (true) {
            String input = view.read();

            for (Command command : commands) {
                if (command.canProcess(input)) {
                    command.Process(input);
                    break;
                }
            }
            view.write("enter please command or help command for a help call");
        }


    }


}
