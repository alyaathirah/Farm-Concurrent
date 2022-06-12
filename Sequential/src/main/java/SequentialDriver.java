import DB.Database;
import DB.Seeder;
import DB.Table;
import org.apache.commons.lang3.time.StopWatch;

public class SequentialDriver {
    public static void main(String[] args) {
        System.out.println("Running Sequential Program");
        StopWatch watch = new StopWatch();
        
        //Create database
        Database createDatabase = new Database();
        Table createTables = new Table();
        Seeder createSeeder = new Seeder();
        createDatabase.databaseManager();
        createTables.tableManager();
        createSeeder.seederManager();

        //Start Timer (to measure the speed of activity)
        watch.start();

        // Create activities for each farmers
        int nFarmer = 100;
        Farm[] farms = new Farm[10];
        for (int i = 0; i < 10; i++) {
            farms[i] = new Farm(i + 1);
        }
        Farmer[] farmers = new Farmer[nFarmer];
        for (int i = 0; i < nFarmer; i++) {
            farmers[i] = new Farmer(i + 1, farms);
        }

        // start farmers' activities
        for (int i = 0; i < nFarmer; i++) {
            farmers[i].run();
        }

        //end timer
        watch.stop();

        //simple report
        System.out.println(watch.getTime() + "ms");

        // input log target
        InputLog inputlog = new InputLog();
        inputlog.inputlog();
    }
}
