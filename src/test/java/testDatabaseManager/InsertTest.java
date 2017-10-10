package testDatabaseManager;

import controller.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Statement;
import java.util.Arrays;

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
        manager.connect("sqlcmd", "postgres", "postgres");
    }

    @Test
    public void insertDataCorrect_id_101_name_Stiven_password_Pupkin() {

       // Statement stmt = manager.getConnection();
        String sqlInsert = "INSERT INTO public.users (id , name, password)" +
                "VALUES ('101','Stiven', 'Pupkin')";
        //TODO

        String expected = new StringBuilder().append("id:101" + newline).append("name:Stiven" + newline)
                .append("password:Pupkin ").toString();

    //    manager.insert(stmt, sqlInsert);

        //assertEquals(expected, actual);


        System.out.println(expected);
    }


    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами
    }
}
