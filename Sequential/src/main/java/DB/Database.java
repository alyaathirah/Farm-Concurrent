package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;


// import java.sql.*;

public class Database {
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String SQL_URL = "jdbc:mysql://localhost/";
    static final String DB_NAME = "farms";
    static final String USERNAME = "root";
    static final String PASSWORD = "";

    // manage all database related function here

    
    public Database() {
    }

    public void databaseManager() {
        if (checkDriver() == true) {
            if (checkDatabaseExisted() == true) {
                dropDatabase();
            }
            createDatabase();
        }
    }


    // check driver before any other function about database
    private boolean checkDriver() {
        boolean result = false;
        try {
            // System.out.println("Check driver...");
            Class.forName(DRIVER);
            result = true;
        } catch (ClassNotFoundException ex) {
            // System.out.println("Error: unable to load driver class!");
            // System.out.println(ex.getMessage());
            
        }
        return result;
    }

    //get connection
    protected static Connection getConnection(String url, String username, String password) throws SQLException {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Make sure to open xampp or the database is created");
            System.exit(1);
            return null;
        }
        
    }

    //get sql connnection
    protected Connection getSQLConnection() throws SQLException {
        return getConnection(SQL_URL, USERNAME, PASSWORD);
    }

    // check database exists
    private boolean checkDatabaseExisted() {
        boolean result = false;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            String dbName = DB_NAME;
            if (conn != null) {
                // System.out.println("Checking if database '" + DB_NAME + "' exists...");
                rs = conn.getMetaData().getCatalogs();
                while (rs.next()) {
                    String catalogs = rs.getString(1);
                    if (dbName.equals(catalogs)) {
                        System.out.println("The database '" + dbName + "' existed");
                        result = true;
                    }
                }
            } else {
                System.out.println("unable to create database connection");
            }
        } catch (SQLException ex) {
            // ex.printStackTrace();
            System.out.println("Make sure XAMPP is opened");
            System.exit(0);
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

    // create database function
    private boolean createDatabase() {
        boolean result = false;
        // System.out.println("Creating database...");
        try (Connection conn = getSQLConnection();
                Statement stmt = conn.createStatement();) {
            String sql = "CREATE DATABASE " + DB_NAME;
            stmt.executeUpdate(sql);
            // System.out.println("Database '" + DB_NAME + "' created successfully");
            result = true;
            conn.close();
        } catch (SQLException e) {
            System.out.println("Database '" + DB_NAME + "' created failed");
            e.printStackTrace();
        }
        return result;
    }

    // drop database function
    private boolean dropDatabase() {
        boolean result = false;
        // System.out.println("Droping database...");
        try (Connection conn = getSQLConnection();
                Statement stmt = conn.createStatement();) {
            String sql = "DROP DATABASE " + DB_NAME;
            stmt.executeUpdate(sql);
            // System.out.println("Database '" + DB_NAME + "' dropped successfully");
            result = true;
            conn.close();
        } catch (SQLException e) {
            System.out.println("Database '" + DB_NAME + "' dropped failed");
            e.printStackTrace();
        }
        return result;
    }
}