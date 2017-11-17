package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager.doBeforeTests;

import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

public class CreateDatabaseAndUserForTests {
    PostgresDatabaseManager postgresDatabaseManager = new PostgresDatabaseManager();

    public void createDatabaseAndUser(String databaseName, String newUser, String newPassword) {
        postgresDatabaseManager.createDatabase(databaseName);
        postgresDatabaseManager.createUser(newUser, newPassword);
    }
}
