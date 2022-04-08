package DB;

public class activity_counter {
    private static activity_counter counter = new activity_counter();
    int count = 0;

    private activity_counter(){

    }
    public static activity_counter getInstance(){
        return counter;
    }
    public int getCount(){
        return count++;
    }
}
