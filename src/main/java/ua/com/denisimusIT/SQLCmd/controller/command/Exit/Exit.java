package ua.com.denisimusIT.SQLCmd.controller.command.Exit;

import ua.com.denisimusIT.SQLCmd.controller.command.Command;
import ua.com.denisimusIT.SQLCmd.controller.command.Exit.Exeption.ExitException;
import ua.com.denisimusIT.SQLCmd.view.View;

public class Exit implements Command {


    private View view;

    public Exit(View view) {
        this.view = view;
    }


    @Override
    public boolean canProcess(String command) {
        return command.equals(format());
    }

    @Override
    public void process(String command) {
        view.write("See you soon!");
        throw new ExitException();
    }

    @Override
    public String description() {
        //TODO

        return null;
    }

    @Override
    public String format() {
        return "exit";
    }

}