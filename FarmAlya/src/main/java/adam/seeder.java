package adam;

import java.sql.*;
import com.github.javafaker.Faker;

public class seeder {
    protected void seederManager() {
        seedPlants();
        seedFertilizers();
        seedPesticides();
        seedFarms();
        seedUsers();
    }

    // seed data into table 'plants'
    private boolean seedPlants() {
        boolean result = false;
        System.out.println("Seeding data into table 'plants'...");
        String SQL = "INSERT INTO plants(name,unitType) "
                + "VALUES(?,?)";
        try (Connection conn = table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            String plantName = "";
            for (int i = 1; i < 101; i++) {
                plantName = "plant" + i;
                pstmt.setString(1, plantName);
                pstmt.setString(2, "mass");
                pstmt.execute();
            }
            conn.close();
            System.out.println("Seed table 'plants' successfully");
        } catch (SQLException ex) {
            System.out.println("Seed table 'plants' failed");
            System.out.println(ex.getMessage());
        }
        return result;
    }

    // seed data into table 'fertilizers'
    private boolean seedFertilizers() {
        boolean result = false;
        System.out.println("Seeding data into table 'fertilizers'...");
        String SQL = "INSERT INTO fertilizers(name,unitType) "
                + "VALUES(?,?)";
        try (Connection conn = table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            String fertilizerName = "";
            for (int i = 1; i < 101; i++) {
                fertilizerName = "fertilizer" + i;
                pstmt.setString(1, fertilizerName);
                pstmt.setString(2, "pack");
                pstmt.execute();
            }
            conn.close();
            System.out.println("Seed table 'fertilizers' successfully");
        } catch (SQLException ex) {
            System.out.println("Seed table 'fertilizers' failed");
            System.out.println(ex.getMessage());
        }
        return result;
    }

    // seed data into table 'pesticides'
    private boolean seedPesticides() {
        boolean result = false;
        System.out.println("Seeding data into table 'pesticides'...");
        String SQL = "INSERT INTO pesticides(name,unitType) "
                + "VALUES(?,?)";
        try (Connection conn = table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            String pesticideName = "";
            for (int i = 1; i < 101; i++) {
                pesticideName = "pesticide" + i;
                pstmt.setString(1, pesticideName);
                pstmt.setString(2, "mass");
                pstmt.execute();
            }
            conn.close();
            System.out.println("Seed table 'pesticides' successfully");
        } catch (SQLException ex) {
            System.out.println("Seed table 'pesticides' failed");
            System.out.println(ex.getMessage());
        }
        return result;
    }

    // seed data into table 'farms'
    private boolean seedFarms() {
        boolean result = false;
        Faker faker = new Faker();
        System.out.println("Seeding data into table 'farms'...");
        String SQL = "INSERT INTO farms(name,address) "
                + "VALUES(?,?)";
        try (Connection conn = table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            String farmName = "";
            for (int i = 1; i < 11; i++) {
                farmName = "farms" + i;
                pstmt.setString(1, farmName);
                pstmt.setString(2, faker.address().streetAddress());
                pstmt.execute();
            }
            conn.close();
            System.out.println("Seed table 'farms' successfully");
        } catch (SQLException ex) {
            System.out.println("Seed table 'farms' failed");
            System.out.println(ex.getMessage());
        }
        return result;
    }

    // seed data into table 'users'
    private boolean seedUsers() {
        boolean result = false;
        Faker faker = new Faker();
        System.out.println("Seeding data into table 'users'...");
        String SQL = "INSERT INTO users(name,email,password,phoneNumber) "
                + "VALUES(?,?,?,?)";
        try (Connection conn = table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            for (int i = 1; i < 6; i++) {
                pstmt.setString(1, faker.name().fullName());
                pstmt.setString(2, faker.internet().emailAddress());
                pstmt.setString(3, faker.internet().password());
                pstmt.setString(4, faker.phoneNumber().phoneNumber());
                pstmt.execute();
            }
            conn.close();
            System.out.println("Seed table 'users' successfully");
        } catch (SQLException ex) {
            System.out.println("Seed table 'users' failed");
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
