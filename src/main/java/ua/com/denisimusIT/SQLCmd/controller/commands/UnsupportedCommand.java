package ua.com.denisimusIT.SQLCmd.controller.commands;

import ua.com.denisimusIT.SQLCmd.controller.commands.Command;
import ua.com.denisimusIT.SQLCmd.view.View;

public class UnsupportedCommand implements Command {
    private View view;

    public UnsupportedCommand(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void Process(String command) {
        view.write("Nonexistent commands:" + command);
    }
}
