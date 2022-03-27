import java.sql.*;

public class database {
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";
    static final String DB_NAME = "farms";
    static final String USERNAME = "root";
    static final String PASSWORD = "";

    public void databaseManager() {
        if (checkDriver() == true) {
            if (checkDatabaseExisted() == true) {
                dropDatabase();
            }
            createDatabase();
        }
    }

    protected boolean checkDriver() {
        boolean result = false;
        try {
            System.out.println("Check driver...");
            Class.forName(DRIVER);
            result = true;
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            System.out.println(ex.getMessage());
        }
        return result;
    }

    protected boolean checkDatabaseExisted() {
        boolean result = false;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String dbName = DB_NAME;
            if (conn != null) {
                System.out.println("Check if database '" + DB_NAME + "' existed...");
                rs = conn.getMetaData().getCatalogs();
                while (rs.next()) {
                    String catalogs = rs.getString(1);
                    if (dbName.equals(catalogs)) {
                        System.out.println("The database '" + dbName + "' exists");
                        result = true;
                    }
                }
            } else {
                System.out.println("unable to create database connection");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return result;
    }

    protected boolean createDatabase() {
        boolean result = false;
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                Statement stmt = conn.createStatement();) {
            String sql = "CREATE DATABASE " + DB_NAME;
            stmt.executeUpdate(sql);
            System.out.println("Database '" + DB_NAME + "' created successfully...");
            result = true;
        } catch (SQLException e) {
            System.out.println("Database '" + DB_NAME + "' created failed...");
            e.printStackTrace();
        }
        return result;
    }

    protected boolean dropDatabase() {
        boolean result = false;
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                Statement stmt = conn.createStatement();) {
            String sql = "DROP DATABASE " + DB_NAME;
            stmt.executeUpdate(sql);
            System.out.println("Database '" + DB_NAME + "' dropped successfully...");
            result = true;
        } catch (SQLException e) {
            System.out.println("Database '" + DB_NAME + "' dropped failed...");
            e.printStackTrace();
        }
        return result;
    }
}