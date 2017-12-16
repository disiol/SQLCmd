package ua.com.denisimusIT.SQLCmd.model;

import java.util.Arrays;

public class DataSet {
    final String newline = System.lineSeparator();


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
    }

    private Data[] data = new Data[100]; // TODO remove magic number 100, make colekhen
    private int index = 0;

    public void put(String name, Object value) {
        data[index++] = new Data(name, value);
    }

    public Object[] getValues() {
        Object[] result = new Object[index];
        for (int i = 0; i < index; i++) {
            result[i] = data[i].getValue();
        }
        return result;
    }

    public String[] getNames() {
        String[] result = new String[index];
        for (int i = 0; i < index; i++) {
            result[i] = data[i].getName();
        }
        return result;
    }

    @Override
    public String toString() {
        return "DataSet{" + newline +
                "names:" + Arrays.toString(getNames()) + newline +
                "values:" + Arrays.toString(getValues()) + newline +
                "}";
    }
}