package executor_framework;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorFrameworkClass1 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService cbWay = Executors.newSingleThreadExecutor();
        ExecutorService cache = Executors.newCachedThreadPool();
        ExecutorService runWay = Executors.newSingleThreadExecutor();
        ExecutorService runAndResult = Executors.newSingleThreadExecutor();
        Callable<String> cb = () -> "Callable Method";
        Runnable runnable = () -> System.out.println("Runnable method");
        Future<String> futureCB = cbWay.submit(cb);
        Future<?> futureRW = runWay.submit(runnable);   // Runnable method
        Future<String> runResult = runAndResult.submit(()-> System.out.println("Runnable in run and result"), "Task is finished"); // Runnable in run and result
        
        cbWay.shutdownNow();   // Attempts to stop all actively executing tasks, halts the processing of waiting tasks, and returns a list of the tasks that were awaiting execution.
        System.out.println(futureCB.get());     // Callable Method
        System.out.println(futureRW.get());     // null
        System.out.println(runResult.get());     // Task is finished
        cbWay.shutdown();
        runWay.shutdown();
        runAndResult.shutdown(); 
        System.out.println(runAndResult.isShutdown());   // true
        Thread.sleep(1);
        System.out.println(runAndResult.isTerminated());   // true with above Thread.sleep(), else false because it takes time.

    }
}

