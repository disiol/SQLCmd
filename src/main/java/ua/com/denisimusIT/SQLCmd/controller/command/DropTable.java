package ua.com.denisimusIT.SQLCmd.controller.command;

public class DropTable implements Command {
    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process(String command) {

    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public String format() {
        return null;
    }
// TODO•Команда удаляет заданную таблицу
//•Формат: ua.com.denisimusIT.SQLCmd.controller.command.drop | tableName
//•где tableName - имя удаляемой таблицы
//•Формат вывода: текстовое сообщение с результатом выполнения операции
    }
