package ir.ahmad;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Hello world!");
        new Main().run();
    }

    public void run() throws InterruptedException, ExecutionException {

        //Note: By default, CompletableFuture uses the ForkJoinPool.commonPool().
        // You can pass your ExecutiveService to managing thread pool.
        //Note: Use runAsync for task returns void
        var buyingBreadFuture = CompletableFuture.supplyAsync(() -> {

            //test error:
            var showError = false;
            if (showError) {
                throw new RuntimeException("asd");
            }

            try {
                return buyBread();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        //Note: The thenRun() is used when you want to perform a side effect action when a CompletableFuture completes, without needing access to the result of the computation.
        buyingBreadFuture.thenRun(() -> System.out.println("I Buy Bread."));

        //Note: The thenAccept() is used when you want to perform an action with the result of a CompletableFuture but don't need to produce a new result.
        buyingBreadFuture.thenAccept((bread) -> System.out.println("I buy this:" + bread + "."));

        //Note: The thenApply() is used when you want to apply a function to the result of a CompletableFuture and produce a new result. --> like map function
        buyingBreadFuture.thenApply((bread) -> "put bread in bag");

        //Note: The handle() is used to handle both the result of a computation and any exceptions that may occur during its execution
        buyingBreadFuture.handle((bread, throwable) -> {
            if (throwable != null) {
                System.out.println("We got this error in 'buyingBreadFuture':" + throwable.getMessage());
                throw new CompletionException(throwable);
            }

            return bread;
        });


        //Note: The thenCompose() is used when a CompletableFuture’s result is dependent on another CompletableFuture
        var slicedBreadFuture = buyingBreadFuture.thenCompose((bread) -> CompletableFuture.supplyAsync(() -> {
            try {
                return sliceBread(bread);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));


        //Note: The thenCombine() is used when you want to combine two independent CompletableFutures
        var buyingButterFuture = CompletableFuture.supplyAsync(() -> {

            try {
                return buyButter();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        var finalFuture = slicedBreadFuture.thenCombine(buyingButterFuture, (bread, butter) -> String.format("We have '%1$s' slice of bread and '%2$s'.", bread, butter));

        System.out.println(finalFuture.get());

        //Note: There are also async versions of these functions (thenRunAsync, thenAcceptAsync, ...).
        //The non-async versions will run synchronously in the thread the previous stage was run in.
        //The async versions can take an executor as an extra argument allowing you to run different stages in different thread pools.

        //Note: The CompletableFuture.allOf() Returns a new CompletableFuture that is completed when all of the given CompletableFutures complete.
        //Note: Use CompletableFuture.anyOf() Returns a new CompletableFuture that is completed when any of the given CompletableFutures complete.
    }

    private String buyBread() throws InterruptedException {
        Thread.sleep(1000);

        return "Baguette";
    }

    private int sliceBread(String bread) throws InterruptedException {
        Thread.sleep(1000);

        return bread.length();
    }

    private String buyButter() throws InterruptedException {
        Thread.sleep(1000);

        return "Mihan Butter";
    }
}