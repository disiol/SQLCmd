package ua.com.denisimusIT.SQLCmd.controller.command;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class DisconnectOfDatabase implements Command {
    private View view;
    private DatabaseManager manager;
    private String newLine = System.lineSeparator();


    public DisconnectOfDatabase(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("disconnect|");
    }

    @Override
    public void process(String command) {
        String[] dataCommand = command.split("\\|");
        String databaseName = dataCommand[1];
        int minQuantity = 2;
        if (dataCommand.length != minQuantity) {
            throw new IllegalArgumentException(String.format("The number of parameters partitioned by the character '|' " +
                    "is incorrect, it is expected  %s, but is: %s", minQuantity, dataCommand.length) + newLine +
                    "\texample: disconnect|database");
        }
        manager.disconnectOfDatabase("\"" + databaseName + "\"");
        //TODO exehen Database didon crate and masege

    }

    @Override
    public String description() {
        return "Disconnect of database";
    }

    @Override
    public String format() {
        return "disconnect|database";
    }
}
