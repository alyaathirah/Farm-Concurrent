package DB;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import com.github.javafaker.Faker;

public class Seeder {
    private static Seeder seeder = new Seeder();
    private Random rand = new Random();
    private activity_counter counter = activity_counter.getInstance();
    private Connection Mconn;

    //seeder cant initialize twice
    public Seeder() {
        try {
            Mconn = Table.getDatabaseConnection();
            Mconn.setAutoCommit(false);
        } catch (SQLException e) {
            // need to have error handling here
            e.printStackTrace();
        }
    }
    public static Seeder getInstance(){
        return seeder;
    }

    public void seederManager(int nPlant, int nFertilizer, int nPesticide, int nFarm, int nUser) {
        seedFarms(nFarm);
        seedFertilizers(nFertilizer);
        seedPesticides(nPesticide);
        seedPlants(nPlant);
        seedUsers(nUser);
    }

    public void closeConn() {
        try {
            if (Mconn != null)
                Mconn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rollback() {
        try {
            if (Mconn != null)
                Mconn.rollback();
            // System.out.println("rollback occurred");
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        }
    }

    private boolean seedFarms(int n) {
        // seed data into table 'farms'
        boolean result = false;
        Faker faker = new Faker();
        String SQL = "INSERT INTO farms(id,name,address) " + "VALUES(?,?,?)";

        try (PreparedStatement pstmt = Mconn.prepareStatement(SQL, new String[] { "id" })) {

            String farmName = "";
            for (int i = 1; i < n; i++) {
                farmName = "farm" + i;
                pstmt.setString(1, Integer.toString(i));
                pstmt.setString(2, farmName);
                pstmt.setString(3, faker.address().streetAddress());
                pstmt.execute();
                Mconn.commit();

                generateFarmables(String.valueOf(i));
            }

        } catch (SQLException ex) {
            System.out.println("Seed table 'farms' failed");
            System.out.println(ex.getMessage());
            rollback();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // can use here to close resources
        }
        return result;
    }

    private boolean seedFertilizers(int n) {
        // seed data into table 'fertilizers'
        boolean result = false;
        String SQL = "INSERT INTO fertilizers(id,name,unitType) " + "VALUES(?,?,?)";

        try (PreparedStatement pstmt = Mconn.prepareStatement(SQL)) {

            String fertilizerName = "";
            for (int i = 1; i < n; i++) {
                fertilizerName = "fertilizer" + i;
                pstmt.setString(1, Integer.toString(i));
                pstmt.setString(2, fertilizerName);
                pstmt.setString(3, "pack");
                pstmt.execute();
                Mconn.commit();

            }

        } catch (SQLException ex) {
            System.out.println("Seed table 'fertilizers' failed");
            System.out.println(ex.getMessage());
            rollback();
        }
        return result;
    }

    private boolean seedPesticides(int n) {
        // seed data into table 'pesticides'
        boolean result = false;
        String SQL = "INSERT INTO pesticides(id,name,unitType) " + "VALUES(?,?,?)";

        try (PreparedStatement pstmt = Mconn.prepareStatement(SQL)) {

            String pesticideName = "";
            for (int i = 1; i < n; i++) {
                pesticideName = "pesticide" + i;
                pstmt.setString(1, Integer.toString(i));
                pstmt.setString(2, pesticideName);
                pstmt.setString(3, "mass");
                pstmt.execute();
                Mconn.commit();

            }

        } catch (SQLException ex) {
            System.out.println("Seed table 'pesticides' failed");
            System.out.println(ex.getMessage());
            rollback();
        }
        return result;
    }

    private boolean seedPlants(int n) {
        // seed data into table 'plants'
        boolean result = false;
        String SQL = "INSERT INTO plants(id,name,unitType) " + "VALUES(?,?,?)";
        try (PreparedStatement pstmt = Mconn.prepareStatement(SQL)) {

            String plantName = "";
            for (int i = 1; i < n; i++) {
                plantName = "plant" + i;
                pstmt.setString(1, Integer.toString(i));
                pstmt.setString(2, plantName);
                pstmt.setString(3, "mass");
                pstmt.execute();
                Mconn.commit();

            }

        } catch (SQLException ex) {
            System.out.println("Seed table 'plants' failed");
            System.out.println(ex.getMessage());
            rollback();
        }
        return result;
    }

    private boolean seedUsers(int n) {
        // seed data into table 'users'
        boolean result = false;
        Faker faker = new Faker();
        String SQL = "INSERT INTO users(id,name,email,password,phoneNumber) " + "VALUES(?,?,?,?,?)";

        try (PreparedStatement pstmt = Mconn.prepareStatement(SQL, new String[] { "id" })) {

            for (int i = 1; i < n; i++) {
                pstmt.setString(1, Integer.toString(i));
                pstmt.setString(2, faker.name().fullName());
                pstmt.setString(3, faker.internet().emailAddress());
                pstmt.setString(4, faker.internet().password());
                pstmt.setString(5, faker.phoneNumber().phoneNumber());
                pstmt.execute();
                Mconn.commit();

                for (int j = 1; j <= (rand.nextInt(5) + 1); j++) {
                    assignFarm(String.valueOf(i), "farmer");
                }
            }

        } catch (SQLException ex) {
            System.out.println("Seed table 'users' failed");
            System.out.println(ex.getMessage());
            rollback();
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
    public void seedActivity(String[] str){ //special seed activity method for lost activities array
        seedActivity(str[0], str[1], str[2], str[3], str[4], str[5], str[6], str[7]);
    }
    public void seedActivity(String action, String type, String unit, String quantity, String field, String row,
        String farmId, String userId) {
        // seed data into table 'activities'
        // Date date = new Date(System.currentTimeMillis());
        Faker faker = new Faker();
        Date date = faker.date().future(rand.nextInt(3) + 1, TimeUnit.DAYS);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String SQL = "INSERT INTO activities(id, date, action, type, unit, quantity, field, row, farm_id, user_id) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = Mconn.prepareStatement(SQL)) {

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
            Mconn.commit();

            //rollback on probability given: 2%
//            if (this.rand.nextInt(101) <= 8)
//                throw new SQLException("simulate rollback");

        } catch (SQLException ex) {
            // System.out.println("Seed table 'activities' failed");
            // System.out.println(ex.getMessage());
            rollback();
        }
    }

    private boolean seedFarmable(String farmid, String farmable_type) {
        // seed data into table 'farmables'
        boolean result = false;
        int farmableLimit = 100;
        int randFarmableID = rand.nextInt(farmableLimit) + 1;
        String SQL = "INSERT INTO farmables(farm_id,farmable_id,farmable_type) " + "VALUES(?,?,?)";

        try (PreparedStatement pstmt = Mconn.prepareStatement(SQL)) {

            pstmt.setString(1, farmid);
            pstmt.setString(2, String.valueOf(randFarmableID));
            pstmt.setString(3, farmable_type);
            pstmt.execute();
            Mconn.commit();

        } catch (SQLException ex) {
            System.out.println("Seed table 'farmables' failed");
            System.out.println(ex.getMessage());
            rollback();
        }
        return result;
    }

    private boolean assignFarm(String userid, String farmable_type) {
        // assign user/farmers to random farm
        boolean result = false;
        int farmableLimit = 10;
        int randFarmableID = rand.nextInt(farmableLimit) + 1;
        // System.out.println("Seeding data into table 'farmables'...");
        String SQL = "INSERT INTO farmables(farm_id,farmable_id,farmable_type) " + "VALUES(?,?,?)";

        try (PreparedStatement pstmt = Mconn.prepareStatement(SQL)) {

            pstmt.setString(1, String.valueOf(randFarmableID));
            pstmt.setString(2, userid);
            pstmt.setString(3, farmable_type);
            pstmt.execute();
            Mconn.commit();
        } catch (SQLException ex) {
            System.out.println("Seed table 'farmables' failed");
            System.out.println(ex.getMessage());
            rollback();
        }
        return result;
    }
}
