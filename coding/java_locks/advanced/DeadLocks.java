class Pen {
    public synchronized void writeWithPenAndPaper(Paper paper) {
        System.out.println(Thread.currentThread().getName() + " is using pen " + this + " and trying to user "+ paper);
        paper.finishWriting();
    }

    public synchronized void finishWriting() {
        System.out.println(Thread.currentThread().getName() + " finished using pen " + this);
    }
}

class Paper {
    public synchronized void writeWithPenAndPaper(Pen pen) {
        System.out.println(Thread.currentThread().getName() + " is using paper " + this + " and trying to use" + pen);
        pen.finishWriting();
    }

    public synchronized void finishWriting() {
        System.out.println(Thread.currentThread().getName() + " finished using paper " + this);
    }
}

class PenTask implements Runnable {

    private Pen pen;
    private Paper paper;

    public PenTask(Pen pen, Paper paper) {
        this.pen = pen;
        this.paper = paper;
    }

    @Override
    public void run() {
        pen.writeWithPenAndPaper(paper); // pen thread locks pen and tried to lock paper
    }

}

class PaperTask implements Runnable {

    private Pen pen;
    private Paper paper;

    public PaperTask(Pen pen, Paper paper) {
        this.pen = pen;
        this.paper = paper;
    }

    @Override
    public void run() {
        paper.writeWithPenAndPaper(pen); // paper thread locks paper and tried to lock pen
    }

}

public class DeadLocks {

    public static void main(String[] args) {
        Pen pen = new Pen();
        Paper paper = new Paper();

        Thread penThread = new Thread(new PenTask(pen, paper), "Pen-Thread");
        Thread paperThread = new Thread(new PaperTask(pen, paper), "Paper-Thread");
        penThread.start();
        paperThread.start();
    }
}
