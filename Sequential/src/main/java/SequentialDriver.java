import DB.Database;
import DB.Seeder;
import DB.Table;
import org.apache.commons.lang3.time.StopWatch;


/**
 * TODO
 * - create relationship of farms and its plants,fertilizers,pesticides
 */
public class SequentialDriver {
    public static void main(String[] args){
        System.out.println("Running sequential program");
        StopWatch watch = new StopWatch();
        watch.start();
        /**
         * Create database
         */
        Database createDatabase = new Database();
        Table createTables = new Table();
        Seeder createSeeder = new Seeder();
        createDatabase.databaseManager();
        createTables.tableManager();
        createSeeder.seederManager();


        //Create activities for each users
        Farm[] farms = new Farm[10];
        for(int i=0; i<10; i++){
            farms[i] = new Farm(i+1);
        }
        Farmer[] farmers = new Farmer[100];
//        Thread[] farmersT = new Thread[100];
        for(int i=0; i<100; i++){
            farmers[i] = new Farmer(i+1,farms);
//            farmersT[i] = new Thread(farmers[i]);
        }

        //start farmers' activities
        for(int i=0; i<100; i++){
            farmers[i].run();
        }
        watch.stop();
        System.out.println(watch.getTime()+"ms");

        //input log target
        InputLog inputlog = new InputLog();
        inputlog.inputlog();

    }
}
