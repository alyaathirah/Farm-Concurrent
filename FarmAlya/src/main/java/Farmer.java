import java.util.ArrayList;

public class Farmer implements Runnable{
    int id;
    String name;
    String[] farms;

    int activityNum = 100;
    ArrayList<Activity> activities = new ArrayList<>();

    //types
    String[] types = {"plant", "fertilizer", "pesticides"};
    //then subtypes based on farm's farmables list


    public Farmer(int id) {
        this.id = id;
    }

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
