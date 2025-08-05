package volatile_atomic.atomic_class;

public class VolatileCounter {
    private volatile int counter = 0;

    public void increment() {
        counter++;
    }

    public void getCounter() {
        System.out.println(counter);
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileCounter vc = new VolatileCounter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                vc.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                vc.increment();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        vc.getCounter(); // < 2000 only
    }
}
