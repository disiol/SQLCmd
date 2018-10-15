package ua.com.denisimusIT.SQLCmd.controller.command;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class DropDatabase implements Command {
    private String newLine = System.lineSeparator();


    private View view;
    private DatabaseManager manager;


    public DropDatabase(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("dropDatabase|");
    }

    @Override
    public void process(String command) {
        // TODO а если юзер случайно ввел команду? Может переспросить его?
        String[] data = command.split("\\|");
        if (data.length != count()) {
            throw new IllegalArgumentException(String.format("The number of parameters partitioned by the character '|' " +
                    "is incorrect, it is expected  %s, but is: %s", count(), data.length) + newLine +
                    String.format("\tTeam format %s, and you have entered: %s", format(),command));
        }
        String databaseName = data[1];

        manager.dropDatabase("\"" + databaseName + "\"");
        view.write("Database  " + databaseName + " deleted successfully");


    }

    @Override
    public String description() {
        return "Delete database";
    }

    @Override
    public String format() {
        return "dropDatabase|DatabaseName";
    }

    private int count() {
        return format().split("\\|").length;
    }
}
