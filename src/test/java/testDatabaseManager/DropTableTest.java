package testDatabaseManager;

import controller.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class DropTableTest {
    DatabaseManager databaseManager;

    @Before
    public void connectToDataBase() {
        String dataBase = "sqlCmd";
        String user = "postgres";
        String password = "1111";

        databaseManager = new DatabaseManager();
        databaseManager.connect(dataBase, user, password);
        //TODO  crate  таблицу с данами


    }

    @Test
    public void сreateTableUsers1() {

        //TODO тест создания таблицы
        databaseManager.dropTable("company");
        String expected = "[users1]";
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
