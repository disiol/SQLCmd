package ua.com.denisimusIT.SQLCmd.controller.testCommands;

import ua.com.denisimusIT.SQLCmd.model.DataSet;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class FindCommandTest {

    final String newline = System.lineSeparator();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private DatabaseManager manager;
    private String tableName;

    @Before
    public void setup() {
        manager = new PostgresDatabaseManager();
        String database = "sqlCmd";
        String user = "postgres";
        String password = "1111";
        manager.connect(database, user, password);

    }
    @Test
    public void insertDataCorrect_id_101_name_Stiven_password_Pupkin()  {
        //TODO
         tableName = "users1";

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

        //thenSystem.setOut(new PrintStream(outContent));
        DataSet dataSet = new DataSet();

        String expected2 = new StringBuilder().append("id:101" + newline).append("name:Stiven" + newline)
                .append("password:Pupkin" + newline).append("--------------------------") + newline.toString();

        DataSet[] actual2 = manager.getTableData(tableName);

        assertEquals(expected2, Arrays.toString(actual2));


    }


    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами
        manager.dropTable(tableName);
    }
}
