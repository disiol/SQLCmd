package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    public void before() {
        connectToDataBase();
        CrateDb();
        ConnectToCrateDb();

    }

    private void connectToDataBase() {
        postgresDatabaseManager.connectToDatabase(dataBase, userDb, password);
        //создать и выбрать баззу
    }

    private void ConnectToCrateDb() {
        postgresDatabaseManager.connectToDatabase(testDatabase, userDb, password);
    }

    private void CrateDb() {
        postgresDatabaseManager.createDatabase(testDatabase);
    }

    @Test
    public void createUserTest() {
        System.setOut(new PrintStream(outContent));

        user = "den";
        String password = "test";
        postgresDatabaseManager.createUser(user, password);


        connectToDataBase();
        postgresDatabaseManager.giveAccessUserToTheDatabase(testDatabase,user);
        postgresDatabaseManager.connectToDatabase(testDatabase, user, password);
        assertTrue("test connekt to DB ",postgresDatabaseManager.isConnected());

        String expected = "Creating user: " + user + newline +
                "It is created user: " + user + " with the password: " + password + newline
                + "Access user: "+ user + " to the database: testdatabase it is allow" ;
        String actual = outContent.toString();
        assertEquals("created user", expected, actual);


        String expected1 = "The user : " + user + "already is created";
        String actual2 = outContent.toString();
        assertEquals("The user already is created", expected, actual);



    }


    @After
    public void dropDatabase() {
        connectToDataBase();
        postgresDatabaseManager.giveAccessUserToTheDatabase(testDatabase,userDb);
        connectToDataBase();
        postgresDatabaseManager.dropDatabase(testDatabase);
        connectToDataBase();
        postgresDatabaseManager.dropUser(user);

    }

}
