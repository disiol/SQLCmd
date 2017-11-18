package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 07.10.17.
 * mail: deoniisii@gmail.com
 */
public class CreateATableTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final String newline = System.lineSeparator();
    private String testDatabaseName;

    DatabaseManager postgresDatabaseManager;

    @Before
    public void connectToDataBase() {
        String dataBase = "postgres";
        String user = "postgres";
        String password = "1111";

        postgresDatabaseManager = new PostgresDatabaseManager();
        postgresDatabaseManager.connect(dataBase, user, password);
        testDatabaseName = "TestDatabase";
        postgresDatabaseManager.createDatabase(testDatabaseName);
        postgresDatabaseManager.connect(testDatabaseName, user, password);


    }

    @Test
    public void сreateTableCompany() {
        //TODO тест создания таблицы

        System.setOut(new PrintStream(outContent));

        // создает таблицу и проверает создана ли она
//        String sql = "CREATE TABLE company " +
//                "(id INT PRIMARY KEY     NOT NULL," +
//                " name           TEXT    NOT NULL, " +
//                " password       TEXT     NOT NULL)";




        String tableName = "company";
        postgresDatabaseManager.createATable(tableName);

        String expected = "Table " + tableName + " created successfully" + newline;
        String actual = outContent.toString();
        assertEquals("сreateTableCompany", expected, actual);



        String expected_1 = "[company]";
        Object[] actual_1 = postgresDatabaseManager.getTableNames().toArray();
        assertEquals("сreateTableCompany", expected_1, Arrays.toString(actual_1));


        // вытаскивает значение данных  и сравнивает
    }
//TODO

    @After
    public void dropDatabase() {
        //drop  таблицу с данами
        postgresDatabaseManager.dropTable(testDatabaseName);

    }
}
