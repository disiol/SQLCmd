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
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void сreateTableUsers1() {

        //TODO тест создания таблицы
        String sql = "CREATE TABLE users1 " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " AGE            INT     NOT NULL, " +
                " ADDRESS        CHAR(50), " +
                " SALARY         REAL)";
        databaseManager.createATable(stmt,sql);
        String expected = "[users, users1]";
        String[] actual = databaseManager.getTableNames();
        assertEquals("сreateTableUsers1", expected, Arrays.toString(actual));

        // создает таблицу и проверает создана ли она
    }


    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами
    }
}
