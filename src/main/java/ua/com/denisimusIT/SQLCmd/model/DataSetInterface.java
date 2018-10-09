package ua.com.denisimusIT.SQLCmd.model;

public interface DataSetInterface {
    void put(String name, Object value);

    Object[] getValues();

    String[] getNames();
}
