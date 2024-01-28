package hablamos.util;

public class MultithreadingManager {
    // Example: Run a time-intensive calculation in a separate thread
    public static void runTimeIntensiveTask(Runnable task) {
        Thread thread = new Thread(task);
        thread.start();
    }

    // Example: Wait for a specific thread to finish its execution
    public static void waitForThread(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Handle InterruptedException, log, or throw a custom exception
            e.printStackTrace();
        }
    }
}
