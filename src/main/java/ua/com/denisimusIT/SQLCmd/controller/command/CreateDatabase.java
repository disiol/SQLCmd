package ua.com.denisimusIT.SQLCmd.controller.command;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class CreateDatabase implements Command {

    private String newLine = System.lineSeparator();


    private View view;
    private DatabaseManager manager;

    public CreateDatabase(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("createDatabase" +"|");
    }

    @Override
    public void process(String command) {

        String[] data = command.split("\\|");
        if (data.length != count()) {
            throw new IllegalArgumentException(String.format("The number of parameters partitioned by the character '|' " +
                    "is incorrect, it is expected  %s, but is: %s", count(), data.length));
        }
        String databaseName = data[1];

        manager.createDatabase("\"" + databaseName + "\"" );
        view.write("Database created " + databaseName  + " successfully");


    }

    @Override
    public String description() {
        return "created database";
    }

    @Override
    public String format() {
        return "createDatabase|DatabaseName";
    }

    private int count() {
        return format().split("\\|").length;
    }

}
