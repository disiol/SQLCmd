package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class CreateUserTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    DatabaseManager postgresDatabaseManager = new PostgresDatabaseManager();
    private String testDatabase = "testdatabase";
    @Before
    public void connectToDataBase() {
        String dataBase = "postgres";
        String user = "postgres";
        String password = "1111";
        postgresDatabaseManager.connect(dataBase, user, password);
        //TODO создать и выбрать баззу
        postgresDatabaseManager.createDatabase(testDatabase);
        postgresDatabaseManager.connect(testDatabase, user, password);
    }

    @Test
    public void createUserTest() {
        System.setOut(new PrintStream(outContent));

        String user = "den";
        String password = "111";
        postgresDatabaseManager.createUser(user, password);
        String expected = "It is created user:" + user + " with the password: " + password;
        String actual = outContent.toString();
        assertEquals("created user", expected, actual);


        expected = "The user : " + user + "already is";
        actual = outContent.toString();
        assertEquals("The user already is", expected, actual);


    }


    @After
    public  void dropDatabase(){
        postgresDatabaseManager.dropDatabase(testDatabase);
    }

}
