package testDatabaseManager;

import controller.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Denis Oleynyk on 07.10.17.
 * mail: deoniisii@gmail.com
 */
public class TablesTest {
    @Before
    public void connectToDataBase() {
        String dataBase = "sqlCmd";
        String user = "postgres";
        String password = "1111";

        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.connect(dataBase, user, password);
        //TODO создание таблицы
    }

    @Test
    public void users() {

        //TODO вывода на екран списка таблиц
        // создает таблицу и проверает создана ли она
    }


    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами
    }
}
