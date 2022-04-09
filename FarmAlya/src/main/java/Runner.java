import DB.database;
import DB.fetcher;
import DB.seeder;
import DB.table;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;

/**
 * TODO
 * - create relationship of farms and its plants,fertilizers,pesticides
 */
public class Runner {
    public static void main(String[] args){
        StopWatch watch = new StopWatch();
        watch.start();
        /**
         * Create database
         */
        database createDatabase = new database();
        table createTables = new table();
        seeder createSeeder = new seeder();
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
//        for (Thread thread : farmersT) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        //Create activity

        InputLog inputlog = new InputLog();
        inputlog.inputlog();
        watch.stop();
        System.out.println(watch.getTime()+"ms");
    }
}
