package unfair_fair;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnfairFair {
    private final Lock lock = new ReentrantLock();

    public void accessResource() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " acquired the lock.");
            Thread.sleep(100);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName() + " released the lock.");
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        UnfairFair uf = new UnfairFair();

        Runnable task = new Runnable() {

            @Override
            public void run() {
                uf.accessResource();
            }

        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        Thread t3 = new Thread(task, "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        // try {
        //     t1.start();
        //     Thread.sleep(50);
        //     t2.start();
        //     Thread.sleep(50);
        //     t3.start();
        // } catch (Exception e) {
        // }

    }
}

/*
 * UNFAIR: private final Lock lock = new ReentrantLock();
 * 
 * Thread-1 acquired the lock.
 * Thread-1 released the lock.
 * Thread-3 acquired the lock.
 * Thread-3 released the lock.
 * Thread-2 acquired the lock.
 * Thread-2 released the lock.
 * 
 * 
 * FAIR: private final Lock lock = new ReentrantLock(true); ===> Here order of
 * request is 1,3,2 but internal execution is fair,
 * 
 * Thread-1 acquired the lock.
 * Thread-1 released the lock.
 * Thread-3 acquired the lock.
 * Thread-3 released the lock.
 * Thread-2 acquired the lock.
 * Thread-2 released the lock.
 *
 * 
 * 
 * 
 * For:
 * 
 * 
 * try {
 * t1.start();
 * Thread.sleep(50);
 * t2.start();
 * Thread.sleep(50);
 * t3.start();
 * } catch (Exception e) {
 * }
 * 
 * 
 * Thread-1 acquired the lock.
 * Thread-1 released the lock.
 * Thread-2 acquired the lock.
 * Thread-2 released the lock.
 * Thread-3 acquired the lock.
 * Thread-3 released the lock.
 */
