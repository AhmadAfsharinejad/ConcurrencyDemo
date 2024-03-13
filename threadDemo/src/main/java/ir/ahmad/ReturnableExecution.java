package ir.ahmad;

import ir.ahmad.task.TaskThread;

public class ReturnableExecution {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");
        new ReturnableExecution().run();
    }

    public void run() throws InterruptedException {

        //if you want to get return value , you must create a runnable class which return result of execution
        var task = new TaskThread(1);

        Thread thread = new Thread(task);
        thread.start();
        thread.join();

        var result = task.getResult();
        //this is too complicated!
    }
}