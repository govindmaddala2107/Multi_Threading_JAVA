package executor_framework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorFrameworkClass {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int n = 9;

        // Normal method
        // for (int i = 1; i <= n; i++) {
        // long rslt = factorial(i);
        // System.out.println("Factorial of " + i + " is: " + rslt);
        // }

        // Thread way in normal way:
        // for (int i = 1; i <= n; i++) {
        // int finalI = i;
        // Thread thread = new Thread(() -> {
        // long rslt = factorial(finalI);
        // System.out.println("Factorial of " + finalI + " is: " + rslt);
        // });
        // thread.start();
        // }

        // Thread[] threads = new Thread[9];
        // for (int i = 1; i <= n; i++) {
        // int finalI = i;
        // threads[i - 1] = new Thread(() -> {
        // long rslt = factorial(finalI);
        // System.out.println("Factorial of " + finalI + " is: " + rslt);
        // });
        // threads[i - 1].start();
        // }

        // for (Thread thread : threads) {
        // try {
        // thread.join();
        // } catch (InterruptedException e) {
        // Thread.currentThread().interrupt();
        // }
        // }

        ExecutorService executor = Executors.newFixedThreadPool(9);

        for (int i = 1; i <= n; i++) {
            int finalI = i;
            executor.submit(() -> {
                long rslt = factorial(finalI);
                System.out.println("Factorial of " + finalI + " is: " + rslt);
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Total time for execution is: " + (System.currentTimeMillis() - startTime));
    }

    public static long factorial(int n) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (n <= 0)
            return 0;
        long rslt = 1;
        for (int i = 1; i <= n; i++) {
            rslt *= i;
        }
        return rslt;
    }
}

/*
 * 
 * Normal execution: takes around 9000 seconds
 * for (int i = 1; i <= n; i++) {
 * factorial(i)
 * }
 * 
 * Factorial of 1 is: 1
 * Factorial of 2 is: 2
 * Factorial of 3 is: 6
 * Factorial of 4 is: 24
 * Factorial of 5 is: 120
 * Factorial of 6 is: 720
 * Factorial of 7 is: 5040
 * Factorial of 8 is: 40320
 * Factorial of 9 is: 362880
 * Total time for execution is: 9082
 * 
 * With Normal Thread method, execution is completely become asynchronous
 * because that "Total time for execution is: " is executed at first go
 * 
 * Total time for execution is: 7
 * Factorial of 4 is: 24
 * Factorial of 8 is: 40320
 * Factorial of 2 is: 2
 * Factorial of 1 is: 1
 * Factorial of 7 is: 5040
 * Factorial of 5 is: 120
 * Factorial of 6 is: 720
 * Factorial of 3 is: 6
 * Factorial of 9 is: 362880
 * 
 * 
 * With creation of threads in array: [1029 seconds]
 * 
 * Thread[] threads = new Thread[9];
 * for (int i = 1; i <= n; i++) {
 * int finalI = i;
 * threads[i - 1] = new Thread(() -> {
 * long rslt = factorial(finalI);
 * System.out.println("Factorial of " + finalI + " is: " + rslt);
 * });
 * threads[i - 1].start();
 * }
 * 
 * for (Thread thread : threads) {
 * try {
 * thread.join();
 * } catch (InterruptedException e) {
 * Thread.currentThread().interrupt();
 * }
 * }
 * 
 *
 * Factorial of 9 is: 362880
 * Factorial of 2 is: 2
 * Factorial of 4 is: 24
 * Factorial of 3 is: 6
 * Factorial of 1 is: 1
 * Factorial of 6 is: 720
 * Factorial of 5 is: 120
 * Factorial of 8 is: 40320
 * Factorial of 7 is: 5040
 * Total time for execution is: 1029
 * 
 * 
 * With Executor way:
 * 
 * ExecutorService executor = Executors.newFixedThreadPool(9);
 * 
 * for (int i = 1; i <= n; i++) {
 * int finalI = i;
 * executor.submit(() -> {
 * long rslt = factorial(finalI);
 * System.out.println("Factorial of " + finalI + " is: " + rslt);
 * });
 * }
 * executor.shutdown();
 * try {
 * executor.awaitTermination(100, TimeUnit.SECONDS);
 * } catch (InterruptedException e) {
 * e.printStackTrace();
 * }
 * 
 * 
 * 
 * Factorial of 7 is: 5040
 * Factorial of 4 is: 24
 * Factorial of 8 is: 40320
 * Factorial of 9 is: 362880
 * Factorial of 2 is: 2
 * Factorial of 1 is: 1
 * Factorial of 3 is: 6
 * Factorial of 6 is: 720
 * Factorial of 5 is: 120
 * Total time for execution is: 1038
 */
