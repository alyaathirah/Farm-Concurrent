import java.util.ArrayList;

public class Runner {
    public static void main(String[] args){
        ArrayList<Farm> farms = new ArrayList<>();
        ArrayList<Plant> plants = new ArrayList<>();
        ArrayList<Fertilizer> fertilizers = new ArrayList<>();
        ArrayList<Pesticide> pesticides = new ArrayList<>();

        for(int i=0; i<10; i++){
            farms.add(new Farm());
        }
        for(int i=0; i<100; i++){
            plants.add(new Plant());
        }
        for(int i=0; i<100; i++){
            fertilizers.add(new Fertilizer());
        }
        for(int i=0; i<100; i++){
            pesticides.add(new Pesticide());
        }

    }
}
