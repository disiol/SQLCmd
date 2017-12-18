package ua.com.denisimusIT.SQLCmd.controller.command;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class ListOfTablesNames implements Command {
    private static final String NEWLINE = System.lineSeparator();

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
        view.write("List of tablesNames " + NEWLINE);
        view.write(tableNames);
    }
}
