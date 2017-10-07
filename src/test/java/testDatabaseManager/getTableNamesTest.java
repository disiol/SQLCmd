package testDatabaseManager;

import controller.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 07.10.17.
 * mail: deoniisii@gmail.com
 */
public class getTableNamesTest {
    DatabaseManager databaseManager = new DatabaseManager();

    @Before
    public void connectToDataBase() {
        String dataBase = "sqlCmd";
        String user = "postgres";
        String password = "1111";
        databaseManager.connect(dataBase, user, password);
        //TODO создание таблицы
    }

    @Test
    public void users() throws SQLException {

        //TODO вывода на екран списка таблиц
        String expected;
        String []actual;
        expected = "[users]";
        Connection connection = databaseManager.getConnection();
        actual = databaseManager.getTableNames();
        assertEquals(expected, Arrays.toString(actual));
        connection.close();
    }


    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами
    }
}
