package unit13.assignment2;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TinyWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        while(true) {
            Socket client = server.accept();
            Thread clientThread = new Thread(() -> {
                try {
                    handleClient(client);
                } catch (IOException e) {}
            });
            clientThread.start();
        }
    }

    private static void handleClient(Socket client) throws IOException {
        Scanner sc = new Scanner(client.getInputStream());
        OutputStream os = client.getOutputStream();
        int newLineCounter = 0;
        while(newLineCounter < 3) {
            String line = sc.nextLine();
            System.out.println(line);
            newLineCounter++;
        }

        String httpResponse = "HTTP/1.1 200 OK\r\n" + "Content-Length: 12\r\n" + "Content-Type: text/plain; charset=utf-8\r\n\r\n" + "Hello World!\r\n";
        os.write(httpResponse.getBytes());
        os.flush();
        client.close();
    }
}
