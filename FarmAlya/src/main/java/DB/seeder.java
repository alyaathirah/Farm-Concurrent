package DB;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

import com.github.javafaker.Faker;

public class seeder {
    Random rand = new Random();
    activity_counter counter = activity_counter.getInstance();

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
        String SQL = "INSERT INTO plants(id,name,unitType) "
                + "VALUES(?,?,?)";
        try (Connection conn = table.getDatabaseConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            String plantName = "";
            for (int i = 1; i < 101; i++) {
                plantName = "plant" + i;
                pstmt.setString(1, Integer.toString(i));
                pstmt.setString(2, plantName);
                pstmt.setString(3, "mass");
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
        String SQL = "INSERT INTO fertilizers(id,name,unitType) "
                + "VALUES(?,?,?)";
        try (Connection conn = table.getDatabaseConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            String fertilizerName = "";
            for (int i = 1; i < 101; i++) {
                fertilizerName = "fertilizer" + i;
                pstmt.setString(1, Integer.toString(i));
                pstmt.setString(2, fertilizerName);
                pstmt.setString(3, "pack");
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
        String SQL = "INSERT INTO pesticides(id,name,unitType) "
                + "VALUES(?,?,?)";
        try (Connection conn = table.getDatabaseConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            String pesticideName = "";
            for (int i = 1; i < 101; i++) {
                pesticideName = "pesticide" + i;
                pstmt.setString(1, Integer.toString(i));
                pstmt.setString(2, pesticideName);
                pstmt.setString(3, "mass");
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
        String SQL = "INSERT INTO farms(id,name,address) "
                + "VALUES(?,?,?)";
        try (Connection conn = table.getDatabaseConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQL, new String[] { "id" })) {
            String farmName = "";
            for (int i = 1; i < 11; i++) {
                farmName = "farm" + i;
                pstmt.setString(1, Integer.toString(i));
                pstmt.setString(2, farmName);
                pstmt.setString(3, faker.address().streetAddress());
                pstmt.execute();

                // return autoincrement id
//                long key = -1L;
//                ResultSet rs = pstmt.getGeneratedKeys();
//
//                if (rs.next()) {
//                    key = rs.getLong(1);
//                }
                // generate relationship with plants, fertilizer, and pesticides
                generateFarmables(String.valueOf(i));
            }
            conn.close();
            System.out.println("Seed table 'farms' successfully");
        } catch (SQLException ex) {
            System.out.println("Seed table 'farms' failed");
            System.out.println(ex.getMessage());
        }
        return result;
    }

    private void generateFarmables(String farmid) {
        // each types of farmables has random number of the types (1-5)
        // generate plants
        for (int i = 1; i <= (rand.nextInt(5) + 1); i++) {
            seedFarmable(farmid, "plant");
        }
        // generate fertilizers
        for (int i = 1; i <= (rand.nextInt(5) + 1); i++) {
            seedFarmable(farmid, "fertilizer");
        }
        // generate pesticides
        for (int i = 1; i <= (rand.nextInt(5) + 1); i++) {
            seedFarmable(farmid, "pesticide");
        }
    }

    // seed data into table 'users'
    private boolean seedUsers() {
        boolean result = false;
        Faker faker = new Faker();
        System.out.println("Seeding data into table 'users'...");
        String SQL = "INSERT INTO users(id,name,email,password,phoneNumber) "
                + "VALUES(?,?,?,?,?)";
        try (Connection conn = table.getDatabaseConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQL, new String[] { "id" })) {
            for (int i = 1; i < 101; i++) {
                pstmt.setString(1, Integer.toString(i));
                pstmt.setString(2, faker.name().fullName());
                pstmt.setString(3, faker.internet().emailAddress());
                pstmt.setString(4, faker.internet().password());
                pstmt.setString(5, faker.phoneNumber().phoneNumber());
                pstmt.execute();
                // assign to farms
                // return autoincrement id
//                String key = "";
//                ResultSet rs = pstmt.getGeneratedKeys();
//                if (rs.next()) {
//                    key = rs.getString(1);
//                }
//                System.out.println(key);
                for (int j = 1; j <= (rand.nextInt(5) + 1); j++) {
                    assignFarm(String.valueOf(i), "farmer");
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

    // seed data into table 'activities'
    public boolean seedActivity(String action, String type, String unit, String quantity, String field, String row,
            String farmId, String userId) {
        boolean result = false;
        // Date
        Date date = new Date(System.currentTimeMillis());
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("Seeding data into table 'activities'...");
        String SQL = "INSERT INTO activities(id, date, action, type, unit, quantity, field, row, farm_id, user_id) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = table.getDatabaseConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, String.valueOf(counter.getCount()));
            pstmt.setString(2, formatter.format(date));
            pstmt.setString(3, action);
            pstmt.setString(4, type);
            pstmt.setString(5, unit);
            pstmt.setString(6, quantity);
            pstmt.setString(7, field);
            pstmt.setString(8, row);
            pstmt.setString(9, farmId);
            pstmt.setString(10, userId);

            pstmt.execute();

            conn.close();
            System.out.println("Seed table 'activities' successfully");
        } catch (SQLException ex) {
            System.out.println("Seed table 'activities' failed");
            System.out.println(ex.getMessage());
        }
        return result;
    }

    // seed data into table 'farmables'
    private boolean seedFarmable(String farmid, String farmable_type) {
        boolean result = false;
        int farmableLimit = 100;
        int randFarmableID = rand.nextInt(farmableLimit) + 1;
        System.out.println("Seeding data into table 'farmables'...");
        String SQL = "INSERT INTO farmables(farm_id,farmable_id,farmable_type) "
                + "VALUES(?,?,?)";
        try (Connection conn = table.getDatabaseConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, farmid);
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
    private boolean assignFarm(String userid, String farmable_type) {
        boolean result = false;
        int farmableLimit = 10;
        int randFarmableID = rand.nextInt(farmableLimit) + 1;
        System.out.println("Seeding data into table 'farmables'...");
        String SQL = "INSERT INTO farmables(farm_id,farmable_id,farmable_type) "
                + "VALUES(?,?,?)";
        try (Connection conn = table.getDatabaseConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, String.valueOf(randFarmableID));
            pstmt.setString(2, userid);
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
