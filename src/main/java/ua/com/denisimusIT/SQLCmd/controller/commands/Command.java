package ua.com.denisimusIT.SQLCmd.controller.commands;

public interface Command {
    boolean canProcess(String command);

    void Process(String command);
}
