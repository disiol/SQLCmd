package controller;

import java.sql.*;
import java.util.Arrays;
import java.util.Random;

public class DatabaseManager {

    private Connection connection;

    public static void main(String[] argv) throws ClassNotFoundException, SQLException {

        String dataBase = "sqlCmd";
        String user = "postgres";
        String password = "1111";

        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.connect(dataBase, user, password);

        Connection connection = databaseManager.getConnection();

        // insert
        Statement stmt = connection.createStatement();
        String sqlInsert = "INSERT INTO public.users (name, password)" +
                "VALUES ('Stiven', 'Pupkin')";
        insert(stmt, sqlInsert);

        // select
        stmt = connection.createStatement();
        String sqlSelect = "SELECT * FROM public.users WHERE id > 10";
        select(stmt, sqlSelect);


        // delete
        delete(connection);

        // update
        update(connection, "UPDATE public.users SET password = ? WHERE id > 3");

        // Create a Table
        stmt = connection.createStatement();
        String sql = "CREATE TABLE COMPANY " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " AGE            INT     NOT NULL, " +
                " ADDRESS        CHAR(50), " +
                " SALARY         REAL)";
        createATable(stmt, sql);
    }

    public static void update(Connection connection, String sql) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            String pass = "password_" + new Random().nextInt();
            ps.setString(1, pass);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Statement delete(Connection connection) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM public.users " +
                    "WHERE id > 10 AND id < 100");
            stmt.close();
            return stmt;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;

    }

    public static void createATable(Statement stmt, String sql) {
        try {

            stmt.executeUpdate(sql);
            stmt.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Table created successfully");
    }


    public static void select(Statement stmt, String sqlSelect) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sqlSelect);
            while (rs.next()) {
                System.out.println("id:" + rs.getString("id"));
                System.out.println("name:" + rs.getString("name"));
                System.out.println("password:" + rs.getString("password"));
                System.out.println("-----");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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


    public static void insert(Statement stmt, String sqlInsert) {
        try {
            stmt.executeUpdate(sqlInsert);
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

    public Connection getConnection() {
        //TODO выпелить
        return connection;
    }
}