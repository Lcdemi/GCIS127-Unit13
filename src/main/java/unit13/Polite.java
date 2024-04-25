package unit13;

public class Polite implements Runnable {
    private final String message;
    private final Object lock;

    public Polite(String message, Object lock) {
        this.message = message;
        this.lock = lock;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {}
            synchronized(lock) {
                System.out.println(message);
                lock.notifyAll();
                try {
                    lock.wait();
                } catch (InterruptedException e) {}
            }
        }
    }

    public static void main(String[] args) {
        Object obj = new Object();
        Polite message1 = new Polite("Nuh Uh", obj);
        Polite message2 = new Polite("Ya Huh", obj);
        new Thread(message1).start();
        new Thread(message2).start();
    }
}
