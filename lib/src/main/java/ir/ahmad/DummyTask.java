package ir.ahmad;

public class DummyTask {

    public static int runTask(int index) {

        System.out.println("Task " + index + " started.");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Task " + index + " completed.");

        return index;
    }
}