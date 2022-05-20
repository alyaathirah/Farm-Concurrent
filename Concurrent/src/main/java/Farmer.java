import DB.Fetcher;
import java.util.Random;

public class Farmer implements Runnable {
    Random rand = new Random();
    int userid;
    String name;
    String[] farms;
    Farm[] FarmObjects;

    DB.Fetcher Fetcher = new Fetcher();
    // int activityNum = 100;
    int activityNum = 100;
    // types
    String[] types = { "plant", "fertilizer", "pesticides" };
    // then subtypes based on farm's farmables list

    public Farmer(int userid, Farm[] FarmObjects) {
        this.userid = userid;
        this.FarmObjects = FarmObjects;
        farms = Fetcher.fetchFarmsByUser(userid);
    }

    @Override
    public void run() {
        for (int i = 0; i < activityNum; i++) {// 100 activities
            createActivity();
        }
    }

    public void createActivity() {// need id,date,action,type,unit,quantity,field,row,farmId,userId
        // Pick random farm
        Farm tempFarm = FarmObjects[Integer.parseInt(farms[rand.nextInt(farms.length)]) - 1];
        tempFarm.getJob(userid);
    }

    public void disaster() {

    }
}