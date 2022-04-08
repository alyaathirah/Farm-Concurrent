import DB.fetcher;
import DB.table;

import java.sql.*;
import java.util.Scanner;

public class InputLog {

    public void inputlog() {
        //int select = 0;
        Scanner input = new Scanner(System.in);  // Create a Scanner object
        System.out.println(" ");
        System.out.println("Which targets of activity log (input number 0 or 1 or 2 or 3 or 4 or 5) :");
        System.out.println(" 0 = Exit System \n 1 = Activity logs for a target farm \n 2 = Activity logs for a target farmer \n 3 = Activity logs for a target farm and plant / fertilizer / pesticide \n 4 = Activity logs for a target farm and plant / fertilizer / pesticide between date A and date B (inclusive) ");
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
        } else if (select == 0) {
            System.out.println("Thank You . Good Bye .");
        } else{
            System.out.println("Wrong Input");
            inputlog();
        }
    }


    //target farm
    public void target1(){
        Scanner input = new Scanner(System.in);
        System.out.println("Which farm (input number 1 or 2 or 3 or 4 or 5 or 6 or 7 or 8 or 9 or 10) : ");
        System.out.println("1 = farm1 \n 2 = farm2 \n 3 = farm3 \n 4 = farm4 \n 5 = farm5 \n 6 = farm6 \n 7 = farm7 \n 8 = farm8 \n 9 = farm9 \n 10 = farm10" );
        System.out.print("Input : ");
        int select = input.nextInt();


                String SQL = "SELECT action,type,field,row,quantity,unit,date FROM activities WHERE farm_id="+select;
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
        //System.out.println("1 = farm1 \n 2 = farm2 \n 3 = farm3 \n 4 = farm4 \n 5 = farm5 \n 6 = farm6 \n 7 = farm7 \n 8 = farm8 \n 9 = farm9 \n 10 = farm10" );
        System.out.print("Input : ");
        int select = input.nextInt();


        String SQL = "SELECT * FROM activities WHERE user_id="+select;
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
    public void target3(){
        Scanner input = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        System.out.println("Which farm (input number 1 to 10) : ");
        //System.out.println("1 = farm1 \n 2 = farm2 \n 3 = farm3 \n 4 = farm4 \n 5 = farm5 \n 6 = farm6 \n 7 = farm7 \n 8 = farm8 \n 9 = farm9 \n 10 = farm10" );
        System.out.print("Input : ");
        int select = input.nextInt();//farm
        System.out.println("Input plant name : ");
        System.out.print("Input : ");
        String select2 = input2.nextLine();//plant


        String SQL = "SELECT *  FROM `activities` WHERE `farm_id` LIKE " + "'" + select  + "'" + " AND `type` LIKE " + "'" + select2  + "'";
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
    public void target4(){
        Scanner input = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        Scanner input3 = new Scanner(System.in);
        Scanner input4 = new Scanner(System.in);
        System.out.println("Which farm (input number 1 to 10) : ");
        //System.out.println("1 = farm1 \n 2 = farm2 \n 3 = farm3 \n 4 = farm4 \n 5 = farm5 \n 6 = farm6 \n 7 = farm7 \n 8 = farm8 \n 9 = farm9 \n 10 = farm10" );
        System.out.print("Input : ");
        int select = input.nextInt();//farm
        System.out.println("Input plant name : ");
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
        try (Connection conn = table.getDatabaseConnection();
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

}