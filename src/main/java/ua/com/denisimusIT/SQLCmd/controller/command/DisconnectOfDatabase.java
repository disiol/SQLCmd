package ua.com.denisimusIT.SQLCmd.controller.command;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class DisconnectOfDatabase implements Command {
    private View view;
    private DatabaseManager manager;

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
        //TODO chek
        manager.disconnectOfDatabase("\"" + databaseName + "\"");
    }

    @Override
    public String description() {
        return "Disconnect of database";
    }

    @Override
    public String format() {
        return "disconnect|database|User";
    }
}
