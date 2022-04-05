package DB;

import com.github.javafaker.Faker;

import java.sql.*;
import java.util.stream.Collectors;

public class fetcher {
    //fetches all users in db
    public String[] fetchUsers(){
        return null;
    }

    public String[] fetchFarmsperUser(){
        boolean result = false;
        String SQL = "INSERT INTO plants(name,unitType) "
                + "VALUES(?,?)";
        Array array = null;
        try (Connection conn = table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            array = table.getDatabaseConnection().createArrayOf("VARCHAR", new Object[]{"A1", "B2","C3"});
            pstmt.setArray(1, array);
            ResultSet rs = pstmt.executeQuery();
            System.out.println(rs);
        }
        catch (SQLException ex) {
            System.out.println("Fetch failed");
            System.out.println(ex.getMessage());
        }

        return null;
    }
}
