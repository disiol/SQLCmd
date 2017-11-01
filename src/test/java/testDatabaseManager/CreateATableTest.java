package testDatabaseManager;

import controller.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 07.10.17.
 * mail: deoniisii@gmail.com
 */
public class CreateATableTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    DatabaseManager databaseManager;

    @Before
    public void connectToDataBase() {
        String dataBase = "sqlCmd";
        String user = "postgres";
        String password = "1111";

        databaseManager = new DatabaseManager();
        databaseManager.connect(dataBase, user, password);


    }

    @Test
    public void сreateTableCompany() {
        //TODO тест создания таблицы
        // создает таблицу и проверает создана ли она
        String sql = "CREATE TABLE company " +
                "(id INT PRIMARY KEY     NOT NULL," +
                " name           TEXT    NOT NULL, " +
                " password       TEXT     NOT NULL)";
        databaseManager.createATable("COMPANY");
        String expected = "[users1, company]";
        String[] actual = databaseManager.getTableNames();
        assertEquals("сreateTableCompany", expected, Arrays.toString(actual));


        // вытаскивает значение данных  и сравнивает
    }


    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами
        databaseManager.dropTable("COMPANY");

    }
}
