package ua.com.denisimusIT.SQLCmd.controller;

import ua.com.denisimusIT.SQLCmd.controller.commands.Command;
import ua.com.denisimusIT.SQLCmd.controller.commands.Exit;
import ua.com.denisimusIT.SQLCmd.controller.commands.Help;
import ua.com.denisimusIT.SQLCmd.model.DataSet;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

import java.util.List;

public class MainController {
    private static final String NEWLINE = System.lineSeparator();
    private final Command[] commands;
    private View view;
    private DatabaseManager manager;
    private String separator = "•+--------------------------------------------------";
    private String beginSymbol = "•+ ";


    public MainController(View view, DatabaseManager databaseManager) {
        this.view = view;
        this.manager = databaseManager;
        this.commands = new Command[]{new Exit(view),new Help(view),new Tables(view,manager)};
    }

    public void run() {
        connectToDb();

        while (true) {
            view.write("enter the commands or help commands for a help call");
            String command = view.read();
            if (command.equals("tables")) {
                listOfDb();
            } else if (commands[1].canProcess(command)) {
                commands[1].Process(command);
            } else if (command.startsWith("find|")) {
                doFind(command);
            } else if (commands[0].canProcess(command)) {
                commands[0].Process(command);
            } else {
                view.write("Nonexistent commands:" + command);
            }
        }
    }

    private void doFind(String command) {
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


    private void connectToDb() {
        while (true) {
            try {
                view.write("Welcome to SQLCmd! =)");
                view.write("Enter, please a database name, user name and the password in a format: database|userName|password");
                String string = view.read();
                String[] data = string.split("\\|");
                String databaseName = data[0];
                String userName = data[1];
                String password = data[2];

                if (data.length != 3) {
                    throw new IllegalArgumentException("The number of parameters partitioned by the character '|' " +
                            "is incorrect, it is expected 3, but is: " + data.length);
                }
                manager.connect(databaseName, userName, password);
                break;

            } catch (Exception e) {
                String message = e.getMessage();
                if (e.getCause() != null) {
                    message += " " + e.getCause().getMessage();
                }
                view.write("Failure! For the reason : " + message);
                view.write("Repeat attempt please");
            }
        }
    }

    private void listOfDb() {
        String tableNames = manager.getTableNames().toString();
        view.write("Table names: ");
        view.write(tableNames);
    }

}
