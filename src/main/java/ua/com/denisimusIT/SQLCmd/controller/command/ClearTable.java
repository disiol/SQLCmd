package ua.com.denisimusIT.SQLCmd.controller.command;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class ClearTable implements Command {


    private DatabaseManager manager;
    private View view;
    private String newLine = System.lineSeparator();


    public ClearTable(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear" + "|");
    }

    @Override
    public void process(String command) {
        // TODO а если юзер случайно ввел команду? Может переспросить его?

        String[] data = command.split("\\|");


        int minQuantity = 2;
        if (data.length != minQuantity) {
            throw new IllegalArgumentException(String.format("The number of parameters partitioned by the character '|' " +
                    "is incorrect, it is expected  %s, but is: %s", minQuantity, data.length) + newLine +
                    "\texample: create|tableName|column1 column type, column2 column type,...,columnN column typ");
        }
        String tableName = data[1];
        manager.clearATable("\"" + tableName + "\"");
        view.write("The table: " + tableName.toString() + " is cleared successfully");

    }

    @Override
    public String description() {
        return "for cleaning of all table";
    }

    @Override
    public String format() {
        return "clear|tableName";
    }
//•Формат: ua.com.denisimusIT.SQLCmd.controller.command.clearATable | tableName
//•где tableName - имя очищаемой таблицы
//•Формат вывода: текстовое сообщение с результатом выполнения операции
}
