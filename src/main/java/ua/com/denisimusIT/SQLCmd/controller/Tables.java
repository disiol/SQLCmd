package ua.com.denisimusIT.SQLCmd.controller;

import ua.com.denisimusIT.SQLCmd.controller.commands.Command;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.Console;
import ua.com.denisimusIT.SQLCmd.view.View;

public class Tables implements Command {
    private final DatabaseManager manager;
    private View view = new Console();

    public Tables(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("tables");
    }

    @Override
    public void Process(String command) {
        String tableNames = manager.getTableNames().toString();
        view.write("Table names: ");
        view.write(tableNames);
    }
}
