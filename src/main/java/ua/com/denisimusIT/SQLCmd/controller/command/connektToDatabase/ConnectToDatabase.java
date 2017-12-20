package ua.com.denisimusIT.SQLCmd.controller.command.connektToDatabase;

import ua.com.denisimusIT.SQLCmd.controller.command.Command;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class ConnectToDatabase implements Command {
    private static String COMMAND_SAMPLE;


    private final View view;
    private final DatabaseManager manager;

    public ConnectToDatabase(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        String connect = format().substring(0, description().indexOf("|"));
        return command.startsWith(connect + "|");

    }

    @Override
    public void process(String command) {


        String[] data = command.split("\\|");
        if (data.length != count()) {
            throw new IllegalArgumentException(String.format("The number of parameters partitioned by the character '|' " +
                    "is incorrect, it is expected  %s, but is: %s", count(), data.length));
        }
        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];

        try {
            manager.connectToDatabase(databaseName, userName, password);
        } catch (Exception e) {
            printError(e);
        }
        view.write("Opened database: " + databaseName + " successfully");


    }

    @Override
    public String format() {

        return "connect|databaseName|userName|password";
    }

    @Override
    public String description() {

        return "for connection to the database with which we will work";
    }


    private int count() {
        return description().split("\\|").length;
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

