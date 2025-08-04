package executor_framework;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierClass {
    public static void main(String[] args) {
        int count = 3;
        ExecutorService executor = Executors.newFixedThreadPool(count);
        CyclicBarrier barrier = new CyclicBarrier(count, () -> System.out.println("System is up and running."));
        executor.submit(new DependentService(barrier));
        executor.submit(new DependentService(barrier));
        executor.submit(new DependentService(barrier));
        System.out.println("Main");
        executor.shutdown();

        /*
         * Main
         * pool-1-thread-1 service started...
         * pool-1-thread-2 service started...
         * pool-1-thread-3 service started...
         * pool-1-thread-1 is waiting at the barrier.
         * pool-1-thread-2 is waiting at the barrier.
         * pool-1-thread-3 is waiting at the barrier.
         * 
         * 
         * With
         * CyclicBarrier barrier = new CyclicBarrier(count, ()->
         * System.out.println("System is up and running."));
         * 
         * Main
         * pool-1-thread-3 service started...
         * pool-1-thread-1 service started...
         * pool-1-thread-2 service started...
         * pool-1-thread-1 is waiting at the barrier.
         * pool-1-thread-3 is waiting at the barrier.
         * pool-1-thread-2 is waiting at the barrier.
         * System is up and running.
         */
    }
}

class DependentService implements Callable<String> {

    private final CyclicBarrier barrier;

    public DependentService(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " service started...");
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + " is waiting at the barrier.");
        barrier.await();
        return "Ok";
    }

}
