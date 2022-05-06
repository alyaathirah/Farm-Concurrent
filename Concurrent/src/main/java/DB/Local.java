package DB;

import java.io.FileOutputStream;

public class Local {

    private static FileOutputStream fos;

    public Local()
    {
        try {
            fos = new FileOutputStream("Local.txt", true);
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public void save(String sql) {

        try {
            sql += "\n";
            fos.write(sql.getBytes());
        } catch (Exception e) {
            System.out.println("not able to save");
            System.out.println(e.getMessage());
        }
    }
}
