import DB.Fetcher;
import DB.Seeder;
import java.util.Random;

public class Farm {
    Random rand = new Random();
    int farmid;
    String[] actions = { "sowing", "pesticide", "fertilizing", "harvest", "sales" }; // status {0,1,2,3,4}
    int[] plants;
    int[] fertilizer;
    int[] pesticides;
    int[][] field;// field x row | each row store status of actions

    DB.Fetcher Fetcher = new Fetcher();
    DB.Seeder Seeder = new Seeder();

    public Farm(int farmid) {
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
        // for(int i=0; i<plants.length; i++)
        // System.out.println(plants[i]);
    }
//    synchronized int[] getFieldRow(){
//        // get random field index
//        int tempField = rand.nextInt(plants.length);
//        // get random row index
//        int tempRow = rand.nextInt(5);
//        // check status
//        int tempStatus = field[tempField][tempRow];
//        while (tempStatus == -1){
//            if (tempStatus == -1)
//                return new int[]{-1, -1};
//            else {
//                return new int[]{tempField, tempField};
//            }
//        }
//        return new int[]{-1, -1};
//
//    }
    synchronized void getJob(int userid) {// later set to synchronized in concurrent
        // CONCURRENT: if status -1 meaning other users working on that row, so it will
        // find other row or field or other farm to work on
        // String id, String date, String action, String type, String unit, double
        // quantity, int field, int row, String farmId, String userId

        // get random field index
        int tempField = rand.nextInt(plants.length);
        // get random row index
        int tempRow = rand.nextInt(5);
        // check status
        int tempStatus = field[tempField][tempRow];
        // change status
        if (tempStatus >= 4)
            tempStatus = 0; // set to zero after sales
        else {
            tempStatus++;
            field[tempField][tempRow] = tempStatus;
        }
        String tempAction = actions[tempStatus];
        // generate random quantity and unit
        int tempQuantity = rand.nextInt(100);
        String tempUnit = "kg";

        // get type from field number | if new status pesticide/fertilizer
        // type=pesticide/fertilizer's brand
        String tempType = "";
        if (tempStatus == 0 || tempStatus == 3 || tempStatus == 4) {// plant related
            tempType = Fetcher.fetchPlantByID(plants[tempField]);
        } else if (tempStatus == 1)
            tempType = Fetcher.fetchPesticideByID(pesticides[rand.nextInt(pesticides.length)]);
        else if (tempStatus == 2)
            tempType = Fetcher.fetchFertilizerByID(fertilizer[rand.nextInt(fertilizer.length)]);
        else
            tempType = "none";
        // push to database
        Seeder.seedActivity(tempAction, tempType, tempUnit, String.valueOf(tempQuantity), String.valueOf(tempField),
                String.valueOf(tempRow), String.valueOf(farmid), String.valueOf(userid));

    }
}
