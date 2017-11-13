package ua.com.denisimusIT.SQLCmd.testDatabaseManager;

import ua.com.denisimusIT.SQLCmd.model.DataSet;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;
import org.junit.*;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 06.10.17.
 * mail: deoniisii@gmail.com
 */
public class GetTableDatTest {


    private DatabaseManager manager;
    private String tableName;

    @Before
    public void setup() {
        manager = new PostgresDatabaseManager();
        String database = "postgres";
        String user = "postgres";
        String password = "1111";
        manager.connect(database, user, password);

    }

    @Test
    public void testGetTableData() {

        tableName = "company";

        // when
        manager.createATable(tableName);
        String expected = "[" + tableName + "]";
        Object[] actual = manager.getTableNames().toArray();
        assertEquals("сreateTableCompany", expected, Arrays.toString(actual));


        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        manager.insertData(tableName, input);

        // then
        DataSet[] company = manager.getTableData(tableName);
        assertEquals(1, company.length);

        DataSet user = company[0];
        assertEquals("[id, name, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, Stiven, pass]", Arrays.toString(user.getValues()));
    }


    @After
    public void deleteTable() {
        // drop  таблицу с данами
        manager.dropTable(tableName);
    }
}
