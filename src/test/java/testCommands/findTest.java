package testCommands;

import model.DataSet;
import model.PostgresDatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class findTest {

    final String newline = System.lineSeparator();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private PostgresDatabaseManager manager;

    @Before
    public void setup() {
        manager = new PostgresDatabaseManager();
        String database = "sqlCmd";
        String user = "postgres";
        String password = "1111";
        manager.connect(database, user, password);

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

        DataSet[] actual = manager.getTableData(tableName);

        assertEquals(expected, Arrays.toString(actual));


    }


    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами
    }
}
