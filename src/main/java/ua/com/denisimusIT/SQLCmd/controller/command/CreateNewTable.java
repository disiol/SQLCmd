package ua.com.denisimusIT.SQLCmd.controller.command;

public class CreateNewTable implements Command {
    private String newLine = System.lineSeparator();

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create" + "|");
    }

    @Override
    public void process(String command) {


    }

    @Override
    public String description() {
        return "for creation of record in the table";
    }

    @Override
    public String format() {
        return "for create table without values:" + newLine
                + "create|tableName|column1, column2, value2,...,columnN, valueN" + newLine +
                "for create table with values:" + newLine
                + "create|tableName|column1 value1, column2, value2,...,columnN, valueN";
    }
//•Формат: ua.com.denisimusIT.SQLCmd.controller.command.create | tableName | column1 | column2 | ... | columnN
//•где: tableName - имя таблицы
//•column1 - имя первого столбца записи
//•column2 - имя второго столбца записи
//•columnN - имя n-го столбца записи
//•Формат вывода: текстовое сообщение с результатом выполнения
//операции
}
