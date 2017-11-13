package ua.com.denisimusIT.SQLCmd.testDatabaseManager;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class DropTableTest {
    DatabaseManager postgresDatabaseManager;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final String newline = System.lineSeparator();


    @Before
    public void connectToDataBase() {
        String dataBase = "postgres";
        String user = "postgres";
        String password = "1111";

        postgresDatabaseManager = new PostgresDatabaseManager();
        postgresDatabaseManager.connect(dataBase, user, password);


    }

    @Test
    public void dropTableCompany() {
        String tableName = "company";
        //defore
        postgresDatabaseManager.createATable(tableName);
        String expected = "[company]";
        Object[] actual = postgresDatabaseManager.getTableNames().toArray();
        assertEquals("сreateTableCompany", expected, Arrays.toString(actual));

        System.setOut(new PrintStream(outContent));



        //тест удаления таблицы
        postgresDatabaseManager.dropTable(tableName);
        String expected_1 = "Table " + tableName + " deleted in given database..." + newline ;
        String actual_1 = outContent.toString();

        assertEquals("dropTableCompanyMessage", expected_1, actual_1);


        String expected_2 = "[]";
        Object[] actual_2 = postgresDatabaseManager.getTableNames().toArray();
        assertEquals("dropTableCompany", expected_2, Arrays.toString(actual_2));

        // создает таблицу и проверает создана ли она
        // вытаскивает значение данных  и сравнивает
    }

}
