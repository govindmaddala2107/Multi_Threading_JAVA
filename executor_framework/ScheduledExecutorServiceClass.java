package executor_framework;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceClass {
    public static void main(String[] args) {
        ScheduledExecutorService schedular = Executors.newScheduledThreadPool(1);

        // Submits a periodic action that becomes enabled first after the given initial
        // delay, and subsequently with the given period; that is, executions will
        // commence after initialDelay, then initialDelay + period, then initialDelay +
        // 2 * period, and so on.

        // Simple words: Might task takes more than 2 seconds. It runs again even if before task is running.
        ScheduledFuture<?> scheduleAtFixedRateFuture = schedular.scheduleAtFixedRate(
                () -> System.out.println("Task executed at every 5 seconds"), 2, 2, TimeUnit.SECONDS);

        // try {
        //     scheduleAtFixedRateFuture.get();
        // } catch (InterruptedException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // } catch (ExecutionException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }

        // Submits a periodic action that becomes enabled first after the given initial
        // delay, and subsequently with the given delay between the termination of one
        // execution and the commencement of the next.

        // Simple words: Might task takes more than 2 seconds. It wait until that task finishes and then takes 2 seconds delay and then run again
        ScheduledFuture<?> scheduleWithFixedDelay = schedular.scheduleWithFixedDelay(
                () -> System.out.println("Task with fixed delay executed at every 5 seconds"), 2, 2, TimeUnit.SECONDS);

        schedular.schedule(() -> {
            System.out.println("Intializing timeout...!");
            schedular.shutdown();
        }, 10, TimeUnit.SECONDS);
    }
}
