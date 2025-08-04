package count_down_latch;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CountDownLatchClass {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<String> future1 = executorService.submit(new DependentService());
        Future<String> future2 = executorService.submit(new DependentService());
        Future<String> future3 = executorService.submit(new DependentService());

        future1.get();
        future2.get();
        future3.get();
        System.out.println("All dependent services are finished. Starting main service...");
        executorService.shutdown();

        /*
         * pool-1-thread-1 service started...
         * pool-1-thread-3 service started...
         * pool-1-thread-2 service started...
         * All dependent services are finished. Starting main service...
         */

        // With Latch
        System.out.println("-------------------->LATCH<--------------------");
        int count = 3;
        ExecutorService executorServiceWithLatch = Executors.newFixedThreadPool(count);
        CountDownLatch latch = new CountDownLatch(count);
        executorServiceWithLatch.submit(new DependentServiceWithLatch(latch));
        executorServiceWithLatch.submit(new DependentServiceWithLatch(latch));
        executorServiceWithLatch.submit(new DependentServiceWithLatch(latch));
        latch.await();
        System.out.println("Latch All dependent services are finished. Starting main service...");
        executorServiceWithLatch.shutdown();
        System.out.println("-------------------->LATCH<--------------------");
        // With Latch and Runnable
        System.out.println("-------------------->Latch and Runnable<--------------------");
        CountDownLatch executorServiceWithLatchRunnable = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(new DependentServiceWithRunnable(executorServiceWithLatchRunnable)).start();
        }
        executorServiceWithLatchRunnable.await(5, TimeUnit.SECONDS);
        System.out.println("LatchAndRunnable All dependent services are finished. Starting main service...");
        System.out.println("-------------------->Latch and Runnable<--------------------");

        /*
         * 
         * executorServiceWithLatchRunnable.await();
         * -------------------->Latch and Runnable<--------------------
         * Thread-2 service started...
         * Thread-1 service started...
         * Thread-0 service started...
         * LatchAndRunnable All dependent services are finished. Starting main
         * service...
         * -------------------->Latch and Runnable<--------------------
         */

        /*
         * executorServiceWithLatchRunnable.await(5, TimeUnit.SECONDS);
         * 
         * -------------------->Latch and Runnable<--------------------
         * LatchAndRunnable All dependent services are finished. Starting main
         * service...
         * -------------------->Latch and Runnable<--------------------
         * Thread-2 service started...
         * Thread-0 service started...
         * Thread-1 service started...
         */

    }

}

class DependentService implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " service started...");
        return "Ok";
    }

}

class DependentServiceWithLatch implements Callable<String> {

    private final CountDownLatch latch;

    public DependentServiceWithLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public String call() throws Exception {
        try {
            System.out.println(Thread.currentThread().getName() + " service started...");
            Thread.sleep(2000);
        } finally {
            latch.countDown();
        }
        return "Ok";
    }

}

class DependentServiceWithRunnable implements Runnable {

    private final CountDownLatch latch;

    public DependentServiceWithRunnable(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(6000);
            System.out.println(Thread.currentThread().getName() + " service started...");
        } catch (Exception e) {

        } finally {
            latch.countDown();
        }
    }
}
