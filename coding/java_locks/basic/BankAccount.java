public class BankAccount {
    private int balance = 100;

    public synchronized void withDrawal(int amount) {
        System.out.println(Thread.currentThread().getName() + " attempting to withdraw " + amount);
        if (balance >= amount) {
            System.out.println(Thread.currentThread().getName() + " processing withdrawal ");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " completed withdrawal and remaining balance is "+ balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " insufficient balance.");
        }
    }
}

/*
Without Synch:
    Thread-1 attempting to withdraw 50
    Thread-2 attempting to withdraw 50
    Thread-2 processing withdrawal    
    Thread-1 processing withdrawal    
    Thread-2 completed withdrawal and remaining balance is 50
    Thread-1 completed withdrawal and remaining balance is 0

With Synchronized
    Thread-1 attempting to withdraw 50
    Thread-1 processing withdrawal 
    Thread-1 completed withdrawal and remaining balance is 50
    Thread-2 attempting to withdraw 50
    Thread-2 processing withdrawal    
    Thread-2 completed withdrawal and remaining balance is 0

 */
