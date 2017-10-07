package testDatabaseManager;

import controller.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
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
    private Statement stmt = null;

    @Before
    public void setup() throws SQLException {
        manager = new DatabaseManager();
        manager.connect("sqlCmd", "postgres", "1111");
        stmt = manager.getConnection().createStatement();
//        String sql = "CREATE TABLE public.test1 " +
//                "(id INT PRIMARY KEY     NOT NULL," +
//                " name           TEXT    NOT NULL, " +
//                " password            INT     NOT NULL)";
//
//        stmt.executeUpdate(sql);
//        System.out.println("CREATE TABLE ");

    }

    @Test
    public void insertDataCorrect_id_101_name_Stiven_password_Pupkin() {


        try {
            stmt = manager.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sqlInsert = "INSERT INTO public.test1 (id , name, password)" +
                "VALUES ('102','Stiven', '1111')";
        //TODO  доделать селект

        String expected = new StringBuilder().append("id:102" + newline).append("name:Stiven" + newline)
                .append("password:1111 ").toString();

        manager.insert(stmt, sqlInsert);

        String actual = null;
        try {
            actual = stmt.executeQuery("SELECT * FROM test1;").getString("*");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(expected, actual);


    }


    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами
    }
}
