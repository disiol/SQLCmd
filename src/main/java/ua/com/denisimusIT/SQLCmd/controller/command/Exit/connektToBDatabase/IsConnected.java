package ua.com.denisimusIT.SQLCmd.controller.command.Exit.connektToBDatabase;

import ua.com.denisimusIT.SQLCmd.controller.command.Command;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class IsConnected implements Command {
    private final View view;
    private final DatabaseManager manager;

    public IsConnected(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return !manager.isConnected();
    }

    @Override
    public void process(String command) {
        view.write(String.format("You cannot use a command '%s' be not connected by means of a command yet " +
                "connectToDatabase|databaseName|userName|password", command));
    }
}
