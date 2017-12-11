package ua.com.denisimusIT.SQLCmd.controller.commands.Exit;

import ua.com.denisimusIT.SQLCmd.controller.commands.Command;
import ua.com.denisimusIT.SQLCmd.controller.commands.Exit.Exeption.ExitException;
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
    public void process(String command) {
        view.write("See you soon!");
        throw new ExitException();
    }

}