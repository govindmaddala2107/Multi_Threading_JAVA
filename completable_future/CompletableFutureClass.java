package completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureClass {
    public static void main(String[] args) {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("F1-Thread-1");
            } catch (Exception e) {
            }
            return "F1-ok";
        });
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("F2-Thread-2");
            } catch (Exception e) {
            }
            return "F2-Ok";
        });
        CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("F3-Thread-1");
            } catch (Exception e) {
            }
            return "F3-Ok";
        });

        // Initiating single one:
        try {
            f1.get();
        } catch (InterruptedException | ExecutionException e) {
        }

        // Initiating many at a time.Here void is because it mentions that tasks are
        // finished but to get value of them , use f2.get() like that only.
        CompletableFuture<Void> joinFuture = CompletableFuture.allOf(f2, f3);
        joinFuture.join();

        // locking at first step itself
        try {
            String f4 = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(5000);
                    System.out.println("F4-Thread-4");
                } catch (Exception e) {
                }
                return "F4-ok";
            }).get();
            System.out.println(f4);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // callback kind of:
        CompletableFuture<String> f5 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("F5-Thread-5");
            } catch (Exception e) {
            }
            return "F5-ok";
        }).thenApplyAsync(x -> x + " || " + x);
        try {
            System.out.println(f5.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // timeout
        CompletableFuture<String> f6 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("F6-Thread-6");
            } catch (Exception e) {
            }
            return "F6-ok";
        }).orTimeout(1, TimeUnit.SECONDS).exceptionally(s -> "Timeout occured");
        /*
         * 
         * java.util.concurrent.ExecutionException:
         * java.util.concurrent.TimeoutException
         * at
         * java.base/java.util.concurrent.CompletableFuture.reportGet(CompletableFuture.
         * java:396)
         * at
         * java.base/java.util.concurrent.CompletableFuture.get(CompletableFuture.java:
         * 2073)
         * at
         * completable_future.CompletableFutureClass.main(CompletableFutureClass.java:
         * 85)
         * Caused by: java.util.concurrent.TimeoutException
         */
        try {
            System.out.println(f6.get());
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Passing executor
        ExecutorService executor = Executors.newFixedThreadPool(1);
        CompletableFuture<String> f7 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("F5-Thread-5");
            } catch (Exception e) {
            }
            return "F5-ok";
        }, executor);
        try {
            System.out.println(f7.get());
            executor.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Main");

    }
}
