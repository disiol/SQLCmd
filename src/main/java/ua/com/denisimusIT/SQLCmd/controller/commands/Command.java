package ua.com.denisimusIT.SQLCmd.controller.commands;

public interface Command {
    boolean canProcess(String command);

    void process(String command);
}
