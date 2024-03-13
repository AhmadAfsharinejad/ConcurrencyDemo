package ir.ahmad;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        new Main().run();
    }

    public void run() {

        for (int i = 0; i < 5; i++) {

            DummyTask.runTask(i);
        }
        //Note: each task wait for previous task
    }
}