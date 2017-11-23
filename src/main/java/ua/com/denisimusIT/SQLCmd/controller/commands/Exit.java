package ua.com.denisimusIT.SQLCmd.controller.commands;

import ua.com.denisimusIT.SQLCmd.view.View;

public class Exit implements Command {


    private View view;

    public Exit(View view) {
        this.view = view;
    }


    @Override
    public boolean canProcess(String command) {
        return command.equals("exit");
    }

    @Override
    public void Process(String command) {
        view.write("See you soon!");
        System.exit(0);
    }
// TODO•Команда для отключения от БД и выход из приложения
//•Формат: ua.com.denisimusIT.SQLCmd.controller.commands.exit (без параметров)
//•Формат вывода: текстовое сообщение с результатом выполнения операции
}