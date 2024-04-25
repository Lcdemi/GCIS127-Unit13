package unit13;

public class Printer extends Thread {
    private String written;

    public Printer(String written) {
        this.written = written;
    }

    @Override
    public void run() {
        // String[] tokens = written.split(" "); for splitting strings not splitting chars
        // for(int i = 0; i < tokens.length; i++) {
        //     System.out.println(tokens[i]);
        // }
        for(char c : written.toCharArray()) {
            System.out.println(c);
        }
    }

    public static void main(String[] args) {
        Printer printer = new Printer("Hello World!");
        printer.start();
    }
}
