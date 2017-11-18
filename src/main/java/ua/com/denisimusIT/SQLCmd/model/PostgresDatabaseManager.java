package ua.com.denisimusIT.SQLCmd.model;


import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PostgresDatabaseManager implements DatabaseManager {

    private Connection connection;


    @Override
    public void connect(String databaseName, String userName, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add jdbc jar to project.", e);

        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + databaseName, userName,
                    password);
            System.out.println("Opened database successfully");
        } catch (SQLException e) {
            connection = null;

            throw new RuntimeException(
                    String.format("Cant get connection for model:%s user:%s", databaseName, userName), e);
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
        //TODO прием имен колонок
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
    public List<String> getTableNames() {
        //TODO дороботать  аргуметы поиска добавить выбот нужной схемы
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

            String tableNames = getNameFormatted(input, "%s,");
            String values = getValuesFormatted(input, "'%s',");

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
        //TODO добавить выбор схемы
        PreparedStatement ps = null;
        try {
            String tableNames = getNameFormatted(newValue, "%s = ?,");

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
            System.out.println("Creating database...");
            stmt = connection.createStatement();

            String sql = "DROP DATABASE " + databaseName;
            stmt.executeUpdate(sql);
            System.out.println("Database drop successfully...");
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
    public void selectDatabase(String databaseName) {
        //TODO
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
            System.out.println("Creating user...");
            stmt = connection.createStatement();

            String sql = "CREATE USER " + newUser + " WITH password " + "'" + newPassword + "'";
            stmt.executeUpdate(sql);
            System.out.printf("It is created user: %s with the password: %s", newUser, newPassword);
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


}