package ir.ahmad.task;

import ir.ahmad.DummyTask;

public class TaskThread implements Runnable {

    private final int index;
    private int result;

    public TaskThread(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        result = DummyTask.runTask(index);
    }

    public int getResult() {
        return result;
    }
}
