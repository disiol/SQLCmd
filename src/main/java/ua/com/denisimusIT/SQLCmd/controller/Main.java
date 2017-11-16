package ua.com.denisimusIT.SQLCmd.controller;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.Console;
import ua.com.denisimusIT.SQLCmd.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager databaseManager = new PostgresDatabaseManager();
        MainController mainController = new MainController(view,databaseManager);
        mainController.run();


    }
}
