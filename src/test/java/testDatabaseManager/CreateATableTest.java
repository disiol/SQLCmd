package testDatabaseManager;

import controller.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 07.10.17.
 * mail: deoniisii@gmail.com
 */
public class CreateATableTest {
    DatabaseManager databaseManager;
    private Connection connection;
    Statement stmt;

    @Before
    public void connectToDataBase() {
        String dataBase = "sqlCmd";
        String user = "postgres";
        String password = "1111";

        databaseManager = new DatabaseManager();
        databaseManager.connect(dataBase, user, password);
        connection = databaseManager.getConnection();

    }

    @Test
    public void сreateTableUsers1() {

        //TODO тест создания таблицы
        String sql = "CREATE TABLE users1 " +
                "(id INT PRIMARY KEY     NOT NULL," +
                " name           TEXT    NOT NULL, " +
                " password       TEXT     NOT NULL)";
        databaseManager.createATable();
        String expected = "[users, users1]";
        String[] actual = databaseManager.getTableNames();
        assertEquals("сreateTableUsers1", expected, Arrays.toString(actual));

        // создает таблицу и проверает создана ли она
        // вытаскивает значение данных  и сравнивает
    }


    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами

    }
}
