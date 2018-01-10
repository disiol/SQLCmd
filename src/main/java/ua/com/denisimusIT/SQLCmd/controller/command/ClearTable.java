package ua.com.denisimusIT.SQLCmd.controller.command;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class ClearTable implements Command {


    private DatabaseManager manager;
    private View view;
    private String newLine = System.lineSeparator();


    public ClearTable(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {
        // TODO а если юзер случайно ввел команду? Может переспросить его?

        String[] data = command.split("\\|");


        int minQuantity = 2;
        if (data.length != minQuantity) {
            throw new IllegalArgumentException(String.format("Team format %s, and you have entered: %s",format().toString(), command));
        }
        String tableName = data[1];
        manager.clearATable("\"" + tableName + "\"");
        view.write(String.format("The table: \"%s\"  is cleared successfully", data[1]));

    }

    @Override
    public String description() {
        return "for cleaning of all table";
    }

    @Override
    public String format() {
        return "clear|tableName";
    }

}
