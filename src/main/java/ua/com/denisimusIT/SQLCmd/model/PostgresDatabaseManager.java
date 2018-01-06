package ua.com.denisimusIT.SQLCmd.model;


import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PostgresDatabaseManager implements DatabaseManager {

    private Connection connection;
    private final static String NEW_LINE = System.lineSeparator();

    //TODO clouse resurses

    @Override
    public void connectToDatabase(String databaseName, String userName, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add jdbc jar to project.", e);

        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + databaseName, userName, password);
        } catch (SQLException e) {
            connection = null;

            throw new RuntimeException(
                    String.format("Cant get connection for model:%s user:%s", databaseName, userName), e);
        }

    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }


    @Override
    public void clearATable(final String tableName) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM " + tableName);
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
    public void createATable(final String tableName, String columnsValues) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS  " + tableName +
                    "(" + columnsValues + ")";
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //   System.exit(0);
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM "  + tableName );
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
    public DataSet[] getTableColumn(String tableName, final String columnsName) {
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


    private int getSize(String tableName) {
        Statement stmt;
        int size = 0;
        try {
            stmt = connection.createStatement();
            ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM " +   tableName );
            rsCount.next();
            size = rsCount.getInt(1);
            rsCount.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return size;
    }

    @Override
    public List<String> getTableNames() {
        //TODO добавить выбор нужной схемы
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' " +
                    "AND table_type='BASE TABLE'");
            List<String> tables = new LinkedList<>();
            while (rs.next()) {
                tables.add(rs.getString("table_name"));
            }
            rs.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void insertData(String tableName, DataSet input) {
        // берет значения из  DataSet
        // вставлает их в таблицу

        Statement stmt = null;


        try {
            stmt = connection.createStatement();

            String columnName = getNameFormatted(input, "%s,");
            String values = getValuesFormatted(input, "'%s',");

            String sql = "INSERT INTO " + tableName + "(" + columnName + ")" + "VALUES (" + values + ")";
            stmt.executeUpdate(sql);
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
        //TODO добавить выбор схемы и колонки

        PreparedStatement ps = null;
        try {
            String tableNames = getNameFormatted(newValue, "%s = ?,");

            String sql = "UPDATE public." + tableName + " SET " + tableNames + " WHERE id = ?";
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
            //if(tableName =null)
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


    private String getValuesFormatted(DataSet input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    private String getNameFormatted(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }


    @Override
    public void createDatabase(final String databaseName) {

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE DATABASE " + databaseName;
            stmt.executeUpdate(sql);
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


    @Override
    public List<String> getDatabaseNames() {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT datname FROM pg_database");
            List<String> tables = new LinkedList<>();
            while (rs.next()) {
                tables.add(rs.getString("datname"));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
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
        return null;
    }

    @Override
    public void dropDatabase(final String databaseName) {

        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            String sql = "DROP DATABASE " + databaseName;
            stmt.executeUpdate(sql);
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


    @Override
    public void disconnectOfDatabase(String databaseName) {

        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            String sql = "SELECT pg_terminate_backend(pg_stat_activity.pid)" + NEW_LINE +
                    "FROM pg_stat_activity" + NEW_LINE +
                    "WHERE pg_stat_activity.datname = " + "'" + databaseName + "'" + NEW_LINE +
                    "  AND pid <> pg_backend_pid();" + NEW_LINE;
            stmt.execute(sql);
            System.out.println("Disconnect of database: " + databaseName + " successfully");
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

    @Override
    public List<String> currentDatabase() {

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT current_database();");
            List<String> databaseName = new LinkedList<>();
            while (rs.next()) {
                databaseName.add(rs.getString("current_database"));
            }
            return databaseName;
        } catch (SQLException e) {
            e.printStackTrace();
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
        return null;
    }


    @Override
    public void createUser(String newUser, String newPassword) {
        //TODO
        Statement stmt = null;
        try {
            System.out.println("Creating user: " + newUser);
            stmt = connection.createStatement();

            String sql = "CREATE USER " + newUser + " WITH password " + "'" + newPassword + "'";
            stmt.executeUpdate(sql);
            System.out.printf("It is created user: %s with the password: %s" + NEW_LINE, newUser, newPassword);
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

    @Override
    public void dropUser(String userName) {
        Statement stmt = null;
        try {
            System.out.println("Creating user...");
            stmt = connection.createStatement();

            String sql = "DROP USER " + userName;
            stmt.executeUpdate(sql);
            System.out.printf("The user:%s is delete", userName);
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

    @Override
    public void giveAccessUserToTheDatabase(String databaseName, String userName) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "GRANT ALL ON DATABASE " + databaseName + " TO  " + userName;
            stmt.executeUpdate(sql);
            System.out.printf("Access user: %s to the database: %s it is allow", userName, databaseName);
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

    @Override
    public List<String> getTableColumns(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.columns WHERE table_schema='public' " +
                    "AND table_name='" + tableName + "'");
            List<String> tables = new LinkedList<>();
            while (rs.next()) {
                tables.add(rs.getString("column_name"));
            }
            rs.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }


}