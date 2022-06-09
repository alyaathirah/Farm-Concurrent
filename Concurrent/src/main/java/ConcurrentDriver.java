import DB.Database;
import DB.Seeder;
import DB.Table;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;


/**
 * TODO
 * - create relationship of farms and its plants,fertilizers,pesticides
 */
public class ConcurrentDriver {
    public static void main(String[] args){
        System.out.println("Running concurrent program");
        StopWatch watch = new StopWatch();
        /**
         * Create database
         */
        Database createDatabase = new Database();
        Table createTables = new Table();
        Seeder createSeeder = new Seeder();
        createDatabase.databaseManager();
        createTables.tableManager();
        createSeeder.seederManager(101, 101, 101, 11, 101);

        watch.start();  
        int nFarmer = 100;

        //Create activities for each users
        Farm[] farms = new Farm[10];
        for(int i=0; i<10; i++){
            farms[i] = new Farm(i+1);
        }

        Farmer[] farmers = new Farmer[nFarmer];
        Thread[] farmersT = new Thread[nFarmer];
        for(int i=0; i<nFarmer; i++){
            farmers[i] = new Farmer(i+1,farms);
            farmersT[i] = new Thread(farmers[i]);
        }

        //start farmers' activities
        for(int i=0; i<nFarmer; i++){
            farmersT[i].start();
//            farmers[i].run();
        }


        for (Thread thread : farmersT) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread joined");
        
        //START of RUN INTERRUPTED ACTIVITY (turns)
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (Farmer sfarmer : Farmer.skipFarmer) {
            executorService.execute(sfarmer);
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(24L, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        createSeeder.closeConn();
        //END of RUN INTERRRUPTED ACTIVITY (turns)
        watch.stop();
        System.out.println("Activity excuted: "+Farmer.totalAct + " Interrupted: "+Farmer.totalInterruptedAct);
        System.out.println(watch.getTime()+"ms");
        //Create activity

        //input log target
        InputLog inputlog = new InputLog();
        inputlog.inputlog();

    }
}
