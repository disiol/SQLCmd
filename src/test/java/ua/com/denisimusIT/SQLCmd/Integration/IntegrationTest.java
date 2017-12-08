package ua.com.denisimusIT.SQLCmd.Integration;

import org.junit.BeforeClass;
import org.junit.*;
import ua.com.denisimusIT.SQLCmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    private String newLine = System.lineSeparator();
    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;


    @Before
    public void setup() {
        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test

    public void testExit() {

        //given
        in.add("exit");
        //then
        Main.main(new String[0]);
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database , enter please a database name, " +
                "user name and the password in a format: connect|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                "See you soon!" + newLine;
        assertEquals("testExit", expected, actual);
    }

    @Test

    public void testHelp() {

        //given
        in.add("help");
        //then
        Main.main(new String[0]);
        //wen
        String actual = getData();
        String expected = "Welcome to SQLCmd! =)" + newLine +
                "For connect to database , enter please a database name, user name and the password in a format: connect|database|username|password" + newLine +
                "or help command for a help call" + newLine +
                "Help: " + newLine +
                "•сonnect" + newLine +
                "•Команда для подключения к соответствующей БД" + newLine +
                "•Формат команды: connect|database|username|password" + newLine +
                "•где: database - имя БД" + newLine +
                "•username - имя пользователя БД" + newLine +
                "•password - пароль пользователя БД" + newLine +
                "•Формат вывода: текстовое сообщение с результатом выполнения операции" + newLine +
                "" + newLine +
                "•tables" + newLine +
                "•Команда выводит список всех таблиц" + newLine +
                "•Формат: tables (без параметров)" + newLine +
                "•Формат вывода:" + newLine +
                "•в любом удобном формате" + newLine +
                "•например [table1, table2, table3]" + newLine +
                "" + newLine +
                "•clearATable" + newLine +
                "•Команда очищает содержимое указанной (всей) таблицы" + newLine +
                "•Формат: clearATable | tableName" + newLine +
                "•где tableName - имя очищаемой таблицы" + newLine +
                "•Формат вывода: текстовое сообщение с результатом выполнения операции" + newLine +
                "" + newLine +
                "•drop" + newLine +
                "•Команда удаляет заданную таблицу" + newLine +
                "•Формат: drop | tableName" + newLine +
                "•где tableName - имя удаляемой таблицы" + newLine +
                "•Формат вывода: текстовое сообщение с результатом выполнения операции" + newLine +
                "" + newLine +
                "•insertData" + newLine +
                "•Команда создает новую таблицу с заданными полями" + newLine +
                "•Формат: insertData | tableName | column1 | column2 | ... | columnN" + newLine +
                "•где: tableName - имя таблицы" + newLine +
                "•column1 - имя первого столбца записи" + newLine +
                "•column2 - имя второго столбца записи" + newLine +
                "•columnN - имя n-го столбца записи" + newLine +
                "•Формат вывода: текстовое сообщение с результатом выполнения операции" + newLine +
                "" + newLine +
                "•find" + newLine +
                "•Команда для получения содержимого указанной таблицы" + newLine +
                "•Формат: find | tableName•где tableName - имя таблицы" + newLine +
                "•Формат вывода: табличка в консольном формате" + newLine +
                "•+--------+---------+------------------+" + newLine +
                "•+ col1   + col2    +        col3      +" + newLine +
                "•+--------+---------+------------------+" + newLine +
                "•+ 123    + stiven  +      pupkin      +" + newLine +
                "•+ 345    + eva     +      pupkina     +" + newLine +
                "•+--------+---------+------------------+" + newLine +
                "" + newLine +
                "•insertToTable" + newLine +
                "•Команда для вставки одной строки в заданную таблицу" + newLine +
                "•Формат: insertToTable | tableName | column1 | value1 | column2 | value2 | ... |" + newLine +
                "columnN | valueN" + newLine +
                "•где: tableName - имя таблицы" + newLine +
                "•column1 - имя первого столбца записи" + newLine +
                "•value1 - значение первого столбца записи" + newLine +
                "•column2 - имя второго столбца записи" + newLine +
                "•value2 - значение второго столбца записи" + newLine +
                "•columnN - имя n-го столбца записи" + newLine +
                "•valueN - значение n-го столбца записи" + newLine +
                "•Формат вывода: текстовое сообщение с результатом выполнения операции" + newLine +
                "" + newLine +
                "•update" + newLine +
                "•Команда обновит запись, установив значение column2 = value2, длякоторой соблюдается условие column1 = value1" + newLine +
                "•Формат: update | tableName | column1 | value1 | column2 | value2" + newLine +
                "•где: tableName - имя таблицы" + newLine +
                "•column1 - имя столбца записи которое проверяется" + newLine +
                "•value1 - значение которому должен соответствовать столбец" + newLine +
                "column1 для обновляемой записи" + newLine +
                "•column2 - имя обновляемого столбца записи" + newLine +
                "•value2 - значение обновляемого столбца записи" + newLine +
                "•columnN - имя n-го обновляемого столбца записи" + newLine +
                "•valueN - значение n-го обновляемого столбца записи" + newLine +
                "•Формат вывода: табличный, как при find со старыми значениями обновленных записей." + newLine +
                "" + newLine +
                "•delete" + newLine +
                "•Команда удаляет одну или несколько записей для которыхсоблюдается условие column = value" + newLine +
                "•Формат: delete | tableName | column | value" + newLine +
                "•где: tableName - имя таблицы" + newLine +
                "•Column - имя столбца записи которое проверяется" + newLine +
                "•value - значение которому должен соответствовать столбец" + newLine +
                "column1 для удаляемой записи" + newLine +
                "•Формат вывода: табличный, как при find со старыми значениями" + newLine +
                "удаляемых записей." + newLine +
                "" + newLine +
                "•help" + newLine +
                "•Команда выводит в консоль список всех доступных команд•Формат: Help (без параметров)" + newLine +
                "•Формат вывода: текст, описания команд с любым форматированием" + newLine +
                "" + newLine +
                "•exit" + newLine +
                "•Команда для отключения от БД и выход из приложения" + newLine +
                "•Формат: exit (без параметров)" + newLine +
                "•Формат вывода: текстовое сообщение с результатом выполнения операции" + newLine +
                "enter please command or help command for a help call" + newLine +
                "See you soon!" + newLine;
        assertEquals("testHelp", expected, actual);
    }

    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }
}
