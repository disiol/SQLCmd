package controller;

import javax.swing.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Random;

public class DatabaseManager {

    private Connection connection;

    public static void main(String[] argv) {

        String dataBase = "sqlCmd";
        String user = "postgres";
        String password = "1111";

        DatabaseManager dataBaseManager = new DatabaseManager();
        dataBaseManager.connect(dataBase, user, password);

        Connection connection = dataBaseManager.getConnection();


        // getTableData
        String tableName = "public.users1";
        dataBaseManager.getTableData(tableName);


        // delete
        dataBaseManager.clearATable(tableName);

        // update
        dataBaseManager.update();

        // Create a Table

        dataBaseManager.createATable("COMPANY");
    }


    public void update() {
        //TODO прием аргументов для обновления
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("UPDATE public.users SET password = ? WHERE id > 3");
            String pass = "password_" + new Random().nextInt();
            ps.setString(1, pass);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public void connect(String database, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Please add jdbc jar to project.");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + database, user,
                    password);
            System.out.println("Opened database successfully");
        } catch (SQLException e) {
            String message = String.format("Cant get connection for database:%s user:%s", database, user);
            e.printStackTrace();
            String error = e.toString();
            String errorUser = "org.postgresql.util.PSQLException: FATAL: password authentication failed for user \"" + user + "\"";
            String errorDatabase = "org.postgresql.util.PSQLException: FATAL: database \"" + database + "\" does not exist";
            if (error.equals(errorUser)) {
                System.out.println(message + ", not the correct password or user name");
            } else if (error.equals(errorDatabase)) {
                System.out.println(message + " ,database does not exist");
            } else {
                System.out.println(message);

            }
            connection = null;
        }

    }

    public Connection getConnection() {

        //TODO выпелить
        return connection;
    }

    public void clearATable(final String tableName) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM " + tableName);
            System.out.println(tableName + " cleared successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }


    }

    public void createATable(final String tableName) {
        //TODO прием аргументов
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE " + tableName +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PASSWORD       TEXT     NOT NULL)");
            stmt.close();
            System.out.println("Table " + tableName + " created successfully");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    public DataSet[] getTableData(String tableName) {
        Statement stmt = null;
        try {
            int size = getSize(tableName);
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            DataSet[] result = new DataSet[size];
            int index = 0;
            while (rs.next()) {
                DataSet dataSet = new DataSet();
                result[index++] = dataSet;
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
            System.out.println(Arrays.toString(result));//TODO потом выпелить
            rs.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new DataSet[0];

        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }


    }

    public DataSet[] getTableColumns(String tableName, final String columnsName) {
        //TODO
        Statement stmt = null;
        ResultSet rs = null;
        try {
            int size = getSize(tableName);
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT " + columnsName + " FROM " + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            DataSet[] result = new DataSet[size];
            int index = 0;
            while (rs.next()) {
                DataSet dataSet = new DataSet();
                result[index++] = dataSet;
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
            //System.out.println(Arrays.toString(result));//TODO потом выпелить

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new DataSet[0];

        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    if (rs != null) {
                        rs.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }


    }


    private int getSize(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
        rsCount.next();
        int size = rsCount.getInt(1);
        rsCount.close();
        return size;
    }

    public String[] getTableNames() {
        //TODO дороботать масиф и аргуметы поиска
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' " +
                    "AND table_type='BASE TABLE'");
            String[] tables = new String[100];
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("table_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
            rs.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }


    public void insertData(String tableName, DataSet input) {
        // берет значения из  DataSet
        // вставлает их в таблицу

        Statement stmt = null;


        try {
            stmt = connection.createStatement();


            String tableNamesHad = "";
            String values = "";

            for (String name : input.getNames()) {

                tableNamesHad += name + ",";


            }

            tableNamesHad = tableNamesHad.substring(0, tableNamesHad.length() - 1);

            for (Object value : input.getValues()) {

                values += "'" + value.toString() + "'" + ",";

            }

            values = values.substring(0, values.length() - 1);


            stmt.executeUpdate("INSERT INTO " + tableName + "(" + tableNamesHad + ")" +
                    "VALUES (" + values + ") ");
        } catch (SQLException e) {
            System.out.println("Invalid request");
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }

    }


    public void dropTable(final String tableName) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DROP TABLE " + tableName + " ");
            System.out.println("Table " + tableName + " deleted in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    //For tests
    public void dropDatabase() {
        //TODO
    }

    public void createDatabase() {
        //TODO
    }

}