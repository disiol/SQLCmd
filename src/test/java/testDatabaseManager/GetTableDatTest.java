package testDatabaseManager;

import controller.DataSet;
import controller.DatabaseManager;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 06.10.17.
 * mail: deoniisii@gmail.com
 */
public class GetTableDatTest {
    final String newline = System.lineSeparator();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private DatabaseManager manager;

    @Before
    public void setup() {
        manager = new DatabaseManager();
        manager.connect("sqlCmd", "postgres", "1111");

        //TODO crate table
    }
    @Test
    public void testGetTableData() {
        // given
        String tableName = "public.users1";
        manager.clear(tableName);

        // when
        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        manager.insertData(tableName,input );

        // then
        DataSet[] users1 = manager.getTableData(tableName);
        assertEquals(1, users1.length);

        DataSet user = users1[0];
        assertEquals("[id, name, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, Stiven, pass]", Arrays.toString(user.getValues()));
    }



    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами
    }
}
