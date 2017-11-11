package ua.com.denisimusIT.SQLCmd.model;

import java.util.List;

public interface DatabaseManager {
    void connect(String database, String user, String password);

    void clearATable(String tableName);

    void createATable(String tableName);

    DataSet[] getTableData(String tableName);

    DataSet[] getTableColumns(String tableName, String columnsName);

    List<String> getTableNames();

    void insertData(String tableName, DataSet input);

    void updateTableData(String tableName, int id, DataSet newValue);

    void dropTable(String tableName);


    void createDatabase(String databaseName);


    String [] getDatabaseNames();


    void dropDatabase(final String databaseName);

    void selectDatabase();
}
