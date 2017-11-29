package ua.com.denisimusIT.SQLCmd.controller;

import ua.com.denisimusIT.SQLCmd.controller.commands.Command;
import ua.com.denisimusIT.SQLCmd.controller.commands.Exit;
import ua.com.denisimusIT.SQLCmd.controller.commands.Help;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;


public class MainController {
    private static final String NEWLINE = System.lineSeparator();
    private final Command[] commands;
    private View view;
    private DatabaseManager manager;


    public MainController(View view, DatabaseManager databaseManager) {
        this.view = view;
        this.manager = databaseManager;
        this.commands = new Command[]{new Exit(view), new Help(view), new Tables(view, manager), new Find(view, manager)
                , new UnsupportedCommand(view)};
    }

    public void run() {
        view.write("Welcome to SQLCmd! =)");
        view.write("For connect to database , enter please a database name, user name and the password in a format: " +
                   "connect|database|username|password or help commands for a help call");


        connectToDb();

        while (true) {
            String input = view.read();

            for (Command command : commands) {
                if (command.canProcess(input)) {
                    command.Process(input);
                    break;
                }
            }
        }
    }


    private void connectToDb() {
        while (true) {
            try {
                String string = view.read();
                String[] data = string.split("\\|");
                String databaseName = data[0];
                String userName = data[1];
                String password = data[2];

                if (data.length != 3) { //TODO magic number
                    throw new IllegalArgumentException("The number of parameters partitioned by the character '|' " +
                            "is incorrect, it is expected 3, but is: " + data.length);
                }
                manager.connect(databaseName, userName, password);
                view.write("Opened database: " + databaseName + " successfully");
                break;

            } catch (Exception e) {
                String message = e.getMessage();
                if (e.getCause() != null) {
                    message += " " + e.getCause().getMessage();
                }
                view.write("Failure! For the reason : " + message);
                view.write("Repeat attempt please");
            }
        }
    }


}
