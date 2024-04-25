package unit13;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class HttpGet {
    
    public static void main(String[] args) throws IOException {
        String get = "GET / HTTP/1.1\r\n" + "Host: www.google.com\r\n" + "Connection: close\r\n\r\n";
        Socket socket = new Socket("localhost", 8080);
        OutputStream out = socket.getOutputStream();
        byte[] request = get.getBytes();
        out.write(request);
        out.flush();

        InputStream in = socket.getInputStream();
        Scanner sc = new Scanner(in);
        while(sc.hasNext()) {
            System.out.println(sc.nextLine());
        }

        sc.close();
        socket.close();
    }
}
