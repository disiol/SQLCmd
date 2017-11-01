package testDatabaseManager;

import controller.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 06.10.17.
 * mail: deoniisii@gmail.com
 */
public class InsertTest {
    final String newline = System.lineSeparator();
    private DatabaseManager manager;

    @Before
    public void setup() {
        manager = new DatabaseManager();
        String database = "sqlCmd";
        String user = "postgres";
        String password = "1111";
        manager.connect(database, user, password);

        //TODO crate table
    }

    @Test
    public void insertDataCorrect_id_101_name_Stiven_password_Pupkin() throws SQLException {

        String sqlInsert = "INSERT INTO public.users1 (id , name, password)" +
                "VALUES ('101','Stiven', 'Pupkin')";
        manager.insertToTable();
        //TODO

        String expected = new StringBuilder().append("id:101" + newline).append("name:Stiven" + newline)
                .append("password:Pupkin ").toString();


        String actual = null;

        assertEquals(expected, actual);



    }


    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами
    }
}
