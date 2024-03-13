package ir.ahmad;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class deadLockExecution {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Hello world!");
        new deadLockExecution().run();
    }

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public void run() throws InterruptedException, ExecutionException {

        var future1 = CompletableFuture.runAsync(() -> {
            synchronized (lock1) {

                System.out.println("Thread 1 acquired lock1");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (lock2) {
                    System.out.println("Thread 1 acquired lock2");
                }
            }
        });

        var future2 = CompletableFuture.runAsync(() -> {
            synchronized (lock2) {

                System.out.println("Thread 2 acquired lock2");

                synchronized (lock1) {
                    System.out.println("Thread 2 acquired lock1");
                }
            }
        });

        CompletableFuture.allOf(future1, future2).get();

        System.out.println("Finish");

        //Note: run this command in terminal to detect deadlock:
        //jcmd $PID Thread.print
    }
}
