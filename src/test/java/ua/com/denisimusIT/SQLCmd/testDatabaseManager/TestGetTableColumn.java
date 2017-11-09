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
public class TestGetTableColumn {
    final String newline = System.lineSeparator();

    private PostgresDatabaseManager manager;
    private String tableName;

    @Before
    public void setup() {
        manager = new PostgresDatabaseManager();
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
        DataSet[] users = manager.getTableColumns(tableName, dataGetId);


        DataSet user = users[0];
        assertEquals("[id]", Arrays.toString(user.getNames()));
        assertEquals("[13]", Arrays.toString(user.getValues()));


        dataGetId = "name";
        users = manager.getTableColumns(tableName, dataGetId);
        user = users[0];
        assertEquals("[name]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven]", Arrays.toString(user.getValues()));


        dataGetId = "password";
        users = manager.getTableColumns(tableName, dataGetId);
        user = users[0];
        assertEquals("[password]", Arrays.toString(user.getNames()));
        assertEquals("[pass]", Arrays.toString(user.getValues()));

        dataGetId = "id,password";
        users = manager.getTableColumns(tableName, dataGetId);
        user = users[0];
        assertEquals("[id, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, pass]", Arrays.toString(user.getValues()));

        dataGetId = "name,id,password";
        users = manager.getTableColumns(tableName, dataGetId);
        user = users[0];
        assertEquals("[name, id, password]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven, 13, pass]", Arrays.toString(user.getValues()));


    }


    @Test
    public void testTooRow() {


        // given
        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        manager.insertData(tableName, input);

        DataSet input2 = new DataSet();
        input2.put("id", 14);
        input2.put("name", "Stiven2");
        input2.put("password", "pass2");
        manager.insertData(tableName, input2);

        // then
        //принимает данные для выдачи
        String dataGetId = "id";
        DataSet[] users = manager.getTableColumns(tableName, dataGetId);


        DataSet user = users[0];
        assertEquals("[id]", Arrays.toString(user.getNames()));
        assertEquals("[13]", Arrays.toString(user.getValues()));

        user = users[1];
        assertEquals("[id]", Arrays.toString(user.getNames()));
        assertEquals("[14]", Arrays.toString(user.getValues()));


        dataGetId = "name";
        users = manager.getTableColumns(tableName, dataGetId);
        user = users[0];
        assertEquals("[name]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven]", Arrays.toString(user.getValues()));

        user = users[1];
        assertEquals("[name]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven2]", Arrays.toString(user.getValues()));


        dataGetId = "password";
        users = manager.getTableColumns(tableName, dataGetId);
        user = users[0];
        assertEquals("[password]", Arrays.toString(user.getNames()));
        assertEquals("[pass]", Arrays.toString(user.getValues()));

        user = users[1];
        assertEquals("[password]", Arrays.toString(user.getNames()));
        assertEquals("[pass2]", Arrays.toString(user.getValues()));

        dataGetId = "id,password";
        users = manager.getTableColumns(tableName, dataGetId);
        user = users[0];
        assertEquals("[id, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, pass]", Arrays.toString(user.getValues()));

        user = users[1];
        assertEquals("[id, password]", Arrays.toString(user.getNames()));
        assertEquals("[14, pass2]", Arrays.toString(user.getValues()));

        dataGetId = "name,id,password";
        users = manager.getTableColumns(tableName, dataGetId);

        user = users[0];
        assertEquals("[name, id, password]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven, 13, pass]", Arrays.toString(user.getValues()));

        user = users[1];
        assertEquals("[name, id, password]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven2, 14, pass2]", Arrays.toString(user.getValues()));


    }


    @After
    public void deleteTable() {
        //TODO drop dataBase
        // drop  таблицу с данами
        manager.dropTable(tableName);
    }
}
