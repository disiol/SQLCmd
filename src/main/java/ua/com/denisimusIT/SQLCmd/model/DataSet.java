package ua.com.denisimusIT.SQLCmd.model;

public interface DataSet {
    void put(String name, Object value);

    Object[] getValues();

    String[] getNames();
}
