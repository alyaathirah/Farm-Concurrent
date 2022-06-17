import DB.Fetcher;
import DB.Seeder;
import DB.Table;

import java.sql.*;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Farm {
    Lock lock = new ReentrantLock();
    Lock lock2 = new ReentrantLock();
    Random rand = new Random();
    int farmid;
    String[] actions = { "sowing", "pesticide", "fertilizing", "harvest", "sales" }; // status {0,1,2,3,4}
    int[] plants;
    int[] fertilizer;
    int[] pesticides;
    int[][] field;// field x row | each row store status of actions

    DB.Fetcher Fetcher = new Fetcher();
    Seeder seeder = new Seeder();
    Connection conn;

    public Farm(int farmid) {
        try {
            conn = Table.getDatabaseConnection();
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.farmid = farmid;
        fetchFarmablesDB();
        field = new int[plants.length][5]; // 5 rows per field
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = 0;
            }
        }
    }

    void fetchFarmablesDB() {
        // fetch plants
        plants = Fetcher.fetchFarmablesByFarm(farmid, "plant");
        // fetch fertilizers
        fertilizer = Fetcher.fetchFarmablesByFarm(farmid, "fertilizer");
        // fetch pesticides
        pesticides = Fetcher.fetchFarmablesByFarm(farmid, "pesticide");
    }

    int[] getFieldRow() {
        // if status -1 meaning other users working on that row, so it will find other
        // row or field or other farm to work on
        int tempField;
        int tempRow;
        int tempStatus = -1;

        lock.lock();
        try {
            do {
                tempField = rand.nextInt(plants.length);
                tempRow = rand.nextInt(5);
                tempStatus = field[tempField][tempRow];

                if (tempStatus != -1) {
                    field[tempField][tempRow] = -1; // change the row to -1 temporarily
                    return new int[] { tempField, tempRow, tempStatus };
                }
            } while (tempStatus == -1);
        } finally {
            lock.unlock();
        }
        return new int[] { -1, -1, -1 };
    }

    void returnRowStatus(int tempField, int tempRow, int newStatus) {
        lock2.lock();
        try {
            field[tempField][tempRow] = newStatus;
        } finally {
            lock2.unlock();
        }
    }

    public String[] getJob(int userid, boolean actSkipped) {
        // get random field index
        int[] fieldRowStatus = getFieldRow();
        int tempField = fieldRowStatus[0];
        int tempRow = fieldRowStatus[1];
        int tempStatus = fieldRowStatus[2];

        // change status
        if (tempStatus >= 4)
            tempStatus = 0;// set to zero after sales
        else {
            tempStatus++;
        }
        returnRowStatus(tempField, tempRow, tempStatus);

        String tempAction = actions[tempStatus];
        // generate random quantity and unit
        int tempQuantity = rand.nextInt(100);
        String tempUnit = "";

        // get type from field number | if new status pesticide/fertilizer
        // type=pesticide/fertilizer's brand
        String tempType = "";
        if (tempStatus == 0 || tempStatus == 3 || tempStatus == 4) {// plant name
            tempType = Fetcher.fetchPlantByID(plants[tempField]);
            tempUnit = "kg";
        } else if (tempStatus == 1) { // pesticide name
            tempType = Fetcher.fetchPesticideByID(pesticides[rand.nextInt(pesticides.length)]);
            tempUnit = "l";
        } else if (tempStatus == 2) {// fertilizer name
            tempType = Fetcher.fetchFertilizerByID(fertilizer[rand.nextInt(fertilizer.length)]);
            tempUnit = "pack";
        } else
            tempType = "none";

        if (actSkipped)
            return new String[] { tempAction, tempType, tempUnit, String.valueOf(tempQuantity),
                    String.valueOf(tempField),
                    String.valueOf(tempRow), String.valueOf(farmid), String.valueOf(userid) };
        else {
            // push to database right away
            seeder.seedActivity(conn, tempAction, tempType, tempUnit, String.valueOf(tempQuantity), String.valueOf(tempField),
                    String.valueOf(tempRow), String.valueOf(farmid), String.valueOf(userid));
            return null;
        }
    }
}
