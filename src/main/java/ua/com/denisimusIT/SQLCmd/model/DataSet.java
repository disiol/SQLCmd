package ua.com.denisimusIT.SQLCmd.model;

import java.util.List;

public interface DataSet {
    void put(String name, Object value);

    List<Object> getValues();

    List<Object> getNames();
}
