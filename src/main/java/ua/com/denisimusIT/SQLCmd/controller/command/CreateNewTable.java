package ua.com.denisimusIT.SQLCmd.controller.command;

public class CreateNewTable implements Command {
    //TODO


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
//•TODO Команда создает новую таблицу с заданными полями
//•Формат: ua.com.denisimusIT.SQLCmd.controller.command.create | tableName | column1 | column2 | ... | columnN
//•где: tableName - имя таблицы
//•column1 - имя первого столбца записи
//•column2 - имя второго столбца записи
//•columnN - имя n-го столбца записи
//•Формат вывода: текстовое сообщение с результатом выполнения
//операции
    }
