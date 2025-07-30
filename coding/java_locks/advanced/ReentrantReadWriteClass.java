import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteClass {

    private int count = 0;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public void increment() {
        writeLock.lock();
        try {
            count++;
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        } finally {
            writeLock.unlock();
        }
    }

    public int getCount() {
        readLock.lock();
        try {
            return count;
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantReadWriteClass counter = new ReentrantReadWriteClass();

        Runnable readTask = new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(
                            Thread.currentThread().getName() + " read: " + counter.getCount());
                }
            }
        };
        Runnable writeTask = new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    counter.increment();
                    System.out.println(
                            Thread.currentThread().getName() + " incremented");
                }
            }
        };

        Thread writeThread = new Thread(writeTask);
        Thread readThread1 = new Thread(readTask);
        Thread readThread2 = new Thread(readTask);

        writeThread.start();
        readThread1.start();
        readThread2.start();

        // writeThread.join();
        // readThread1.join();
        // readThread2.join();

        System.out.println("Final count is: " + counter.getCount());
    }

}

/*
 * 
 * OUTPUT:
 * 
 * Thread-0 incremented
 * Thread-0 incremented
 * Thread-0 incremented
 * Thread-0 incremented
 * Thread-0 incremented
 * Thread-0 incremented
 * Thread-0 incremented
 * Thread-0 incremented
 * Thread-0 incremented
 * Thread-0 incremented
 * Thread-1 read: 1 -------------> Might writelock got released and in this
 * meanwhile readlock read this and printed
 * Thread-2 read: 1 -------------> Might writelock got released and in this
 * meanwhile readlock read this and printed
 * Thread-1 read: 10 -------------> Might writelock finished increment while
 * readlock print the 1 and now readlock read this 10 and printed.
 * Thread-2 read: 10
 * Thread-1 read: 10
 * Thread-2 read: 10
 * Thread-1 read: 10
 * Thread-1 read: 10
 * Thread-2 read: 10
 * Thread-1 read: 10
 * Thread-2 read: 10
 * Thread-1 read: 10
 * Thread-2 read: 10
 * Thread-1 read: 10
 * Thread-2 read: 10
 * Thread-1 read: 10
 * Thread-2 read: 10
 * Thread-1 read: 10
 * Thread-2 read: 10
 * Thread-2 read: 10
 * Final count is: 10
 * 
 * 
 * 
 * With Thread.sleep(50) in increment, OUTPUT is as follows:
 * 
 * Thread-0 incremented
 * Thread-2 read: 1
 * Thread-1 read: 1
 * Thread-0 incremented
 * Thread-2 read: 2
 * Thread-1 read: 2
 * Thread-0 incremented
 * Thread-1 read: 3
 * Thread-2 read: 3
 * Thread-0 incremented
 * Thread-2 read: 4
 * Thread-1 read: 4
 * Thread-0 incremented
 * Thread-1 read: 5
 * Thread-2 read: 5
 * Thread-0 incremented
 * Thread-1 read: 6
 * Thread-2 read: 6
 * Thread-0 incremented
 * Thread-1 read: 7
 * Thread-2 read: 7
 * Thread-0 incremented
 * Thread-2 read: 8
 * Thread-1 read: 8
 * Thread-0 incremented
 * Thread-1 read: 9
 * Thread-2 read: 9
 * Thread-0 incremented
 * Thread-1 read: 10
 * Thread-2 read: 10
 * Final count is: 10
 * 
 * 
 * OUTPUT When join lines are commented:
 * 
 * Thread-0 incremented
 * Final count is: 1        ---> because of join lines are commented out, final count got executed.
 * Thread-2 read: 1
 * Thread-1 read: 1
 * Thread-0 incremented
 * Thread-1 read: 2
 * Thread-2 read: 2
 * Thread-0 incremented
 * Thread-2 read: 3
 * Thread-1 read: 3
 * Thread-0 incremented
 * Thread-1 read: 4
 * Thread-2 read: 4
 * Thread-0 incremented
 * Thread-1 read: 5
 * Thread-2 read: 5
 * Thread-0 incremented
 * Thread-2 read: 6
 * Thread-1 read: 6
 * Thread-0 incremented
 * Thread-1 read: 7
 * Thread-2 read: 7
 * Thread-0 incremented
 * Thread-1 read: 8
 * Thread-2 read: 8
 * Thread-0 incremented
 * Thread-2 read: 9
 * Thread-1 read: 9
 * Thread-0 incremented
 * Thread-1 read: 10
 * Thread-2 read: 10
 */
