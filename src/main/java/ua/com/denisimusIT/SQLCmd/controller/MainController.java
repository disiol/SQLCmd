package ua.com.denisimusIT.SQLCmd.controller;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class MainController {
    private static final String NEWLINE = System.lineSeparator();
    private static final String GREETING = "Welcome to SQLCmd! =)" + NEWLINE +
            "For connection to the database you enter the command : " + NEWLINE +
            "connect|database|userName|password " +
            "or help command for a help call" + NEWLINE;
    private View view;
    private DatabaseManager manager;

    public MainController(View view, DatabaseManager databaseManager) {
        this.view = view;
        this.manager = databaseManager;
    }

    public void run() {
        connectToDb();
        for (; ; ) {
            switch (view.read()) {
                case "connect":
                    connectToDb();
                    break;
                case "tables":
                    tablesNames();
                    break;
                case "exit":
                    view.write("Bye! =)");
                    System.exit(0);
                    break;
            }

        }

    }

    private void tablesNames() {
       view.write(manager.getTableNames().toString());
    }


    private void connectToDb() {
        while (true) {
            try {
                view.write(GREETING);
                String string = view.read();
                String[] data = string.split("\\|");
                if (data.length < 3) {
                    throw new IllegalArgumentException("the incorrect quantity of arguments partitioned '|' " +
                            "shall be three and you carried: " + data.length);
                }
                String databaseName = data[0];
                String userName = data[1];
                String password = data[2];
                manager.connect(databaseName, userName, password);
                break;

            } catch (Exception e) {
                printError(e);
            }
        }
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null) {
            message += " " + e.getCause().getMessage();
        }
        view.write("Failure! For the reason : " + message);
        view.write("Repeat attempt please");
        view.write("");
    }

}
