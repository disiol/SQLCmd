package ua.com.denisimusIT.SQLCmd.controller.commands;
// Команда выводит в консоль список всех доступных команд•Формат: Help (без параметров)
//•Формат вывода: текст, описания команд с любым форматированием

import ua.com.denisimusIT.SQLCmd.view.View;

public class Help implements Command {
    private View view;
    final String newline = System.lineSeparator();


    public Help(View view) {
        this.view = view;
    }


    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {//TODO
        view.write("The existing commands:");

        view.write("\tconnect|databaseName|userName|password");
        view.write("\t\tfor connection to the database with which we will work");

        view.write("\tlist");
        view.write("\t\tfor connection to the database with which we will work");

        view.write("\tclear|tableName");
        view.write("\t\tfor cleaning of all table"); // TODO а если юзер случайно ввел команду? Может переспросить его?

        view.write("\tcreate|tableName|column1|value1|column2|value2|...|columnN|valueN");
        view.write("\t\tfor creation of record in the table");

        view.write("\tfind|tableName");
        view.write("\t\tfor receiving contents of the table 'tableName'");

        view.write("\thelp");
        view.write("\t\tfor an output of this list to the screen");

        view.write("\texit");
        view.write("\t\tfor an output from the program");
    }
}
