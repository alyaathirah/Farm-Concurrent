import DB.Database;
import DB.Seeder;
import DB.Table;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.StopWatch;

public class ConcurrentDriver {
    public static void main(String[] args){
        System.out.println("Running Concurrent Program");
        StopWatch watch = new StopWatch();
        
        //create database
        Database createDatabase = new Database();
        Table createTables = new Table();
        Seeder createSeeder = new Seeder();
        createDatabase.databaseManager();
        createTables.tableManager();
        createSeeder.seederManager(101, 101, 101, 11, 101);

        //start timer (to measure the speed of activity)
        watch.start();

        //sreate activities for each farmers
        int nFarmer = 100;
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
        }

        for (Thread thread : farmersT) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread joined");
        
        //running n skipped activity (interrupted activity)
        ExecutorService executorService = Executors.newFixedThreadPool(16);
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

        //end timer
        watch.stop();

        //simple report
        System.out.println("Activity excuted: "+Farmer.totalAct + " Interrupted: "+Farmer.totalInterruptedAct);
        System.out.println(watch.getTime()+"ms");

        //input log target
        InputLog inputlog = new InputLog();
        inputlog.inputlog();
    }
}
