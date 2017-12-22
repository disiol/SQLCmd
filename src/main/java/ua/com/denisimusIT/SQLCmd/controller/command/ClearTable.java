package ua.com.denisimusIT.SQLCmd.controller.command;

import ua.com.denisimusIT.SQLCmd.model.DatabaseManager;
import ua.com.denisimusIT.SQLCmd.view.View;

public class ClearTable implements Command {


    private DatabaseManager manager;
    private View view;

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
        //TODO •Команда очищает содержимое указанной (всей) таблицы
        // TODO а если юзер случайно ввел команду? Может переспросить его?

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
