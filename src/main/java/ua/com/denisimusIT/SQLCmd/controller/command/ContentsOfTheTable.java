package ua.com.denisimusIT.SQLCmd.controller.command;

import ua.com.denisimusIT.SQLCmd.model.DataSet;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

import java.util.List;

public class ContentsOfTheTable implements Command {
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
        String tableName = dataCommand[1];

        view.write("Contents of the table " + tableName + ":");
        List<String> tableColumns = manager.getTableColumns(tableName);
        tableHeader(tableColumns);

        DataSet[] tableData = manager.getTableData(tableName);
        printTable(tableData);

    }

    @Override
    public String description() {
        return "for receiving contents of the table tableName";
    }

    @Override
    public String format() {
        return "find|tableName ";
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
