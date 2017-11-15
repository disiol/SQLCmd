package ua.com.denisimusIT.SQLCmd.controller;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.Console;
import ua.com.denisimusIT.SQLCmd.view.View;

import javax.xml.crypto.Data;

public class MainController {
    private static final String NEWLINE = System.lineSeparator();
    private static final String GREETING = "Welcome to SQLCmd! =)" + NEWLINE +
            "For connection to the database you enter the command : " + NEWLINE +
            "connect|database|userName|password " +
            "or help command for a help call" + NEWLINE;

    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager postgresDatabaseManager = new PostgresDatabaseManager();

        view.write(GREETING);


        while (true) {
            try {

                String string = view.read();
                String[] data = string.split("\\|");
                String databaseName = data[0];
                String userName = data[1];
                String password = data[2];
                postgresDatabaseManager.connect(databaseName, userName, password);
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

}
