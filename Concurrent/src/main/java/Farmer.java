import DB.Fetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class Farmer implements Runnable {
    static List<Farmer> skipFarmer = new ArrayList<Farmer>();
    static int totalAct = 0, totalInterruptedAct = 0;
    int nSkipAct = 0, userid, activityNum = 100;
    boolean listed = false;
    Random rand = new Random();
    String name;
    String[] farms;
    Farm[] FarmObjects;

    DB.Fetcher Fetcher = new Fetcher();
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
                    totalInterruptedAct++;
                    nSkipAct++;
                    continue;
                }
                createActivity();
            }
        } else {
            for (int i = 0; i < nSkipAct; i++) {
                createActivity();
            }
        }
    }

    public void createActivity() {// need id,date,action,type,unit,quantity,field,row,farmId,userId

        // Pick random farm
        Farm tempFarm = FarmObjects[Integer.parseInt(farms[rand.nextInt(farms.length)]) - 1];
        if (tempFarm.getJob(userid)) {
            totalAct++;
        } else {
            totalInterruptedAct++;
            nSkipAct++;
        }
    }

    private void interruptProbability(int percentage) {
        if (this.rand.nextInt(1, 101) <= percentage)
            Thread.currentThread().interrupt();
    }

    private void addToInterruptList() {
        // add this farmer to Interrupted List
        if (!listed) {
            Farmer.skipFarmer.add(this);
            this.listed = true;
        }
    }
}
