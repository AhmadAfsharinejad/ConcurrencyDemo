package ir.ahmad;

import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Hello world!");
        new Main().run();
    }

    public void run() throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        //Note: check other ExecutorService like newSingleThreadExecutor(), newFixedThreadPool()

        var futures = new ArrayList<Future<Integer>>();

        for (int i = 0; i < 5; i++) {

            int finalI = i; //Note : Variable used in lambda expression should be final or effectively final

            var future = executorService.submit(() -> DummyTask.runTask(finalI));
            //Note: executorService.execute() method is void and doesn’t give any possibility to get the result of a task’s execution or to check the task’s status (is it running):

            futures.add(future);
        }

        //wait for all to finish
        for (var future : futures) {
                var result = future.get();
            //Note: if you get() another time, it just returns result; do not execute operation again.
        }

        //Note: an app could reach its end but not be stopped because a waiting ExecutorService will cause the JVM to keep running.
        executorService.shutdown();
    }
}