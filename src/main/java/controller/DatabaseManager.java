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
        String sqlInsert = "INSERT INTO public.users (id , name, password)" +
                "VALUES ('101','Stiven', 'Pupkin')";
        insert(stmt, sqlInsert);

        // select
        stmt = connection.createStatement();
        String sqlSelect = "SELECT * FROM public.users WHERE id > 10";
        ResultSet rs = stmt.executeQuery(sqlSelect);
        while (rs.next()) {
            System.out.println("id:" + rs.getString("id"));
            System.out.println("name:" + rs.getString("name"));
            System.out.println("password:" + rs.getString("password"));
            System.out.println("-----");
        }
        rs.close();
        stmt.close();


        // delete
        stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM public.users " +
                "WHERE id > 10 AND id < 100");
        stmt.close();

        // update
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE public.users SET password = ? WHERE id > 3");
        String pass = "password_" + new Random().nextInt();
        ps.setString(1, pass);
        ps.executeUpdate();
        ps.close();

        connection.close();
    }

    public String[] getTableNames() {
        //TODO дороботать масиф
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");
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



    public static void insert(Statement stmt, String sqlInsert)  {
        try {
            stmt.executeUpdate(sqlInsert);
//            stmt.close();
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
        } catch (SQLException e) {
            System.out.println(String.format("Cant get connection for database:%s user:%s", database, user));
            e.printStackTrace();
            connection = null;
        }
        System.out.println("Opened database successfully");
    }

    public Connection getConnection() {
        return connection;
    }
}