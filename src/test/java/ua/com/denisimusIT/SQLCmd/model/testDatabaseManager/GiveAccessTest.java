package ua.com.denisimusIT.SQLCmd.model.testDatabaseManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class GiveAccessTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    final String newline = System.lineSeparator();

    DatabaseManager postgresDatabaseManager = new PostgresDatabaseManager();

    String dataBase = "postgres";
    String userDb = "postgres";
    String password = "1111";
    private String testDatabase = "testdatabase";

    String user = "den";

    @Before

    public void before() {
        connectToDataBase();
        CrateDb();
        ConnectToCrateDb(userDb, password);
        postgresDatabaseManager.createUser(user, password);

    }

    private void connectToDataBase() {
        postgresDatabaseManager.connect(dataBase, userDb, password);
        //создать и выбрать баззу
    }

    private void ConnectToCrateDb(String userDb, String password) {
        postgresDatabaseManager.connect(testDatabase, userDb, password);
    }

    private void CrateDb() {
        postgresDatabaseManager.createDatabase(testDatabase);
    }

    //TODO

    @Test

    public void GiveAccessTest() {
        System.setOut(new PrintStream(outContent));
        connectToDataBase();
        postgresDatabaseManager.giveAccessUserToTheDatabase(testDatabase, user);

        String expected = "Opened database successfully" + newline +
                "Access user: " + user + " to the database: " + testDatabase + " it is allow";
        String actual = outContent.toString();
        assertEquals("GiveAccess", expected, actual);

    }

    @After
    public void dropDatabase() {
       // ConnectToCrateDb(user, password);
        connectToDataBase();
        postgresDatabaseManager.dropDatabase(testDatabase);
        connectToDataBase();
        postgresDatabaseManager.dropUser(user);

    }

}
