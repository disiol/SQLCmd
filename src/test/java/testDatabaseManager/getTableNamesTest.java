package testDatabaseManager;

import model.PostgresDatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 07.10.17.
 * mail: deoniisii@gmail.com
 */
public class getTableNamesTest {
    PostgresDatabaseManager postgresDatabaseManager = new PostgresDatabaseManager();

    @Before
    public void connectToDataBase() {
        String dataBase = "sqlCmd";
        String user = "postgres";
        String password = "1111";
        postgresDatabaseManager.connect(dataBase, user, password);

    }

    @Test
    public void users() throws SQLException {

        //before
        String tableName = "COMPANY";
        postgresDatabaseManager.createATable(tableName);
        tableName = "users1";
        postgresDatabaseManager.createATable(tableName);


        //then

        String expected;
        String[] actual;
        expected = "[company, users1]";
        actual = postgresDatabaseManager.getTableNames();
        assertEquals(expected, Arrays.toString(actual));
    }


    @After
    public void deleteTable() {
        // drop  таблицу с данами
        postgresDatabaseManager.dropTable("company,users1");
    }
}
