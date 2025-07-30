import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccountAdvanced {
    private int balance = 100;

    private final Lock lock = new ReentrantLock();

    public void withDrawal(int amount) {

        System.out.println(Thread.currentThread().getName() + " attempting to withdraw " + amount);
        try {
            // if (lock.tryLock(2000, TimeUnit.MILLISECONDS)) {
            if (lock.tryLock()) {
                if (balance >= amount) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " processing withdrawal ");
                        // Thread.sleep(100);
                        balance -= amount;
                        System.out.println(Thread.currentThread().getName()
                                + " completed withdrawal and remaining balance is " + balance);
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " insufficient balance.");
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " could not acquired the lock. Will try later");
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        if(Thread.currentThread().isInterrupted()){
            // Keep code related to clean-up if there is interrrupted.
        }
    }
}

/*
 * With 3000 thread sleep and tryLock for 2 seconds:
 * 
 * Thread-1 attempting to withdraw 50
 * Thread-2 attempting to withdraw 50
 * Thread-1 processing withdrawal
 * Thread-2 could not acquired the lock. Will try later
 * Thread-1 completed withdrawal and remaining balance is 50
 * 
 * With 100 thread sleep and tryLock for 2 seconds:
 * 
 * Thread-1 attempting to withdraw 50
 * Thread-2 attempting to withdraw 50
 * Thread-1 processing withdrawal
 * Thread-1 completed withdrawal and remaining balance is 50
 * Thread-2 processing withdrawal
 * Thread-2 completed withdrawal and remaining balance is 0
 * 
 */

/*
 * With 100 thread sleep and tryLock with no params:
 * 
 * Thread-1 attempting to withdraw 50
 * Thread-2 attempting to withdraw 50
 * Thread-1 processing withdrawal
 * Thread-2 could not acquired the lock. Will try later
 * Thread-1 completed withdrawal and remaining balance is 50
 * 
 */
