package testDatabaseManager;

import model.PostgresDatabaseManager;
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


    PostgresDatabaseManager postgresDatabaseManager;

    @Before
    public void connectToDataBase() {
        String dataBase = "sqlCmd";
        String user = "postgres";
        String password = "1111";

        postgresDatabaseManager = new PostgresDatabaseManager();
        postgresDatabaseManager.connect(dataBase, user, password);


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
        String[] actual_1 = postgresDatabaseManager.getTableNames();
        assertEquals("сreateTableCompany", expected_1, Arrays.toString(actual_1));


        // вытаскивает значение данных  и сравнивает
    }


    @After
    public void deleteTable() {
        //drop  таблицу с данами
        postgresDatabaseManager.dropTable("COMPANY");

    }
}
