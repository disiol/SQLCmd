package ua.com.denisimusIT.SQLCmd.controller.command;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class CreateNewTable implements Command {
    private String newLine = System.lineSeparator();
    private View view;
    private DatabaseManager manager;
    private int minQuantity = 3;

    public CreateNewTable(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create" + "|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");


        if (data.length != minQuantity) {
            throw new IllegalArgumentException(String.format("The number of parameters partitioned by the character '|' " +
                    "is incorrect, it is expected  %s, but is: %s", minQuantity, data.length) + newLine +
                    "\texample: create|tableName|column1 column type, column2 column type,...,columnN column typ");
        }
        String tableName = data[1];
        String columnsValues = data[2];
        manager.createATable("\"" + tableName + "\"", columnsValues);
        //TODO dell WORD EROR
        view.write("The table: " + tableName + " is created successfully");
    }

    @Override
    public String description() {

        return "For create table:";
    }

    @Override
    public String format() {
        return
                "create|tableName|column1 column type, column2 column type,...,columnN column type";
    }


}
