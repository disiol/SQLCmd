package ua.com.denisimusIT.SQLCmd.controller.commands;

import ua.com.denisimusIT.SQLCmd.controller.commands.Command;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.Console;
import ua.com.denisimusIT.SQLCmd.view.View;

public class ListOfTablesNames implements Command {
    private final DatabaseManager manager;
    private View view;

    public ListOfTablesNames(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("tables");
    }

    @Override
    public void process(String command) {
        String tableNames = manager.getTableNames().toString();
        view.write("Tables names: ");
        view.write(tableNames);
    }
}
