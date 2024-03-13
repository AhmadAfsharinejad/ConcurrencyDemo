package ir.ahmad;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class semaphoreExecution {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Hello world!");
        new semaphoreExecution().run();
    }

    public void run() throws InterruptedException, ExecutionException {

        Semaphore semaphoreObject = new Semaphore(3);

        var futures = new ArrayList<CompletableFuture<?>>();

        for (int i = 0; i < 20; ++i)
        {

            int finalI = i;
            var future = CompletableFuture.runAsync(() -> {

                try {

                    semaphoreObject.acquire();

                    print(finalI);

                    semaphoreObject.release();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0])).get();
    }

    private void print(int documentToPrint) throws InterruptedException {
        System.out.println("Printing document: " + documentToPrint);
        Thread.sleep(2000);
    }
}
