package ua.com.denisimusIT.SQLCmd.model;

import java.util.List;

public interface DatabaseManager {
    void connect(String databaseName, String userName, String password);

    void clearATable(String tableName);

    void createATable(String tableName);

    DataSet[] getTableData(String tableName);

    DataSet[] getTableColumns(String tableName, String columnsName);

    List<String> getTableNames();

    void insertData(String tableName, DataSet input);

    void updateTableData(String tableName, int id, DataSet newValue);

    void dropTable(String tableName);


    void createDatabase(String databaseName);


    List<String> getDatabaseNames();


    void dropDatabase(final String databaseName);

    void selectDatabase(String databaseName);

    List<String> currentDatabase();

    void createUser(String newUser, String newPassword);

    void giveAccess(String databaseName, String userName);

}
