package ir.ahmad;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Hello world!");
//        new Main().runRaceCondition();
        new Main().runRaceSolution();
        //new Main().runRaceSolution2();
    }

    private int counter = 0;
    private final int loopCount = 100000;

    public void runRaceCondition() throws InterruptedException, ExecutionException {

        var addFuture = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < loopCount; i++) {
                counter++;
            }
        });

        var minusFuture = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < loopCount; i++) {
                counter--;
            }
        });


        CompletableFuture.allOf(addFuture, minusFuture).get();

        System.out.println("Final counter value for RaceCondition: " + counter);
    }

    private final Object synchronizedObj = new Object();

    public void runRaceSolution() throws InterruptedException, ExecutionException {

        var addFuture = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < loopCount; i++) {
                synchronized (synchronizedObj) {
                    counter++;
                }
            }
        });

        var minusFuture = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < loopCount; i++) {
                //Note: The lock behind the synchronized methods and blocks is a reentrant.
                // This means the current thread can acquire the same synchronized lock over and over again while holding it.
                synchronized (synchronizedObj) {
                    synchronized (synchronizedObj) {
                        counter--;
                    }
                }
            }
        });


        CompletableFuture.allOf(addFuture, minusFuture).get();

        System.out.println("Final counter value for Solution: " + counter);
    }

    public void runRaceSolution2() throws InterruptedException, ExecutionException {

        var addFuture = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < loopCount; i++) {
                addCounter();
            }
        });

        var minusFuture = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < loopCount; i++) {
                minusCounter();
            }
        });

        CompletableFuture.allOf(addFuture, minusFuture).get();

        System.out.println("Final counter value for Solution: " + counter);
    }

    private synchronized void addCounter() {
        counter++;
    }

    private synchronized void minusCounter() {
        counter--;
    }
}