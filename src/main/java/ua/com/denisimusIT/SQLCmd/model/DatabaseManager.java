package ua.com.denisimusIT.SQLCmd.model;

public interface DatabaseManager {
    void connect(String database, String user, String password);

    void clearATable(String tableName);

    void createATable(String tableName);

    DataSet[] getTableData(String tableName);

    DataSet[] getTableColumns(String tableName, String columnsName);

    String[] getTableNames();

    void insertData(String tableName, DataSet input);

    void updateTableData(String tableName, int id, DataSet newValue);

    void dropTable(String tableName);

    //For tests
    void createDatabase(String databaseName);

    //for tests
    void ShowDatabase();

    //For tests
    void dropDatabase();
}
