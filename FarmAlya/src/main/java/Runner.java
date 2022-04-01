import DB.database;
import DB.seeder;
import DB.table;

import java.util.ArrayList;

public class Runner {
    public static void main(String[] args){
        ArrayList<Farm> farms = new ArrayList<>();
        ArrayList<Plant> plants = new ArrayList<>();
        ArrayList<Fertilizer> fertilizers = new ArrayList<>();
        ArrayList<Pesticide> pesticides = new ArrayList<>();
        

        //create database (adam)
        database createDatabase = new database();
        table createTables = new table();
        seeder createSeeder = new seeder();
        System.out.println("HELLO");
        createDatabase.databaseManager();
        createTables.tableManager();
        createSeeder.seederManager();

    }
}
