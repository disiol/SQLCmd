package ua.com.denisimusIT.SQLCmd.controller.command;

import ua.com.denisimusIT.SQLCmd.model.DataSetImpl;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

import java.util.List;

public class ContentsOfTheTable implements Command {
    private String newLine = System.lineSeparator();

    private final View view;
    private final DatabaseManager manager;
    private String separator = "•+--------------------------------------------------";
    private String beginSymbol = "•+ ";

    public ContentsOfTheTable(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        String[] dataCommand = command.split("\\|");
        int count = 2;
        if (dataCommand.length != count) {
            throw new IllegalArgumentException(String.format("Team format find|tableName, and you have entered: %s",
                    count, dataCommand.length) + newLine +
                    String.format("Team format find|tableName, and you have entered: %s", command));
        }
        String tableName = dataCommand[1];

        //TODO exehen tabel didon crate

        List<String> tableColumns = manager.getTableColumns(tableName);
        printHeader(tableColumns);

        List<DataSetImpl> tableData = manager.getTableData("\"" + tableName + "\"");
        printTable(tableData);

    }

    @Override
    public String description() {
        return "for receiving contents of the table tableName";

    }

    @Override
    public String format() {
        return "find|tableName";
    }


    private void printHeader(List<String> tableColumns) {
        String result = beginSymbol;
        for (String name : tableColumns) {

            result += name + " + ";

        }

        view.write(separator);
        view.write(result);
        view.write(separator);


    }

    private void printTable(List<DataSetImpl> data) {

        for (DataSetImpl rows : data) {
            printRow(rows);
        }

    }

    private void printRow(DataSetImpl data) {
        String result = beginSymbol;
        Object[] names = data.getValues();

        for (Object name : names) {
            result += name + " + ";
        }
        view.write(result);
        view.write(separator);

    }

}
