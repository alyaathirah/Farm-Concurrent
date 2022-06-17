import DB.Fetcher;
import java.util.Random;

public class Farmer{
    Random rand = new Random();
    int userid;
    String name;
    String[] farms;
    Farm[] FarmObjects;

    DB.Fetcher Fetcher = new Fetcher();
    int activityNum = 1000;
    // types
    String[] types = { "plant", "fertilizer", "pesticides" };
    // then subtypes based on farm's farmables list

    public Farmer(int userid, Farm[] FarmObjects) {
        this.userid = userid;
        this.FarmObjects = FarmObjects;
        farms = Fetcher.fetchFarmsByUser(userid);
    }

    public void run() {
        for (int i = 0; i < activityNum; i++) {
            createActivity();
        }
    }

    public void createActivity() {
        // Pick random farm
        Farm tempFarm = FarmObjects[Integer.parseInt(farms[rand.nextInt(farms.length)]) - 1];
        tempFarm.getJob(userid);
    }
}
