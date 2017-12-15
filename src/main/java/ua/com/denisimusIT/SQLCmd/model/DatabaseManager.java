package ua.com.denisimusIT.SQLCmd.model;

import java.util.List;

public interface DatabaseManager {
    void connectToDatabase(String databaseName, String userName, String password);

    void clearATable(String tableName);

    void createATable(String tableName, String columnsValues);

    DataSet[] getTableData(String tableName);

    DataSet[] getTableColumn(String tableName, String columnsName);

    List<String> getTableNames();

    void insertData(String tableName, DataSet input);

    void updateTableData(String tableName, int id, DataSet newValue);

    void dropTable(String tableName);


    void createDatabase(String databaseName);


    List<String> getDatabaseNames();


    void dropDatabase(final String databaseName);

    void selectDatabase(String databaseName);

    void disconnectOfDatabase(String databaseName);

    List<String> currentDatabase();

    void createUser(String newUser, String newPassword);

    void dropUser(String userName);

    void giveAccessUserToTheDatabase(String databaseName, String userName);

    List<String> getTableColumns(String tableName);

    boolean isConnected();

    void createEmptyATable(String tableName);
}
