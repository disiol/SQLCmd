package ua.com.denisimusIT.SQLCmd.controller.command.exit;

import ua.com.denisimusIT.SQLCmd.controller.command.Command;
import ua.com.denisimusIT.SQLCmd.controller.command.exit.Exeption.ExitException;
import ua.com.denisimusIT.SQLCmd.view.View;

public class Exit implements Command {


    private View view;

    public Exit(View view) {
        this.view = view;
    }


    @Override
    public boolean canProcess(String command) {
        String exit = format();
        return command.equals(exit);
    }

    @Override
    public void process(String command) {
        view.write("See you soon!");
        throw new ExitException();
    }

    @Override
    public String description() {

        return "for an output from the program";
    }

    @Override
    public String format() {
        return "exit";
    }

}