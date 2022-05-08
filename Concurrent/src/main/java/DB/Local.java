package DB;

import java.io.FileOutputStream;

public class Local {

    private static FileOutputStream fos;

    public Local() {
    }

    public static void save(String sql) {
        try {
            fos = new FileOutputStream("Local.txt", true);
            sql += "\n";
            fos.write(sql.getBytes());
        } catch (Exception e) {
            System.out.println("not able to save");
            System.out.println(e.getMessage());
        }
    }
}
