package testDatabaseManager;

import controller.DataSet;
import controller.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 06.10.17.
 * mail: deoniisii@gmail.com
 */
public class TestGetTableColumn {
    final String newline = System.lineSeparator();

    private DatabaseManager manager;
    private String tableName;

    @Before
    public void setup() {
        manager = new DatabaseManager();
        String sqlCmd = "sqlCmd";
        String user = "postgres";
        String password = "1111";
        manager.connect(sqlCmd, user, password);

        //TODO создание базы данных

        tableName = "company";


        manager.createATable(tableName);
        String expected = "[company]";
        String[] actual = manager.getTableNames();
        assertEquals("сreateTableCompany", expected, Arrays.toString(actual));


    }

    @Test
    public void testWanRow() {


        // given
        manager.clearATable(tableName);
        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        manager.insertData(tableName, input);

        // then
        //принимает данные для выдачи
        String dataGetId = "id";
        String actual1 = manager.getTableColumn(tableName, dataGetId);
        String expected1 = "id" + newline +
                           "13";
        assertEquals(expected1, actual1);

        String dataGetName = "name";
        String actual2 = manager.getTableColumn(tableName, dataGetName);
        String expected2 = "name" + newline +
                           "Stiven";
        assertEquals(expected2, actual2);

        String dataGetPassword = "password";
        String actual3 = manager.getTableColumn(tableName, dataGetPassword);
        String expected3 = "password" + newline +
                               "pass";
        assertEquals(expected3, actual3);


    }


    @Test
    public void testTooRow() {


        // given
        manager.clearATable(tableName);


        DataSet input = new DataSet();
        input.put("id", 14);
        input.put("name", "Stiven2");
        input.put("password", "pass2");
        manager.insertData(tableName, input);

        // then
        //принимает данные для выдачи
        String dataGetId = "id";
        String actual1 = manager.getTableColumn(tableName, dataGetId);
        String expected1 = "id" + newline +
                           "13" + newline +
                           "14";
        assertEquals(expected1, actual1);

        String dataGetName = "name";
        String actual2 = manager.getTableColumn(tableName, dataGetName);
        String expected2 = "name" + newline +
                           "Stiven" + newline +
                           "Stiven2";
        assertEquals(expected2, actual2);

        String dataGetPassword = "password";
        String actual3 = manager.getTableColumn(tableName, dataGetPassword);
        String expected3 = "password" + newline +
                               "pass" + newline +
                               "pass2";
        assertEquals(expected3, actual3);


    }




    @After
    public void deleteTable() {
        //TODO drop dataBase
        // drop  таблицу с данами
        manager.dropTable(tableName);
    }
}
