public class App {
    public static void main(String[] args) throws Exception {
        database createDatabase = new database();
        table createTables = new table();

        createDatabase.databaseManager();
        createTables.tableManager();
    }
}
