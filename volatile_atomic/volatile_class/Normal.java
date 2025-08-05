package volatile_atomic.volatile_class;

class SharedObjNormal{
    private boolean flag = false;

    public void setFlagTrue(){
        System.out.println("Writer thread made the flag true");
        flag = true;
    }

    public void printFlagTrue(){
        while (!flag) {
            // do nothing
        }
        System.out.println("Flag is true");
    }
}
public class Normal {
    public static void main(String[] args) {
        SharedObjNormal obj = new SharedObjNormal();

        Thread writerThread = new Thread(()-> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
            obj.setFlagTrue();
        });
        Thread readThread = new Thread(()-> obj.printFlagTrue());

        writerThread.start();
        readThread.start();
    }
}
