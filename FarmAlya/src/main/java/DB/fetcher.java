package DB;
import java.sql.*;
import java.util.ArrayList;

public class fetcher {
    //fetches all users in db
    public String[] fetchUsers(){
        return null;
    }

    public String[] fetchFarmsByUser(int userID){
        ArrayList<String> tempArray = new ArrayList<String>();
        // boolean result = false;
        String SQL = "SELECT farm_id FROM farmables WHERE farmable_type='farmer' AND farmable_id="+userID;
        try (Connection conn = table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            boolean results = pstmt.execute(SQL);
            //Loop through the available result sets.
            do {
                if(results) {
                    ResultSet rs = pstmt.getResultSet();
                    //Show data from the result set.
                    while (rs.next()) {
                        tempArray.add(rs.getString("farm_id"));

                    }
                    rs.close();
                }
                results = pstmt.getMoreResults();
            } while(results);
            pstmt.close();
            conn.close();

        }
        catch (SQLException ex) {
            System.out.println("Fetch failed");
            System.out.println(ex.getMessage());
        }

        String[] arr = tempArray.toArray(new String[0]);
        return arr;
    }
    public int[] fetchFarmablesByFarm(int farmID, String farmableType){ //types:farmer, plant, fertilizer, pesticide
        ArrayList<Integer> tempArray = new ArrayList<>();
        // boolean result = false;
        String SQL = "SELECT farmable_id,farmable_type FROM farmables WHERE farm_id="+farmID+" AND farmable_type='"+farmableType+"'";
        try (Connection conn = table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            boolean results = pstmt.execute(SQL);
            //Loop through the available result sets.
            do {
                if(results) {
                    ResultSet rs = pstmt.getResultSet();
                    //Show data from the result set.
                    while (rs.next()) {
                        tempArray.add(rs.getInt("farmable_id"));
//                        System.out.println(rs.getString("farmable_id")+" "+rs.getString("farmable_type"));
                    }
                    rs.close();
                }
                results = pstmt.getMoreResults();
            } while(results);
            pstmt.close();
            conn.close();
        }
        catch (SQLException ex) {
            System.out.println("Fetch failed");
            System.out.println(ex.getMessage());
        }

        //convert to int[]
        int[] arr = new int[tempArray.size()];
        for(int i=0; i<tempArray.size(); i++){
            arr[i] = tempArray.get(i);
        }

        return arr;
    }
    public String fetchPlantByID(int plantID){
        String SQL = "SELECT name FROM plants WHERE id="+plantID;
        String str = "";
        try (Connection conn = table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            boolean results = pstmt.execute(SQL);
            //Loop through the available result sets.
            do {
                if(results) {
                    ResultSet rs = pstmt.getResultSet();
                    //Show data from the result set.
                    while (rs.next()) {
                        str = (rs.getString("name"));
                    }
                    rs.close();
                }
                results = pstmt.getMoreResults();
            } while(results);
            pstmt.close();
            conn.close();
        }
        catch (SQLException ex) {
            System.out.println("Fetch failed");
            System.out.println(ex.getMessage());
        }
        return str;
    }

    public String fetchFertilizerByID(int fertilizerID){
        String SQL = "SELECT name FROM fertilizers WHERE id="+fertilizerID;
        String str = "";
        try (Connection conn = table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            boolean results = pstmt.execute(SQL);
            //Loop through the available result sets.
            do {
                if(results) {
                    ResultSet rs = pstmt.getResultSet();
                    //Show data from the result set.
                    while (rs.next()) {
                        str = (rs.getString("name"));
                    }
                    rs.close();
                }
                results = pstmt.getMoreResults();
            } while(results);
            pstmt.close();
            conn.close();
        }
        catch (SQLException ex) {
            System.out.println("Fetch failed");
            System.out.println(ex.getMessage());
        }
        return str;
    }

    public String fetchPesticideByID(int pesticideID){
        String SQL = "SELECT name FROM pesticides WHERE id="+pesticideID;
        String str = "";
        try (Connection conn = table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            boolean results = pstmt.execute(SQL);
            //Loop through the available result sets.
            do {
                if(results) {
                    ResultSet rs = pstmt.getResultSet();
                    //Show data from the result set.
                    while (rs.next()) {
                        str = (rs.getString("name"));
                    }
                    rs.close();
                }
                results = pstmt.getMoreResults();
            } while(results);
            pstmt.close();
            conn.close();
        }
        catch (SQLException ex) {
            System.out.println("Fetch failed");
            System.out.println(ex.getMessage());
        }
        return str;
    }
}