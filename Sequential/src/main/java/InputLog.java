
import DB.Table;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputLog {

    public void inputlog() {
        Scanner input = new Scanner(System.in);  // Create a Scanner object
        try{
        System.out.println(" ");
        System.out.println("Which targets of activity log (input number 0 or 1 or 2 or 3 or 4 or 5) :");
        System.out.println(" 0 = Exit System \n 1 = Activity logs for a target farm \n 2 = Activity logs for a target farmer \n 3 = Activity logs for a target farm and plant / fertilizer / pesticide \n 4 = Activity logs for a target farm and plant / fertilizer / pesticide between date A and date B (inclusive) \n 5 = Summarized logs by plants, fertilizers and pesticides for a target farm and plant / fertilizer / pesticide between date A and date B (inclusive) for selected field and row number.");
        System.out.print("Input : ");
        int select = input.nextInt();

        if (select == 1) {
            target1();
            inputlog();
        } else if (select == 2) {
            target2();
            inputlog();
        } else if (select == 3) {
            target3();
            inputlog();
        } else if (select == 4) {
            target4();
            inputlog();
        }else if (select == 5) {
            target5();
            inputlog();    
        } else if (select == 0) {
            System.out.println("Thank You . Good Bye .");
        } else{
            System.out.println("Wrong Input, Out of Choice");
            inputlog();
        }
        input.close();
        }catch(InputMismatchException e){
            System.out.println("Invalid input type");
            inputlog();
        }
    }


    //target farm
    public void target1(){
        Scanner input = new Scanner(System.in);
        System.out.println("Which farm (input number 1 to 10) : ");
        //input farm
        System.out.print("Input : ");
        int select = input.nextInt();

        //display target farm data from database
        String SQL = "SELECT action,type,field,row,quantity,unit,date FROM activities WHERE farm_id="+select;
        String str = "";
        try (Connection conn = Table.getDatabaseConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                boolean results = pstmt.execute(SQL);
                //Loop through the available result sets.
                do {
                    if(results) {
                        ResultSet rs = pstmt.getResultSet();
                        //Show data from the result set.
                        while (rs.next()) {
                            str = (rs.getString("action")+" "+rs.getString("type")+" Field "+rs.getString("field")+" Row "+rs.getString("row")+" "+rs.getString("quantity")+rs.getString("unit")+" "+rs.getString("date"));
                            System.out.println(str);
                        }
                        rs.close();
                    }
                    results = pstmt.getMoreResults();
                } while(results);
            }
            catch (SQLException ex) {
                System.out.println("Fetch failed");
                System.out.println(ex.getMessage());
            }
}

    //target farmer
    public void target2(){
        Scanner input = new Scanner(System.in);
        System.out.println("Which farmer (input number 1 to 100) : ");
        System.out.print("Input : ");
        int select = input.nextInt();

        //display target farmer data from database
        String SQL = "SELECT * FROM activities WHERE user_id="+select;
        String str = "";
        try (Connection conn = Table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            boolean results = pstmt.execute(SQL);
            //Loop through the available result sets.
            do {
                if(results) {
                    ResultSet rs = pstmt.getResultSet();
                    //Show data from the result set.
                    while (rs.next()) {
                        str = (rs.getString("action")+" "+rs.getString("type")+" Field "+rs.getString("field")+" Row "+rs.getString("row")+" "+rs.getString("quantity")+rs.getString("unit")+" "+rs.getString("date"));
                        System.out.println(str);
                    }
                    rs.close();
                }
                results = pstmt.getMoreResults();
            } while(results);
        }
        catch (SQLException ex) {
            System.out.println("Fetch failed");
            System.out.println(ex.getMessage());
        }
    }

    // target farm and plant / fertilizer / pesticide
    public void target3(){
        Scanner input = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        System.out.println("Which farm (input number 1 to 10) : ");
        System.out.print("Input : ");
        int select = input.nextInt();//farm

        selectedFarm(select);

        System.out.println("Input plant / fertilizer / pesticide name : ");
        System.out.print("Input : ");
        String select2 = input2.nextLine();//plant

        String SQL = "SELECT *  FROM `activities` WHERE `farm_id` LIKE " + "'" + select  + "'" + " AND `type` LIKE " + "'" + select2  + "'";
        String str = "";
        try (Connection conn = Table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            boolean results = pstmt.execute(SQL);
            //Loop through the available result sets.
            do {
                if(results) {
                    ResultSet rs = pstmt.getResultSet();
                    //Show data from the result set.
                    while (rs.next()) {
                        str = (rs.getString("action")+" "+rs.getString("type")+" Field "+rs.getString("field")+" Row "+rs.getString("row")+" "+rs.getString("quantity")+rs.getString("unit")+" "+rs.getString("date"));
                        System.out.println(str);
                    }
                    rs.close();
                }
                results = pstmt.getMoreResults();
            } while(results);
        }
        catch (SQLException ex) {
            System.out.println("Fetch failed");
            System.out.println(ex.getMessage());
        }
    }

    //target farm and plant / fertilizer / pesticide between 2 dates
    public void target4(){
        Scanner input = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        Scanner input3 = new Scanner(System.in);
        Scanner input4 = new Scanner(System.in);

        System.out.println("Which farm (input number 1 to 10) : ");
        System.out.print("Input : ");
        int select = input.nextInt();//farm

        selectedFarm(select);

        System.out.println("Input plant / fertilizer / pesticide name : ");
        System.out.print("Input : ");
        String select2 = input2.nextLine();//plant
        System.out.println("Input start date : ");
        System.out.print("Input : ");
        String select3 = input3.nextLine();//start date
        System.out.println("Input end date : ");
        System.out.print("Input : ");
        String select4 = input4.nextLine();//end date

        String SQL = "SELECT *  FROM `activities` WHERE `farm_id` LIKE " + "'" + select  + "'" + " AND `type` LIKE " + "'" + select2  + "'" + " AND `date` between " + "'" + select3  + "'" + " AND " + "'" + select4  + "'";
        String str = "";
        try (Connection conn = Table.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            boolean results = pstmt.execute(SQL);
            //Loop through the available result sets.
            do {
                if(results) {
                    ResultSet rs = pstmt.getResultSet();
                    //Show data from the result set.
                    while (rs.next()) {
                        str = (rs.getString("action")+" "+rs.getString("type")+" Field "+rs.getString("field")+" Row "+rs.getString("row")+" "+rs.getString("quantity")+rs.getString("unit")+" "+rs.getString("date"));
                        System.out.println(str);
                    }
                    rs.close();
                }
                results = pstmt.getMoreResults();
            } while(results);
        }
        catch (SQLException ex) {
            System.out.println("Fetch failed");
            System.out.println(ex.getMessage());
        }
    }

   public void target5(){ 
    Scanner input = new Scanner(System.in);
    Scanner input2 = new Scanner(System.in);
    Scanner input3 = new Scanner(System.in);
    Scanner input4 = new Scanner(System.in);
    Scanner input5 = new Scanner(System.in);
    Scanner input6 = new Scanner(System.in);

    System.out.println("Which farm (input number 1 to 10) : ");
    System.out.print("Input : ");
    int select = input.nextInt();//farm
    
    selectedFarm(select);
  
    // input the target plant / fertilizer / pesticide from the list display
    System.out.println("Input plant / fertilizer / pesticide name : ");
    System.out.print("Input : ");
    String select2 = input2.nextLine();//plant
    System.out.println("Input start date : (yyyy-mm-dd)");
    System.out.print("Input : ");
    String select3 = input3.nextLine();//start date
    System.out.println("Input end date : (yyyy-mm-dd)");
    System.out.print("Input : ");
    String select4 = input4.nextLine();//end date
    System.out.print("Input field: ");
    int select5 = input5.nextInt();//field
    System.out.print("Input row: ");
    int select6 = input6.nextInt();//row

    //display summarized log
    String SQL = "SELECT `action`, `type`, `field`, `row`, `quantity`, `unit`, SUM(`quantity`) AS `sum-quantity` FROM `activities` WHERE `farm_id` LIKE " + "'" + select  + "'" + " AND `type` LIKE " + "'" + select2  + "'" + "AND `field` = " + select5 + " AND `row` = "+ select6 + " AND (`date` between " + "'" + select3  + "'" + " AND " + "'" + select4+ "')" + " GROUP BY `action` HAVING COUNT(`action`)>=1";
    String str ="";  
    try (Connection conn = Table.getDatabaseConnection();
         Statement stmt = conn.createStatement();) {
        //Loop through the available result sets.
        ResultSet rs = stmt.executeQuery(SQL);
        //Show data from the result set.
        while(rs.next()){
            String action = rs.getString("action");
            String type = rs.getString("type");
            int field = rs.getInt("field");
            int row = rs.getInt("row");
            double quantity = rs.getDouble("sum-quantity");
            String unit = rs.getString("unit"); 
            str = action+" "+type+" Field "+field+" Row "+row+" "+quantity+unit;
            System.out.println(str);
        }
       
        stmt.close();      
    }
    catch (SQLException ex) {
        System.out.println("Fetch failed");
        System.out.println(ex.getMessage());
    }
    }

    // display the data result from selected farm
    public void selectedFarm(int select){
        //display the selected farm data result to choose plant/fertilizer/pesticide
    String SQL1 = "SELECT action,type,field,row,quantity,unit,date FROM activities WHERE farm_id="+select;
    String str1 = "";
    try (Connection conn = Table.getDatabaseConnection();
         PreparedStatement pstmt = conn.prepareStatement(SQL1)) {
        boolean results = pstmt.execute(SQL1);
        //Loop through the available result sets.
        do {
            if(results) {
                ResultSet rs = pstmt.getResultSet();
                //Show data from the result set.
                while (rs.next()) {
                    str1 = (rs.getString("action")+" "+rs.getString("type")+" Field "+rs.getString("field")+" Row "+rs.getString("row")+" "+rs.getString("quantity")+rs.getString("unit")+" "+rs.getString("date"));
                    System.out.println(str1);
                }
                rs.close();
            }
            results = pstmt.getMoreResults();
        } while(results);
    }
    catch (SQLException ex) {
        System.out.println("Fetch failed");
        System.out.println(ex.getMessage());
    }
    }
}