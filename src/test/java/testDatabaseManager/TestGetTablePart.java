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
public class TestGetTablePart {
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

    }

    @Test
    public void testGetTablePart() {

        tableName = "company";


        manager.createATable(tableName);
        String expected = "[company]";
        String[] actual = manager.getTableNames();
        assertEquals("сreateTableCompany", expected, Arrays.toString(actual));

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
        String actual1 = manager.getTablePart(tableName, dataGetId);
        String expected1 = "id:13";
        assertEquals(expected1, actual1);

        String dataGetName = "name";
        String actual2 = manager.getTablePart(tableName, dataGetName);
        String expected2 = "name:Stiven";
        assertEquals(expected2, actual2);

        String dataGetPassword = "password";
        String actual3 = manager.getTablePart(tableName, dataGetPassword);
        String expected3 = "password:pass";
        assertEquals(expected3, actual3);


    }


    @After
    public void deleteTable() {
        // drop  таблицу с данами
        manager.dropTable(tableName);
    }
}
