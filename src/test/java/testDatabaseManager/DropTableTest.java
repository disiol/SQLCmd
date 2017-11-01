package testDatabaseManager;

import controller.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class DropTableTest {
    DatabaseManager databaseManager;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final String newline = System.lineSeparator();


    @Before
    public void connectToDataBase() {
        String dataBase = "sqlCmd";
        String user = "postgres";
        String password = "1111";

        databaseManager = new DatabaseManager();
        databaseManager.connect(dataBase, user, password);
        //TODO  crate  таблицу с данами


    }

    @Test
    public void dropTableCompany() {
        //defore
        databaseManager.createATable("COMPANY");
        String expected = "[users1, company]";
        String[] actual = databaseManager.getTableNames();
        assertEquals("сreateTableCompany", expected, Arrays.toString(actual));

        System.setOut(new PrintStream(outContent));

        String tableName = "company";

        //тест удаления таблицы
        databaseManager.dropTable(tableName);
        String expected_1 = "Table " + tableName + " deleted in given database..." + newline ;
        String actual_1 = outContent.toString();

        assertEquals("dropTableCompanyMessage", expected_1, actual_1);


        String expected_2 = "[users1]";
        String[] actual_2 = databaseManager.getTableNames();
        assertEquals("dropTableCompany", expected_2, Arrays.toString(actual_2));

        // создает таблицу и проверает создана ли она
        // вытаскивает значение данных  и сравнивает
    }

}
