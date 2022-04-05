import DB.fetcher;

public class Farm {
    int farmid;
    String[] actions = {"sowing","harvest","pesticide","fertilizing"}; //status {0,1,2,3}
    String[] plants;
    String[] fertilizer;
    String[] pesticides;
    int[][] field;//field x row | each row store status of actions

    fetcher Fetcher = new fetcher();
    public Farm(int farmid){
        this.farmid = farmid;
        fetchFarmablesDB();
        field = new int[plants.length][5]; //5 rows per field
    }
    void fetchFarmablesDB(){
        //fetch plants
        plants = Fetcher.fetchFarmablesByFarm(farmid,"plant");
        //fetch fertilizers
        fertilizer = Fetcher.fetchFarmablesByFarm(farmid,"fertilizer");
        //fetch pesticides
        pesticides = Fetcher.fetchFarmablesByFarm(farmid,"pesticide");
    }
    void findJob(){
        //CONCURRENT: if status -1 meaning other users working on that row, so it will find other row or field or other farm to work on
    }
}
