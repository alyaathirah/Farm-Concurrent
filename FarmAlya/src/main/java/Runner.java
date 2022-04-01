import DB.database;
import DB.seeder;
import DB.table;

import java.util.ArrayList;

/**
 * TODO
 * - create relationship of farms and its plants,fertilizers,pesticides
 */
public class Runner {
    public static void main(String[] args){
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


    }
}
