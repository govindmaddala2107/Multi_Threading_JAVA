package coding.thread_communication;

class SharedResource {
    private int data;
    private boolean hasData;

    public synchronized void produce(int value) {
        while (hasData) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        data = value;
        hasData = true;
        System.out.println("Produced value is: " + value);
        notify();
    }

    public synchronized int consume() {
        while (!hasData) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        hasData = false;
        notify();
        System.out.println("Produced value is: " + data);
        return data;
    }
}

class Producer implements Runnable {
    SharedResource sharedResource;

    public Producer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            sharedResource.produce(i);
        }
    }

}

class Consumer implements Runnable {
    SharedResource sharedResource;

    public Consumer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            sharedResource.consume();
        }
    }

}

public class ThreadCommunication {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();
        Thread producerThread = new Thread(new Producer(sharedResource));
        Thread consumerThread = new Thread(new Consumer(sharedResource));

        producerThread.start();
        consumerThread.start();
    }
}
