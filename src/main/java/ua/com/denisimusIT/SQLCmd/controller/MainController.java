package ua.com.denisimusIT.SQLCmd.controller;

import ua.com.denisimusIT.SQLCmd.controller.commands.Help;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class MainController {
    private static final String NEWLINE = System.lineSeparator();
    private static final String GREETING = "Welcome to SQLCmd! =)";
    private View view;
    private DatabaseManager manager;
    private Help help = new Help();

    public MainController(View view, DatabaseManager databaseManager) {
        this.view = view;
        this.manager = databaseManager;
    }

    public void run() {
        connectToDb();

        while (true) {
            view.write("enter the command or help command for a help call");
            String command = view.read();
            if (command.equals("list")) {
                listOfDb();
            }else if (command.equals("help")){
                view.write(help.toString());
            }else if (command.startsWith("find|")){
                doFind(command);
            }else if(command.equals("exit")){
                view.write("See you soon!");
                System.exit(0);
            }
        }
    }

    private void doFind(String command) {

    }


    private void connectToDb() {
        while (true) {
            try {
                view.write("Welcome to SQLCmd! =)");
                view.write("Enter, please a database name, user name and the password in a format: database|userName|password");
                String string = view.read();
                String[] data = string.split("\\|");
                String databaseName = data[0];
                String userName = data[1];
                String password = data[2];

                if (data.length != 3) {
                    throw new IllegalArgumentException("The number of parameters partitioned by the character '|' " +
                                                       "is incorrect, it is expected 3, but is: " + data.length);
                }
                manager.connect(databaseName, userName, password);
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

    private void listOfDb() {
        String tableNames = manager.getTableNames().toString();
        view.write("Table names: ");
        view.write(tableNames);
    }

}
