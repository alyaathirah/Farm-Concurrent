import DB.Fetcher;
import DB.Seeder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Farmer implements Runnable {
    static List<Farmer> skipFarmer = new ArrayList<Farmer>();
    static AtomicInteger totalAct = new AtomicInteger();
    static AtomicInteger totalInterruptedAct = new AtomicInteger();

    ArrayList<String[] > skippedActivity = new ArrayList<String[]>();
    int nSkipAct = 0, userid, activityNum = 100;

    boolean listed = false;
    Random rand = new Random();
    String name;
    String[] farms;
    Farm[] FarmObjects;
    Fetcher Fetcher = new Fetcher();
    static Seeder Seeder = new Seeder(); //seeder
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
        if (!listed) {
            for (int i = 0; i < activityNum; i++) {

                // give random chances of interrupt
                interruptProbability(2);

                // if interrupted skip the activity and change the flag back
                if (Thread.interrupted()) {
                    addToInterruptList();
                    totalInterruptedAct.incrementAndGet();
                    nSkipAct++;
                    createActivity(true);
                    continue;
                }
                createActivity(false);
            }
        } else {
            retryActivity();
//            createActivity(false);
        }
    }

    public void createActivity(boolean actSkipped) {// need id,date,action,type,unit,quantity,field,row,farmId,userId
        // Pick random farm
        int farmID = Integer.parseInt(farms[rand.nextInt(farms.length)]) - 1;
//        FarmObjects[farmID].getJob(userid);
        if(actSkipped) {
            skippedActivity.add(FarmObjects[farmID].getJob(userid, actSkipped));
        }
        else
            FarmObjects[farmID].getJob(userid,actSkipped);
        totalAct.incrementAndGet();
    }
    private void retryActivity(){
        for (int i = 0; i < nSkipAct; i++) {
            Seeder.seedActivity(skippedActivity.get(i));
        }
    }

    private void interruptProbability(int percentage) {
        if (this.rand.nextInt(101) <= percentage) {
            Thread.currentThread().interrupt();
        }
    }

    private void addToInterruptList() {
        // add this farmer to Interrupted List
        if (!listed) {
            Farmer.skipFarmer.add(this);
            this.listed = true;
        }
    }

}
