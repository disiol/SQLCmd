package ua.com.denisimusIT.SQLCmd.controller.command;

public interface Command {
    boolean canProcess(String command);

    void process(String command);

    String description();

    String format();
}
