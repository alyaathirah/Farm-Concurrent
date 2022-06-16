package DB;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class activity_counter {
    private static activity_counter counter = new activity_counter();
    Lock lock = new ReentrantLock();

    int count = 1;

    public static activity_counter getInstance(){
        return counter;
    }
    public int getCount(){
        lock.lock();
        try {
            return count++;
        } finally {
            lock.unlock();
        }

    }
}
