package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 07.10.17.
 * mail: deoniisii@gmail.com
 */
public class getTableNamesTest {
    DatabaseManager postgresDatabaseManager = new PostgresDatabaseManager();

    @Before
    public void connectToDataBase() {
        String dataBase = "postgres";
        String user = "postgres";
        String password = "1111";
        postgresDatabaseManager.connect(dataBase, user, password);
        //TODO создать и выбрать баззу

    }

    @Test
    public void users() {

        //before
        String tableName = "COMPANY";
        postgresDatabaseManager.createATable(tableName);
        tableName = "users1";
        postgresDatabaseManager.createATable(tableName);


        //then


        String expected = "[company, users1]";
        List<String> tableNames = postgresDatabaseManager.getTableNames();
        Collections.sort(tableNames);
        Object[] actual = tableNames.toArray();
        assertEquals(expected, Arrays.toString(actual));
    }


    @After
    public void deleteTable() {
        // TODO drop  базу с данами
        postgresDatabaseManager.dropTable("company,users1");
    }
}
