package ua.com.denisimusIT.SQLCmd.controller;

import ua.com.denisimusIT.SQLCmd.controller.commands.Command;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class Connect implements Command {
    //TODO
    private final View view;
    private final DatabaseManager manager;

    public Connect(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void Process(String command) {

                try {
                    String[] data = command.split("\\|");
                    if (data.length != 4) { //TODO magic number
                        throw new IllegalArgumentException("The number of parameters partitioned by the character '|' " +
                                "is incorrect, it is expected 4, but is: " + data.length);
                    }
                    String databaseName = data[1];
                    String userName = data[2];
                    String password = data[3];

                    manager.connect(databaseName, userName, password);
                    view.write("Opened database: " + databaseName + " successfully");

                } catch (Exception e) {
                    printError(e);

                }





    }

    private void printError(Exception e) {  //TODO вынести
        String message = e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            message += " " +  cause.getMessage();
        }
        view.write("Failure! For the reason : " + message);
        view.write("Repeat attempt please");
    }
}

