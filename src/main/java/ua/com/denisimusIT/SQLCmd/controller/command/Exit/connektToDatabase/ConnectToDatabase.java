package ua.com.denisimusIT.SQLCmd.controller.command.Exit.connektToDatabase;

import ua.com.denisimusIT.SQLCmd.controller.command.Command;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class ConnectToDatabase implements Command {
    private static String COMMAND_SAMPLE = "connectToDatabase|sql|postgres|1111";


    private final View view;
    private final DatabaseManager manager;

    public ConnectToDatabase(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect" + "|");
    }

    @Override
    public void process(String command) {


        try {
            String[] data = command.split("\\|");
            if (data.length != count()) {
                throw new IllegalArgumentException(String.format("The number of parameters partitioned by the character '|' " +
                        "is incorrect, it is expected %s, but is: %s", count(), data.length));
            }
            String databaseName = data[1];
            String userName = data[2];
            String password = data[3];

            manager.connectToDatabase(databaseName, userName, password);
            view.write("Opened database: " + databaseName + " successfully");
        } catch (IllegalArgumentException e) {
           printError(e);
        }


    }


    private int count() {
        return COMMAND_SAMPLE.split("\\|").length;
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

