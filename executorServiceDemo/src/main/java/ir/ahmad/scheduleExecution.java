package ir.ahmad;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class scheduleExecution {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        new ir.ahmad.scheduleExecution().run();
    }

    public void run() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.schedule(this::scheduledTask, 5, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(this::fixeScheduledTask, 0, 1, TimeUnit.SECONDS);
    }

    private void scheduledTask() {
        System.out.println("I am scheduledTask!");
    }

    private void fixeScheduledTask() {
        System.out.println("I am fixeScheduledTask!");
    }
}
