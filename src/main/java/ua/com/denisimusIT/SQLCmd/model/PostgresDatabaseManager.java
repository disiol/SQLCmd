package ua.com.denisimusIT.SQLCmd.model;

import java.sql.*;
import java.util.Arrays;

public class PostgresDatabaseManager implements DatabaseManager {

    private Connection connection;


    @Override
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
            String error = e.toString();//TODO убрать при финальном релизе
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


    @Override
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

    @Override
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


    @Override
    public DataSet[] getTableData(String tableName) {
        Statement stmt = null;
        try {
            int size = getSize(tableName);
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            DataSet[] result = new DataSet[size];
            int index = 0;
            getData(rs, rsmd, result, index);
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

    @Override
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
            getData(rs, rsmd, result, index);

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

    private void getData(ResultSet rs, ResultSetMetaData rsmd, DataSet[] result, int index) throws SQLException {
        while (rs.next()) {
            DataSet dataSet = new DataSet();
            result[index++] = dataSet;
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
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

    @Override
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


    @Override
    public void insertData(String tableName, DataSet input) {
        // берет значения из  DataSet
        // вставлает их в таблицу

        Statement stmt = null;


        try {
            stmt = connection.createStatement();

            String tableNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");

            stmt.executeUpdate("INSERT INTO " + tableName + "(" + tableNames + ")" +
                    "VALUES (" + values + ")");
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


    @Override
    public void updateTableData(final String tableName, int id, DataSet newValue) {
        PreparedStatement ps = null;
        try {
            String tableNames = getNameFormated(newValue, "%s = ?,");

            String sql = "UPDATE public." + tableName + " SET " + tableNames + " WHERE id = ?";
            System.out.println(sql);
            ps = connection.prepareStatement(sql);

            int index = 1;
            for (Object value : newValue.getValues()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setObject(index, id);

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


    @Override
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


    private String getValuesFormated(DataSet input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    private String getNameFormated(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    //For tests
    public void dropDatabase() {
        //TODO
    }

    //For tests
    public void createDatabase(final String databaseName) {

        Statement stmt = null;
        try {
            System.out.println("Creating database...");
            stmt = connection.createStatement();

            String sql = "CREATE DATABASE " + databaseName;
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        } catch (SQLException se) {
            connection = null;
            se.printStackTrace();
        } catch (Exception e) {
            connection = null;
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }


    //for tests
    public void toShowNamesOfDatabases(){
        //TODO
    }


}