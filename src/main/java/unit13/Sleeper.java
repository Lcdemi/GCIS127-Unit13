package unit13;

import java.util.Random;

public class Sleeper implements Runnable {
    private int seconds;

    public Sleeper(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public void run() {
        try {
            System.out.println("About to sleep...");
            Thread.sleep(seconds * 1000);
            System.out.println("Im Awake!");
        } catch (InterruptedException e) {}
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[5];
        Random RNG = new Random();
        for(int i = 0; i < threads.length; i++) {
            int time = RNG.nextInt(10) + 1;
            Sleeper sleeper = new Sleeper(time);
            Thread sleeperThread = new Thread(sleeper);
            sleeperThread.start();
            threads[i] = sleeperThread;
        }

        for(Thread thread : threads) {
            thread.join();
        }
        System.out.println("Everyone is Awake!");
    }
}
