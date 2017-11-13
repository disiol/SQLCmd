package ua.com.denisimusIT.SQLCmd.testDatabaseManager;

import org.junit.Before;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

public class createDatabaseTest {

    private PostgresDatabaseManager postgresDatabaseManager;

    @Before
    public void connectToDataBase() {
        String dataBase = "sqlCmd";
        String user = "postgres";
        String password = "1111";

        postgresDatabaseManager = new PostgresDatabaseManager();
        postgresDatabaseManager.connect(dataBase, user, password);


    }


}
