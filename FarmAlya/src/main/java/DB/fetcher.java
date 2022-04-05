package DB;

import com.github.javafaker.Faker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class fetcher {
    //fetches all users in db
    public String[] fetchUsers(){
        return null;
    }

    public String[] fetchFarmsByUser(int userID){
        ArrayList<String> tempArray = new ArrayList<String>();
        boolean result = false;
        String SQL = "SELECT farm_id FROM farmables WHERE farmable_id="+userID;
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

        }
        catch (SQLException ex) {
            System.out.println("Fetch failed");
            System.out.println(ex.getMessage());
        }

        String[] arr = tempArray.toArray(new String[0]);
        return arr;
    }
    public String[] fetchFarmablesByFarm(int farmID, String farmableType){ //types:farmer, plant, fertilizer, pesticide
        ArrayList<String> tempArray = new ArrayList<String>();
        boolean result = false;
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
                        tempArray.add(rs.getString("farmable_id"));
                        System.out.println(rs.getString("farmable_id")+" "+rs.getString("farmable_type"));

                    }
                    rs.close();
                }
                results = pstmt.getMoreResults();
            } while(results);
            pstmt.close();

        }
        catch (SQLException ex) {
            System.out.println("Fetch failed");
            System.out.println(ex.getMessage());
        }

        String[] arr = tempArray.toArray(new String[0]);
        return arr;
    }
}
//array = table.getDatabaseConnection().createArrayOf("VARCHAR", new Object[]{"A1", "B2","C3"});
//pstmt.setArray(1, array);