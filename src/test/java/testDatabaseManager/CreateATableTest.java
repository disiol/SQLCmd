package testDatabaseManager;

import controller.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 07.10.17.
 * mail: deoniisii@gmail.com
 */
public class CreateATableTest {
    @Before
    public void connectToDataBase() {
        String dataBase = "sqlCmd";
        String user = "postgres";
        String password = "1111";

        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.connect(dataBase, user, password);
    }

    @Test
    public void сreateTable1() {

       //TODO тест создания таблицы
        String expected = null;
        String actual = new String();
        assertEquals("сreateTable1",expected,actual);

        // создает таблицу и проверает создана ли она
    }


    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами
    }
}
