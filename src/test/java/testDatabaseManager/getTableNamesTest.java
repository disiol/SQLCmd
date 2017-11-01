package testDatabaseManager;

import controller.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 07.10.17.
 * mail: deoniisii@gmail.com
 */
public class getTableNamesTest {
    DatabaseManager databaseManager = new DatabaseManager();

    @Before
    public void connectToDataBase() {
        String dataBase = "sqlCmd";
        String user = "postgres";
        String password = "1111";
        databaseManager.connect(dataBase, user, password);

    }

    @Test
    public void users() throws SQLException {

        //before
        String tableName = "COMPANY";
        databaseManager.createATable(tableName);
        tableName = "users1";
        databaseManager.createATable(tableName);


        //then

        String expected;
        String[] actual;
        expected = "[company, users1]";
        actual = databaseManager.getTableNames();
        assertEquals(expected, Arrays.toString(actual));
    }


    @After
    public void deleteTable() {
        // drop  таблицу с данами
        databaseManager.dropTable("company,users1");
    }
}
