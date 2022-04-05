import java.util.ArrayList;

public class Farmer implements Runnable{
    String id;
    String name;
    String[] farms;

    int activityNum = 100;
    ArrayList<Activity> activities = new ArrayList<>();

    //types
    String[] types = {"plant", "fertilizer", "pesticides"};
    //then subtypes based on farm's farmables list

    double disasterChance = 0.001;//0.1% chance of disaster

    @Override
    public void run() {
        for(int i=0; i<activityNum; i++){//100 activities


        }
    }
    public void createActivity(){//need id,date,action,type,unit,quantity,field,row,farmId,userId
        //Auto:id / Random:action,type,farmId,userId
        //Random farm

        // Random type

        //Random action based on type


    }
    public void disaster(){

    }
}
