import DB.fetcher;
import DB.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class InputLog {

    public void inputlog(){
        //int select = 0;
        Scanner input = new Scanner(System.in);  // Create a Scanner object

        System.out.println("Which targets of activity log (input number 1 or 2 or 3 or 4 or 5) :");
        System.out.println("1 = activity logs for a target farm \n 2 = activity logs for a target farmer \n 3 = activity logs for a target farm and plant / fertilizer / pesticide \n 4 = activity logs for a target farm and plant / fertilizer / pesticide \n 5 = activity logs for a target farm and plant / fertilizer / pesticide between date A and date B (inclusive) ");
        System.out.print("Input : ");
        int select = input.nextInt();

        if(select == 1){
            target1();
        }else if(select == 2){
            target2();
        }else if(select == 3){
            target3();
        }else if(select == 4){
            target4();
        }else if(select == 5){
            target5();
        }

    }

    public void target1(){
        Scanner input = new Scanner(System.in);
        System.out.println("Which (input number 1 or 2 or 3 or 4 or 5 or 6 or 7 or 8 or 9 or 10) : ");
        System.out.println("1 = farm1 \n 2 = farm2 \n 3 = farm3 \n 4 = farm4 \n 5 = farm5 \n 6 = farm6 \n 7 = farm7 \n 8 = farm8 \n 9 = farm9 \n 10 = farm10" );
        int select = input.nextInt();
        switch(select) {
            case 1:
                String SQL = "SELECT action,type,field,row,quantity,unit FROM activities WHERE farm_id="+1;
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
                                str = (rs.getString("action"+"type"+"field"+"row"+"quantity"+"unit1"));
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
                System.out.println(str);
                break;
            case 2:
                String SQL2 = "SELECT * FROM activities WHERE farm_id="+2;
            case 3:
                String SQL3 = "SELECT * FROM activities WHERE farm_id="+3;
            case 4:
                String SQL4 = "SELECT * FROM activities WHERE farm_id="+4;
            case 5:
                String SQL5 = "SELECT * FROM activities WHERE farm_id="+5;
            case 6:
                String SQL6 = "SELECT * FROM activities WHERE farm_id="+6;
            case 7:
                String SQL7 = "SELECT * FROM activities WHERE farm_id="+7;
            case 8:
                String SQL8 = "SELECT * FROM activities WHERE farm_id="+8;
            case 9:
                String SQL9 = "SELECT * FROM activities WHERE farm_id="+9;
            case 10:
                String SQL10 = "SELECT * FROM activities WHERE farm_id="+10;
                break;
            default:
                System.out.println("Wrong Input");
                target1();
        }
    }

    public void target2(){

    }
    public void target3(){

    }
    public void target4(){

    }
    public void target5(){

    }
}
