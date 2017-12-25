package ua.com.denisimusIT.SQLCmd.controller.command;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class CreateNewTable implements Command {
    private String newLine = System.lineSeparator();
    private View view;
    private DatabaseManager manager;

    public CreateNewTable(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create" + "|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");

        //TODO проверку
//        if (data.length != count()) {
//            throw new IllegalArgumentException(String.format("The number of parameters partitioned by the character '|' " +
//                    "is incorrect, it is expected  %s, but is: %s", count(), data.length));
//        }
        String tableName = data[1];
        String columnsValues = data[2];
        manager.createATable(tableName,columnsValues);
        view.write("The table: " + tableName.toString() + " is created successfully");
    }

    @Override
    public String description() {
        return "for creation of record in the table";
    }

    @Override
    public String format() {
        return "For create table with columns:" + newLine
                + "create|tableName|column1 column type, column2 column type,...,columnN column type" + newLine +
                "\tExample: " + newLine +
                "\t\tcreate|tableName|id int  NOT NULL, name TEXT NOT NULL, PASSWORD  TEXT  NOT NULL" + newLine +
                "for create table without columns:" + newLine
                + "create|tableName|" + newLine;
    }

    private int count() {
        return format().split("\\|").length;
    }

//•Формат: ua.com.denisimusIT.SQLCmd.controller.command.create | tableName | column1 | column2 | ... | columnN
//•где: tableName - имя таблицы
//•column1 - имя первого столбца записи
//•column2 - имя второго столбца записи
//•columnN - имя n-го столбца записи
//•Формат вывода: текстовое сообщение с результатом выполнения
//операции
}
