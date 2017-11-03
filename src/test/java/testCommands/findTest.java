package testCommands;

import controller.DataSet;
import controller.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class findTest {

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
    public void insertDataCorrect_id_101_name_Stiven_password_Pupkin()  {
        //TODO

        String tableName = "public.users1";
        System.setOut(new PrintStream(outContent));
        DataSet dataSet = new DataSet();

        String expected = new StringBuilder().append("id:101" + newline).append("name:Stiven" + newline)
                .append("password:Pupkin" + newline).append("--------------------------") + newline.toString();

        // manager.getTableData(tableName);
        DataSet[] actual = manager.getTableData(tableName);

        assertEquals(expected, Arrays.toString(actual));


    }


    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами
    }
}
