package DB;

import java.sql.*;
import java.util.Random;

import com.github.javafaker.Faker;

public class seeder {
    Random rand = new Random();
    public void seederManager() {
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
             PreparedStatement pstmt = conn.prepareStatement(SQL, new String[]{"id"})) {
            String farmName = "";
            for (int i = 1; i < 11; i++) {
                farmName = "farms" + i;
                pstmt.setString(1, farmName);
                pstmt.setString(2, faker.address().streetAddress());
                pstmt.execute();

                //return autoincrement id
                long key = -1L;
                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    key = rs.getLong(1);
                }
                //generate relationship with plants, fertilizer, and pesticides
                generateFarmables(key);
            }
            conn.close();
            System.out.println("Seed table 'farms' successfully");
        } catch (SQLException ex) {
            System.out.println("Seed table 'farms' failed");
            System.out.println(ex.getMessage());
        }
        return result;
    }
    private void generateFarmables(long farmid){
        //each types of farmables has random number of the types (1-5)
        //generate plants
        for(int i=1; i<=(rand.nextInt(5)+1); i++){
            seedFarmable(farmid, "plant");
        }
        //generate fertilizers
        for(int i=1; i<=(rand.nextInt(5)+1); i++){
            seedFarmable(farmid, "fertilizer");
        }
        //generate pesticides
        for(int i=1; i<=(rand.nextInt(5)+1); i++){
            seedFarmable(farmid, "pesticide");
        }
    }

    // seed data into table 'users'
    private boolean seedUsers() {
        boolean result = false;
        Faker faker = new Faker();
        System.out.println("Seeding data into table 'users'...");
        String SQL = "INSERT INTO users(name,email,password,phoneNumber) "
                + "VALUES(?,?,?,?)";
        try (Connection conn = table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL, new String[]{"id"})) {
            for (int i = 1; i < 101; i++) {
                pstmt.setString(1, faker.name().fullName());
                pstmt.setString(2, faker.internet().emailAddress());
                pstmt.setString(3, faker.internet().password());
                pstmt.setString(4, faker.phoneNumber().phoneNumber());
                pstmt.execute();
                //assign to farms
                //return autoincrement id
                long key = -1L;
                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    key = rs.getLong(1);
                }
                for(int j=1; j<=(rand.nextInt(5)+1); j++){
                    assignFarm(key, "farmer");
                }
            }

            conn.close();
            System.out.println("Seed table 'users' successfully");
        } catch (SQLException ex) {
            System.out.println("Seed table 'users' failed");
            System.out.println(ex.getMessage());
        }
        return result;
    }

    // seed data into table 'activities' NOTDONE
    private boolean seedActivity(String date, String farmid, String userid) {
        boolean result = false;

        System.out.println("Seeding data into table 'activities'...");
        String SQL = "INSERT INTO activities(name,address) "
                + "VALUES(?,?)";
        try (Connection conn = table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            for (int i = 1; i < 11; i++) {
//                pstmt.setString(1, farmName);
                pstmt.execute();
            }
            conn.close();
            System.out.println("Seed table 'activities' successfully");
        } catch (SQLException ex) {
            System.out.println("Seed table 'activities' failed");
            System.out.println(ex.getMessage());
        }
        return result;
    }

    // seed data into table 'farmables'
    private boolean seedFarmable(long farmid, String farmable_type) {
        boolean result = false;
        int farmableLimit = 100;
        int randFarmableID = rand.nextInt(farmableLimit)+1;
        System.out.println("Seeding data into table 'farmables'...");
        String SQL = "INSERT INTO farmables(farm_id,farmable_id,farmable_type) "
                + "VALUES(?,?,?)";
        try (Connection conn = table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, String.valueOf(farmid));
            pstmt.setString(2, String.valueOf(randFarmableID));
            pstmt.setString(3, farmable_type);
            pstmt.execute();

            conn.close();
            System.out.println("Seed table 'farmables' successfully");
        } catch (SQLException ex) {
            System.out.println("Seed table 'farmables' failed");
            System.out.println(ex.getMessage());
        }
        return result;
    }
    // assign user/farmers to random farm
    private boolean assignFarm(long userid, String farmable_type) {
        boolean result = false;
        int farmableLimit = 10;
        int randFarmableID = rand.nextInt(farmableLimit)+1;
        System.out.println("Seeding data into table 'farmables'...");
        String SQL = "INSERT INTO farmables(farm_id,farmable_id,farmable_type) "
                + "VALUES(?,?,?)";
        try (Connection conn = table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, String.valueOf(randFarmableID));
            pstmt.setString(2, String.valueOf(userid));
            pstmt.setString(3, farmable_type);
            pstmt.execute();

            conn.close();
            System.out.println("Seed table 'farmables' successfully");
        } catch (SQLException ex) {
            System.out.println("Seed table 'farmables' failed");
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
