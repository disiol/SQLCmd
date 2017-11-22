package ua.com.denisimusIT.SQLCmd.controller;

import ua.com.denisimusIT.SQLCmd.controller.commands.Help;
import ua.com.denisimusIT.SQLCmd.model.DataSet;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class MainController {
    private static final String NEWLINE = System.lineSeparator();
    private View view;
    private DatabaseManager manager;
    private Help help = new Help();
    private String separator = "•+--------------------------------------------------";
    private String beginSymbol = "•+ ";


    public MainController(View view, DatabaseManager databaseManager) {
        this.view = view;
        this.manager = databaseManager;
    }

    public void run() {
        connectToDb();

        while (true) {
            view.write("enter the command or help command for a help call");
            String command = view.read();
            if (command.equals("list")) {
                listOfDb();
            } else if (command.equals("help")) {
                view.write(help.toString());
            } else if (command.startsWith("find|")) {
                doFind(command);
            } else if (command.equals("exit")) {
                view.write("See you soon!");
                System.exit(0);
            } else {
                view.write("Nonexistent command:" + command);
            }
        }
    }

    private void doFind(String command) {
        String[] dataCommand = command.split("\\|");

        DataSet[] data = manager.getTableData(dataCommand[1]);
        tableHeader(data);
        tableRows(data);

    }

    private void tableHeader(DataSet[] data) {
        String title = beginSymbol;
        String[] names = data[0].getNames();
        for (String name : names) {

            title += name + " + ";

        }

        view.write(separator);
        view.write(title);
        view.write(separator);


    }

    private void tableRows(DataSet[] data) {
        String valuesTable = "•+ ";
        int  lengthForNewLine = data[0].getNames().length;
        int counter = 1;


        for (int index = 0; index < data.length; index++) {
            Object[] values = data[index].getValues();
            for (Object name : values) {

                valuesTable += name.toString() + " + ";

                if(counter == lengthForNewLine) {
                    view.write(valuesTable);
                    view.write(separator);
                    valuesTable = beginSymbol;
                    counter = 0;
                }
                counter++;

            }
        }

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
