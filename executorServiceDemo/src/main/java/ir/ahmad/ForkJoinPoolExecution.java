package ir.ahmad;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ForkJoinPoolExecution {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Hello world!");
        new ir.ahmad.ForkJoinPoolExecution().run();
    }

    public void run() throws ExecutionException, InterruptedException {

        ForkJoinPool forkJoinPool
                = ForkJoinPool.commonPool();


        var futures = new ArrayList<Future<Integer>>();

        for (int i = 0; i < 5; i++) {

            int finalI = i; //Note : Variable used in lambda expression should be final or effectively final

            var future = forkJoinPool.submit(() -> DummyTask.runTask(finalI));

            futures.add(future);
        }

        //wait for all to finish
        for (var future : futures) {
            var result  = future.get();
        }

        //Note: check following note if you must do a blocking operation (like calling rest api) inside task which you submit in forkJoinPool:
        //The method ForkJoinPool.managedBlock() is typically called within the execution of a task that is managed by a ForkJoinPool.
        // It's used when a task needs to perform a blocking operation that could potentially stall the thread.
        // By using managedBlock, the task cooperatively hands off control to the ForkJoinPool,
        // allowing it to continue processing other tasks while waiting for the blocking operation to complete.

        forkJoinPool.shutdown();
    }
}