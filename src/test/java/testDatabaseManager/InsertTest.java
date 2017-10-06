package testDatabaseManager;

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
    public void crateTabele() {
        //TODO создать новою таблицу с данами

    }

    @Test
    public void insertDataCorect_id_101_name_Stiven_password_Pupkin() {

        String expected = new StringBuilder().append("id:101" + newline).append("name:Stiven" + newline)
                .append("password:Pupkin " ).toString();

        System.out.println(expected);
    }


    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами
    }
}
