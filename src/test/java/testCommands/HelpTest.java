package testCommands;

import commands.Help;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denis Oleynyk on 06.10.17.
 * mail: deoniisii@gmail.com
 */
public class HelpTest {


    Help help = new Help();
    final String newline = System.lineSeparator();
@Test

      public void helpTest (){
    String expected = new StringBuilder().append("commands.Help: " + newline)
            .append("•commands.сonnect" + newline)
            .append("•Команда для подключения к соответствующей БД" + newline)
            .append("•Формат команды: connect | database | username | password" + newline)
            .append("•где: database - имя БД" + newline)
            .append("•username - имя пользователя БД" + newline)
            .append("•password - пароль пользователя БД" + newline)
            .append("•Формат вывода: текстовое сообщение с результатом выполнения операции" + newline + newline)
            .append("•commands.tables" + newline)
            .append("•Команда выводит список всех таблиц" + newline)
            .append("•Формат: commands.tables (без параметров)" + newline)
            .append("•Формат вывода:" + newline)
            .append("•в любом удобном формате" + newline)
            .append("•например [table1, table2, table3]" + newline + newline)
            .append("•commands.clearATable" + newline)
            .append("•Команда очищает содержимое указанной (всей) таблицы" + newline)
            .append("•Формат: commands.clearATable | tableName" + newline)
            .append("•где tableName - имя очищаемой таблицы" + newline)
            .append("•Формат вывода: текстовое сообщение с результатом выполнения операции" + newline + newline)
            .append("•commands.drop" + newline)
            .append("•Команда удаляет заданную таблицу" + newline)
            .append("•Формат: commands.drop | tableName" + newline)
            .append("•где tableName - имя удаляемой таблицы" + newline)
            .append("•Формат вывода: текстовое сообщение с результатом выполнения операции" + newline + newline)
            .append("•commands.insertData" + newline)
            .append("•Команда создает новую таблицу с заданными полями" + newline)
            .append("•Формат: commands.insertData | tableName | column1 | column2 | ... | columnN" + newline)
            .append("•где: tableName - имя таблицы" + newline)
            .append("•column1 - имя первого столбца записи" + newline)
            .append("•column2 - имя второго столбца записи" + newline)
            .append("•columnN - имя n-го столбца записи" + newline)
            .append("•Формат вывода: текстовое сообщение с результатом выполнения операции" + newline + newline)
            .append("•commands.find" + newline)
            .append("•Команда для получения содержимого указанной таблицы" + newline)
            .append("•Формат: commands.find | tableName•где tableName - имя таблицы" + newline)
            .append("•Формат вывода: табличка в консольном формате" + newline)
            .append("•+--------+---------+------------------+" + newline)
            .append("•+ col1   + col2    +        col3      +" + newline)
            .append("•+--------+---------+------------------+" + newline)
            .append("•+ 123    + stiven  +      pupkin      +" + newline)
            .append("•+ 345    + eva     +      pupkina     +" + newline)
            .append("•+--------+---------+------------------+" + newline + newline)
            .append("•commands.insertToTable" + newline)
            .append("•Команда для вставки одной строки в заданную таблицу" + newline)
            .append("•Формат: commands.insertToTable | tableName | column1 | value1 | column2 | value2 | ... |" + newline)
            .append("columnN | valueN" + newline)
            .append("•где: tableName - имя таблицы" + newline)
            .append("•column1 - имя первого столбца записи" + newline)
            .append("•value1 - значение первого столбца записи" + newline)
            .append("•column2 - имя второго столбца записи" + newline)
            .append("•value2 - значение второго столбца записи" + newline)
            .append("•columnN - имя n-го столбца записи" + newline)
            .append("•valueN - значение n-го столбца записи" + newline)
            .append("•Формат вывода: текстовое сообщение с результатом выполнения операции" + newline + newline)
            .append("•commands.update" + newline)
            .append("•Команда обновит запись, установив значение column2 = value2, для")
            .append("которой соблюдается условие column1 = value1" + newline)
            .append("•Формат: commands.update | tableName | column1 | value1 | column2 | value2" + newline)
            .append("•где: tableName - имя таблицы" + newline)
            .append("•column1 - имя столбца записи которое проверяется" + newline)
            .append("•value1 - значение которому должен соответствовать столбец" + newline)
            .append("column1 для обновляемой записи" + newline)
            .append("•column2 - имя обновляемого столбца записи" + newline)
            .append("•value2 - значение обновляемого столбца записи" + newline)
            .append("•columnN - имя n-го обновляемого столбца записи" + newline)
            .append("•valueN - значение n-го обновляемого столбца записи" + newline)
            .append("•Формат вывода: табличный, как при commands.find со старыми значениями обновленных записей." + newline + newline)
            .append("•commands.delete" + newline)
            .append("•Команда удаляет одну или несколько записей для которых")
            .append("соблюдается условие column = value" + newline)
            .append("•Формат: commands.delete | tableName | column | value" + newline)
            .append("•где: tableName - имя таблицы" + newline)
            .append("•Column - имя столбца записи которое проверяется" + newline)
            .append("•value - значение которому должен соответствовать столбец" + newline)
            .append("column1 для удаляемой записи" + newline)
            .append("•Формат вывода: табличный, как при commands.find со старыми значениями" + newline)
            .append("удаляемых записей." + newline + newline)
            .append("•commands.Help" + newline)
            .append("•Команда выводит в консоль список всех доступных команд•Формат: commands.Help (без параметров)" + newline)
            .append("•Формат вывода: текст, описания команд с любым форматированием" + newline + newline)
            .append("•commands.exit" + newline)
            .append("•Команда для отключения от БД и выход из приложения" + newline)
            .append("•Формат: commands.exit (без параметров)" + newline)
            .append("•Формат вывода: текстовое сообщение с результатом выполнения операции").toString();

    String actual = help.toString();


    assertEquals(expected, actual);
}

}
