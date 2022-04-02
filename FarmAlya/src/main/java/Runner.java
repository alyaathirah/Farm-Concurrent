import DB.database;
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

        //Fetch users

        watch.stop();
        System.out.println(watch.getTime()+"ms");
    }
}
