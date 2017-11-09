package ua.com.denisimusIT.SQLCmd.testDatabaseManager;

import ua.com.denisimusIT.SQLCmd.model.DataSet;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 06.10.17.
 * mail: deoniisii@gmail.com
 */
public class TestUpdateTableData {


    private PostgresDatabaseManager manager;
    private String tableName;

    @Before
    public void setup() {
        manager = new PostgresDatabaseManager();
        manager.connect("sqlCmd", "postgres", "1111");

    }

    @Test
    public void ChangeOfTheTable() {

        tableName = "company";
        manager.createATable(tableName);


        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        manager.insertData(tableName,input);

        // when
        DataSet newValue = new DataSet();
        newValue.put("password", "pass2");
        newValue.put("name", "Pup");
        manager.updateTableData(tableName, 13, newValue);

        // then
        DataSet[] users = manager.getTableData(tableName);
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[id, name, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, Pup, pass2]", Arrays.toString(user.getValues()));

    }


    @After
    public void deleteTable() {
        // drop  таблицу с данами
        manager.dropTable(tableName);
    }
}
