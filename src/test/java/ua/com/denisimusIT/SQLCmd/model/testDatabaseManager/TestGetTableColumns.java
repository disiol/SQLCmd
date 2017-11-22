package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.DataSet;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 06.10.17.
 * mail: deoniisii@gmail.com
 */
public class TestGetTableColumns {
    final String newline = System.lineSeparator();

    private DatabaseManager manager;
    private String tableName;

    @Before
    public void setup() {
        manager = new PostgresDatabaseManager();
        String databaseName = "postgres";
        String user = "postgres";
        String password = "1111";
        manager.connect(databaseName, user, password);

        //TODO создание базы данных и таблицы


        tableName = "users2";




    }

    @Test
    public void TestGetTableColumns() {


        // then


        String actual = manager.getTableColumns(tableName).toString();
        assertEquals("[id, name, password]", actual);
    }




    @After
    public void deleteTable() {
        //TODO drop dataBase
        // drop  таблицу с данами
    }
}
