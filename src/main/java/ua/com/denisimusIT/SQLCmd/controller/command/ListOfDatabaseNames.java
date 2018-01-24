package ua.com.denisimusIT.SQLCmd.controller.command;

public class ListOfDatabaseNames implements Command {
    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process(String command) {

    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public String format() {
        return null;
    }
    //TODO команда для отоброжения списка базы данных
    //datname : [template1, template0, postgres, sqlCmd]


}
