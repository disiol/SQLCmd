package ua.com.denisimusIT.SQLCmd.controller.command;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class СurrentDatabaseName implements Command {
    private View view;
    private DatabaseManager manager;

    public СurrentDatabaseName(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
       String currentDatabaseName = format();
        return command.equals(currentDatabaseName);
    }

    @Override
    public void process(String command) {
        String message = manager.currentDatabase().get(0);
        view.write(message);

    }

    @Override
    public String description() {
        return "shows current database name";
    }

    @Override
    public String format() {
        return "СurrentDatabaseName";
    }
    //TODO
    //current_database
    //  ------------------
    //   [postgres]

}
