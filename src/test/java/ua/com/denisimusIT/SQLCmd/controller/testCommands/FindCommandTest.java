package ua.com.denisimusIT.SQLCmd.controller.testCommands;

import com.sun.corba.se.impl.encoding.CodeSetConversion;
import ua.com.denisimusIT.SQLCmd.controller.Main;
import ua.com.denisimusIT.SQLCmd.controller.MainController;
import ua.com.denisimusIT.SQLCmd.model.DataSet;
import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.model.PostgresDatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class FindCommandTest {

    final String newline = System.lineSeparator();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private DatabaseManager manager = new PostgresDatabaseManager();
    private String tableName;
    private MainController mainController;


    private String database = "postgres";
    private String user = "postgres";
    private String password = "1111";


    @Test
    public void insertDataCorrect_id_101_name_Stiven_password_Pupkin() {
        System.setOut(new PrintStream(outContent));
        mainController.run();
        String connect = database + "|" + user + "|" + password;
        System.setIn(new ByteArrayInputStream(connect.getBytes()));

        String findComand = "find|" + tableName;
        System.setIn(new ByteArrayInputStream(connect.getBytes()));

        //TODO
        tableName = "users1";

        // when


        manager.createATable(tableName,"");
        List<String> tableNames = manager.getTableNames();
        tableNames.add(tableName);
        Collections.sort(tableNames);

        String expected = "[" + tableName + "]";
        Object[] actual = manager.getTableNames().toArray();
        assertEquals("сreateTableCompany", expected, Arrays.toString(actual));


        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        manager.insertData(tableName, input);

        //then
        String separator = "•+--------------------------------------------------";


        String expected2 = new StringBuilder().append(separator + newline).append("id+name+password" + newline)
                .append(separator + "Stiven+Pupkin+" + newline)
                .append("password:" + newline).append("--------------------------") + newline.toString();
        String actual2 = outContent.toString();

        assertEquals(expected2, actual2);


    }


    @After
    public void deleteTable() {
        //TODO  drop  таблицу с данами
        manager.dropTable(tableName);
    }
}
