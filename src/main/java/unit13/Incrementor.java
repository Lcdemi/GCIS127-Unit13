package unit13;

public class Incrementor {
    private int count;

    public Incrementor() {
        this.count = 0;
    }

    public int getCount() {
        return this.count;
    }

    public void increment() {
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        Incrementor inc = new Incrementor();
        for(int i = 0; i < 10; i++) {
            Runnable runner = new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i < 100000; i++) {
                        synchronized(inc) {
                            inc.increment();
                        }
                    }
                }
            };
            Thread thread = new Thread(runner);
            thread.start();
        }

        Thread.sleep(2000);
        System.out.println(inc.getCount());
    }
}
