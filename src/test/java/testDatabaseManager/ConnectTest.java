package testDatabaseManager;

import controller.DatabaseManager;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Denis Oleynyk on 25.09.17.
 * mail: deoniisii@gmail.com
 */
public class ConnectTest {


    DatabaseManager databaseManager = new DatabaseManager();


    @Test
    public void ConnectTest() throws SQLException {
        // TODO  connect to data base

        databaseManager.connect("sqlCmd",
                "postgres", "1111");

        String expected = "Opened database successfully";

        Connection actual = databaseManager.getConnection();
        assertEquals("connect to data base", expected, actual.toString());
        actual.close();

    }

    @Test
    public void theDatabaseManagerDoesNotExist() throws  SQLException {
        databaseManager.connect(" ",
                "postgres", "1111");

        String expected;
        Connection actual;
        expected = "База данных не сушествует";
        actual = databaseManager.getConnection();
        assertEquals("The databaseManager does not exist", expected, actual.toString());
        actual.close();
    }

    @Test
    public void notTheCorrectPassword() throws Exception {
        databaseManager.connect("sqlCmd",
                "postgres", "");

        String expected;
        Connection actual;
        expected = "пароль не верный ";
        actual = databaseManager.getConnection();
        assertEquals("not the correct password", expected, actual.toString());
        actual.close();
    }

    @Test
    public void theUserDoesNotExist() throws SQLException {
        databaseManager.connect("sqlCmd", "", "1111");
        String expected;
        Connection actual;
        expected = "Пользователя не сушествует";
        actual = databaseManager.getConnection();
        assertEquals("the user does not exist", expected, actual.toString());
        actual.close();
    }


}
