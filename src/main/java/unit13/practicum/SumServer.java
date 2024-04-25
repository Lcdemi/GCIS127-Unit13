package unit13.practicum;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SumServer {
    private ServerSocket server;
    private static int sum;

    public SumServer(int port) throws IOException {
        this.server = new ServerSocket(port);
        while(true) {
            Socket client = server.accept();
            Duplexer duplexer = new Duplexer(client);
            Thread clientThread = new Thread(() -> {
                try {
                    handle(duplexer);
                } catch (IOException e) {}
            });
            clientThread.start();
        }
    }

    public void handle(Duplexer duplexer) throws IOException {
        String response = duplexer.read();
        System.out.println("Receieved number: " + response);
        while (!response.equals("0")) {
            incrementSum(Integer.parseInt(response));
            duplexer.send("" + sum);
            System.out.println("Sent sum: " + sum);
            response = duplexer.read();
            System.out.println("Receieved number: " + response);
        }
        duplexer.send("" + sum);
        System.out.println("Sent sum: " + sum);
        duplexer.close();
    }

    public synchronized void incrementSum(int num) {
        sum += num;
    }

    public static void main(String[] args) throws IOException {
        new SumServer(9999);
    }
}


/*
 * 1. Examine the starter code, "Duplexer.java" and "SumClient.java", provided 
in the practicum package. Do not modify the starter code. You will write a 
server-side program that interacts with the SumClient. 
    (a) The server keeps track of the sum of the numbers received from the client
    and responds with the current sum.
    (b) When the server receives a zero, it should send the current sum and 
    close the connection.
     
2. Create a new Java class named "SumServer.java" that handles a single client. 
    (a) The main method should not include all of the code. 
    (b) While the details of implementation may vary, you should declare necessary 
    fields and initialize them properly in a constructor.
    (b) Add a method, named handle or something else, which handles all communication 
    with the connected client. 
    (c) For debugging purposes, print the received or sent number to the standard output 
    whenever the server receives or sends it.
    (c) Run the server and client to verify that they work correctly. You may assume 
    that the user always provides a valid input, i.e., an integer number.
   
3. Update the server to support multi-threading. 
    (a) The server should be capable of handling multiple concurrent clients.
    (a) The server maintains the total sum of the numbers received from ALL 
    clients and responds to the respective client with the current total.
    (c) Make sure that your server is thread safe.  
    (d) Test the updated server with at least two clients. A sequence diagram 
    is provided in practicum.png. 
 */
