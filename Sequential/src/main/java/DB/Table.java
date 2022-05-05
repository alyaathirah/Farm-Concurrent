package DB;

import java.sql.*;

public class Table {
    static final String DB_URL = Database.SQL_URL + Database.DB_NAME;
    static final String USERNAME = Database.USERNAME;
    static final String PASSWORD = Database.PASSWORD;

    public void tableManager() {
        createPlantsTable();
        createFertilizersTable();
        createPesticidesTable();
        createFarmsTable();
        createUsersTable();
        createFarmablesTable();
        createActivitiesTable();
    }

    //
    public static Connection getDatabaseConnection() throws SQLException {
        return Database.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    // create table "plants"
    private boolean createPlantsTable() {
        boolean result = false;
        // System.out.println("Creating table 'plants'...");
        try (Connection conn = getDatabaseConnection();
                Statement stmt = conn.createStatement();) {
            String sql = "CREATE TABLE plants " +
                    "(id VARCHAR(255) not NULL, " +
                    " name VARCHAR(255) not NULL, " +
                    " unitType VARCHAR(255) not NULL, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            // System.out.println("Created table 'plants' successfully");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Created table 'plants' failed");
            e.printStackTrace();
        }
        return result;
    }

    // create table "fertilizers"
    private boolean createFertilizersTable() {
        boolean result = false;
        // System.out.println("Creating table 'fertilizers'...");
        try (Connection conn = getDatabaseConnection();
                Statement stmt = conn.createStatement();) {
            String sql = "CREATE TABLE fertilizers " +
                    "(id VARCHAR(255) not NULL, " +
                    " name VARCHAR(255) not NULL, " +
                    " unitType VARCHAR(255) not NULL, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            // System.out.println("Created table 'fertilizers' successfully");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Created table 'fertilizers' failed");
            e.printStackTrace();
        }
        return result;
    }

    // create table "pesticides"
    private boolean createPesticidesTable() {
        boolean result = false;
        // System.out.println("Creating table 'pesticides'...");
        try (Connection conn = getDatabaseConnection();
                Statement stmt = conn.createStatement();) {
            String sql = "CREATE TABLE pesticides " +
                    "(id VARCHAR(255) not NULL, " +
                    " name VARCHAR(255) not NULL, " +
                    " unitType VARCHAR(255) not NULL, " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            // System.out.println("Created table 'pesticides' successfully");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Created table 'pesticides' failed");
            e.printStackTrace();
        }
        return result;
    }

    // create table "farms"
    private boolean createFarmsTable() {
        boolean result = false;
        // System.out.println("Creating table 'farms'...");
        try (Connection conn = getDatabaseConnection();
                Statement stmt = conn.createStatement();) {
            String sql = "CREATE TABLE farms " +
                    "(id VARCHAR(255) not NULL, " +
                    " name VARCHAR(255) not NULL, " +
                    " address VARCHAR(255) not NULL, " +
                    " PRIMARY KEY ( id ), " +
                    " UNIQUE (address))";
            stmt.executeUpdate(sql);
            // System.out.println("Created table 'farms' successfully");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Created table 'farms' failed");
            e.printStackTrace();
        }
        return result;
    }

    // create table "users"
    private boolean createUsersTable() {
        boolean result = false;
        // System.out.println("Creating table 'users'...");
        try (Connection conn = getDatabaseConnection();
                Statement stmt = conn.createStatement();) {
            String sql = "CREATE TABLE users " +
                    "(id VARCHAR(255) not NULL, " +
                    " name VARCHAR(255) not NULL, " +
                    " email VARCHAR(255) not NULL, " +
                    " password VARCHAR(255) not NULL, " +
                    " phoneNumber VARCHAR(255) not NULL, " +
                    " PRIMARY KEY ( id ), " +
                    " UNIQUE (email)," +
                    " UNIQUE (phoneNumber))";
            stmt.executeUpdate(sql);
            // System.out.println("Created table 'users' successfully");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Created table 'users' failed");
            e.printStackTrace();
        }
        return result;
    }

    // create polymorphic table "farmables"
    private boolean createFarmablesTable() {
        boolean result = false;
        // System.out.println("Creating table 'farmables'...");
        try (Connection conn = getDatabaseConnection();
                Statement stmt = conn.createStatement();) {
            String sql = "CREATE TABLE farmables " +
                    "(farm_id VARCHAR(255) not NULL, " +
                    " farmable_id VARCHAR(255) not NULL, " +
                    " farmable_type VARCHAR(255) not NULL) ";
            stmt.executeUpdate(sql);
            // System.out.println("Created table 'farmables' successfully");
        } catch (SQLException e) {
            System.out.println("Created table 'farmables' failed");
            e.printStackTrace();
        }
        return result;
    }

    // create table "activities"
    private boolean createActivitiesTable() {
        boolean result = false;
        // System.out.println("Creating table 'activities'...");
        try (Connection conn = getDatabaseConnection();
                Statement stmt = conn.createStatement();) {
            String sql = "CREATE TABLE activities " +
                    "(id VARCHAR(255) not NULL, " +
                    " farm_id VARCHAR(255) not NULL, " +
                    " user_id VARCHAR(255) not NULL, " +
                    " date VARCHAR(255) not NULL, " +
                    " action VARCHAR(255) not NULL, " +
                    " type VARCHAR(255) not NULL, " +
                    " unit VARCHAR(255) not NULL, " +
                    " quantity DOUBLE not NULL, " +
                    " field INTEGER not NULL, " +
                    " row INTEGER not NULL, " +
                    " PRIMARY KEY ( id ), " +
                    " FOREIGN KEY (farm_id) REFERENCES farms(id)," +
                    " FOREIGN KEY (user_id) REFERENCES users(id))";
            stmt.executeUpdate(sql);
            // System.out.println("Created table 'activities' successfully");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Created table 'activities' failed");
            e.printStackTrace();
        }
        return result;
    }
}
