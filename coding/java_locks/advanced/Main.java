public class Main {
    public static void main(String[] args) {
        BankAccountAdvanced sbi = new BankAccountAdvanced();
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                sbi.withDrawal(50);
            }
        };

        Thread t1 = new Thread(thread, "Thread-1");
        Thread t2 = new Thread(thread, "Thread-2");
        t1.start();
        t2.start();
    }
}
