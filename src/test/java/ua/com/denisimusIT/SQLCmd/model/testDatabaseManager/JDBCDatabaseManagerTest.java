package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

/**
 * Created by indigo on 25.08.2015.
 */
public class JDBCDatabaseManagerTest extends DatabaseManagerTest {

    @Override
    public DatabaseManager getDatabaseManager() {
        return new PostgresDatabaseManager();
    }
}
