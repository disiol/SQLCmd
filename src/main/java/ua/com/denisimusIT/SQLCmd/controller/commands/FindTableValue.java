package ua.com.denisimusIT.SQLCmd.controller.commands;

import ua.com.denisimusIT.SQLCmd.controller.commands.Command;
import ua.com.denisimusIT.SQLCmd.model.DataSet;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

import java.util.List;

public class FindTableValue implements Command {
    private final View view;
    private final DatabaseManager manager;
    private String separator = "•+--------------------------------------------------";
    private String beginSymbol = "•+ ";

    public FindTableValue(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void Process(String command) {
        String[] dataCommand = command.split("\\|");
        String tableName = dataCommand[1];

        List<String> tableColumns = manager.getTableColumns(tableName);
        tableHeader(tableColumns);

        DataSet[] tableData = manager.getTableData(tableName);
        printTable(tableData);

    }



    private void tableHeader(List<String> names) {
        String result = beginSymbol;
        for (String name : names) {

            result += name + " + ";

        }

        view.write(separator);
        view.write(result);
        view.write(separator);


    }

    private void printTable(DataSet[] data) {

        for (DataSet rows : data) {
            printRow(rows);
        }

    }

    private void printRow(DataSet data) {
        String result = beginSymbol;
        Object[] names = data.getValues();

        for (Object name : names) {
            result += name + " + ";
        }
        view.write(result);
        view.write(separator);
    }

}
