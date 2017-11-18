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
    final String newline = System.lineSeparator();

    DatabaseManager postgresDatabaseManager = new PostgresDatabaseManager();

    String dataBase = "postgres";
    String userDb = "postgres";
    String password = "1111";
    private String testDatabase = "testdatabase";

    String user;
    @Before

    public void before(){
        connectToDataBase();
        CrateDb();
        ConnectToCrateDb();

    }

    private void connectToDataBase() {
        postgresDatabaseManager.connect(dataBase, userDb, password);
        //создать и выбрать баззу
    }

    private void ConnectToCrateDb() { ;
        postgresDatabaseManager.connect(testDatabase, userDb, password);
    }

    private void CrateDb() {
        postgresDatabaseManager.createDatabase(testDatabase);
    }

    @Test
    public void createUserTest() {
        System.setOut(new PrintStream(outContent));

        user = "den";
        String password = "111";
        postgresDatabaseManager.createUser(user, password);
        String expected = "Creating user..." + newline +
                "It is created user: " + user + " with the password: " + password;
        String actual = outContent.toString();
        assertEquals("created user", expected, actual);


        String expected1 = "The user : " + user + "already is created";
        String actual2 = outContent.toString();
        assertEquals("The user already is created", expected, actual);


    }


    @After
    public void dropDatabase() {
        ConnectToCrateDb();
        postgresDatabaseManager.dropUser(user);
        connectToDataBase();
        postgresDatabaseManager.dropDatabase(testDatabase);
    }

}
