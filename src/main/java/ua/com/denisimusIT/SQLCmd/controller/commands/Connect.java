package ua.com.denisimusIT.SQLCmd.controller.commands;

import ua.com.denisimusIT.SQLCmd.controller.commands.Command;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class Connect implements Command {
    private static String COMMAND_SAMPLE = "connect|sql|postgres|1111";


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


                    String[] data = command.split("\\|");
                    if (data.length != count()) {
                        throw new IllegalArgumentException(String.format("The number of parameters partitioned by the character '|' " +
                                "is incorrect, it is expected %s, but is: %s" ,count(), data.length));
                    }
                    String databaseName = data[1];
                    String userName = data[2];
                    String password = data[3];

                    manager.connect(databaseName, userName, password);
                    view.write("Opened database: " + databaseName + " successfully");







    }


    private int count() {
        return COMMAND_SAMPLE.split("\\|").length;
    }



}

