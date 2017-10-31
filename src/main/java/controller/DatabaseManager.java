package controller;

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


        // insert
        dataBaseManager.insert();

        // getTableData
        String tableName = "public.users1";
        dataBaseManager.getTableData(tableName);


        // delete
        dataBaseManager.clear(tableName);

        // update
        dataBaseManager.update();

        // Create a Table

        dataBaseManager.createATable();
    }


    public  void update() {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("UPDATE public.users SET password = ? WHERE id > 3");
            String pass = "password_" + new Random().nextInt();
            ps.setString(1, pass);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void clear(final String tableName) {

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM " + tableName);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void createATable() {
        //TODO
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE COMPANY " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " AGE            INT     NOT NULL, " +
                    " ADDRESS        CHAR(50), " +
                    " SALARY         REAL)");
            stmt.close();
            System.out.println("Table created successfully");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


    }


    public DataSet[] getTableData(String tableName) {
        try {
            int size = getSize(tableName);

            Statement stmt = connection.createStatement();
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
            System.out.println(Arrays.toString(result));
            rs.close();
            stmt.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new DataSet[0];

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
        //TODO дороботать масиф
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


    public void insert() {

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO public.users (name, password)" +
                    "VALUES ('Stiven', 'Pupkin')");
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Invalid request");
            e.printStackTrace();
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
            System.out.println(String.format("Cant get connection for database:%s user:%s", database, user));
            e.printStackTrace();
            connection = null;
        }

    }


    public void insertData(String tableName,DataSet input) {
        // берет значения из  DataSet
        // вставлает их в таблицу

        try {
            Statement stmt = connection.createStatement();


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
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Invalid request");
            e.printStackTrace();
        }

    }


}