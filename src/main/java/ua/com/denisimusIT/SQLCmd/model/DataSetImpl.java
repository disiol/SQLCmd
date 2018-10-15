package ua.com.denisimusIT.SQLCmd.model;


import java.util.*;

public class DataSetImpl implements DataSet {


    private Map<String, Object> data = new LinkedHashMap<String, Object>();

    @Override
    public void put(String name, Object value) {
        data.put(name, value);
    }

    @Override
    public List<Object> getValues() {
        return new ArrayList<Object>(data.values());
    }

    @Override
    public List<Object> getNames() {
        List<Object> result = new ArrayList<>();
        for (Object name: data.keySet()) {
            result.add(name);
        }
        return result;
    }


    @Override
    public String toString() {
        return "{" +
                "names:" + getNames().toString() + ", " +
                "values:" + getValues().toString() +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSetImpl dataSetImpl = (DataSetImpl) o;
        return Objects.equals(data, dataSetImpl.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
