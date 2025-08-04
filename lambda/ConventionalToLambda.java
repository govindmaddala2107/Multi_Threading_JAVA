package lambda;

class ConventionalClass implements Runnable {
    @Override
    public void run() {
        System.out.println("ConventionalClass implements way");
    }
}

public class ConventionalToLambda {

    public static void main(String[] args) {
        ConventionalClass conventionalClass = new ConventionalClass();


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous class way");
            }
        };

        Runnable lambdaWay = () -> System.out.println("Lambda way");

        Thread anonymousThread = new Thread(runnable);
        anonymousThread.start();

        Thread lambdaThread = new Thread(lambdaWay);
        lambdaThread.start();

        Thread conventionalClassThread = new Thread(conventionalClass);
        conventionalClassThread.start();
    }
}
