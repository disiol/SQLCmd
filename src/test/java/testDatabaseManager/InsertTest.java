package testDatabaseManager;

import controller.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Denis Oleynyk on 06.10.17.
 * mail: deoniisii@gmail.com
 */
public class InsertTest {
    final String newline = System.lineSeparator();




    @Before
    public void crateTable() {
        //TODO создать новою таблицу с данами
        connectToDataBase();

    }

    private void connectToDataBase() {
        String dataBase = "sqlCmd";
        String user = "postgres";
        String password = "1111";

        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.connect(dataBase, user, password);
    }

    @Test
    public void insertDataCorrect_id_101_name_Stiven_password_Pupkin() {

        String expected = new StringBuilder().append("id:101" + newline).append("name:Stiven" + newline)
                .append("password:Pupkin " ).toString();

        System.out.println(expected);
    }


    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами
    }
}
