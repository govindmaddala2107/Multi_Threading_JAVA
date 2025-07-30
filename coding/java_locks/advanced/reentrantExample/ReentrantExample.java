package reentrantExample;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantExample {
    private Lock lock = new ReentrantLock();

    public void outerMethod() {
        lock.lock();
        try {
            System.out.println("Outer method");
            innerMethod();
        } finally {
            lock.unlock();
        }
    }

    public void innerMethod() {
        lock.lock();
        try {
            System.out.println("Inner method");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantExample re = new ReentrantExample();
        re.outerMethod();
    }
}

/*
 * lock -> lock -> unlock -> unlock
 * 
 * Outer method
 * Inner method
 * 
 * lock -> unblock -> unblock
 * 
 * Outer method
 * Inner method
 * Exception in thread "main" java.lang.IllegalMonitorStateException
 * at java.base/java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(
 * ReentrantLock.java:175)
 * at java.base/java.util.concurrent.locks.AbstractQueuedSynchronizer.release(
 * AbstractQueuedSynchronizer.java:1007)
 * at
 * java.base/java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:
 * 494)
 * at reentrantExample.ReentrantExample.outerMethod(ReentrantExample.java:15)
 * at reentrantExample.ReentrantExample.main(ReentrantExample.java:30)
 * 
 * 
 * 
 * lock -> unblock
 * 
 * Outer method
 * Inner method
 */