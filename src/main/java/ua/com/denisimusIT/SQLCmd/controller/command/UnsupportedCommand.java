package ua.com.denisimusIT.SQLCmd.controller.command;

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
    public void process(String command) {
        view.write("Nonexistent command:" + command);
    }

    @Override
    public String description() {
        //TODO

        return null;
    }

    @Override
    public String format() {
        //TODO

        return null;
    }
}
