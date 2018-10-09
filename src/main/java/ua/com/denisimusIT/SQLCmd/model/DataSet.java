package ua.com.denisimusIT.SQLCmd.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by indigo on 21.08.2015.
 */
public class DataSet implements DataSetInterface {

    static class Data {
        private String name;
        private Object value;

        public Data(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Data data = (Data) o;

            if (name != null ? !name.equals(data.name) : data.name != null) return false;
            return value != null ? value.equals(data.value) : data.value == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }
    }

    public List<Data> data = new ArrayList<>();

    @Override
    public void put(String name, Object value) {
        for (Data aData : data) {
            if (aData.getName().equals(name)) {
                aData.value = value;
                return;
            }
        }

        data.add(new Data(name, value));
    }

    @Override
    public Object[] getValues() {
        Object[] result = new Object[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i).getValue();
        }
        return result;
    }


    @Override
    public String[] getNames() {
        String[] result = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i).getName();
        }
        return result;
    }


    @Override
    public String toString() {
        return "{" +
                "names:" + Arrays.toString(getNames()) + ", " +
                "values:" + Arrays.toString(getValues()) +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSet dataSet = (DataSet) o;
        return Objects.equals(data, dataSet.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
