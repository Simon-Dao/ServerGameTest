package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private BufferedReader in;
    private PrintWriter out;
    private static final int port = 5002;
    private int count = 0;
    private static int THREADCOUNT = 3;
    private static ExecutorService pool = Executors.newFixedThreadPool(THREADCOUNT);

    private ArrayList<ClientHandler> clients = new ArrayList<>();

    Server() throws IOException {

        ServerSocket ss = new ServerSocket(port);

        while(true) {
            if(count <= THREADCOUNT) {
                System.out.println("[SERVER] is waiting for connection...");
                Socket client = ss.accept();
                count++;
                System.out.println("[SERVER] user " + count + " connected");

                ClientHandler clientThread = new ClientHandler(client, clients,count);
                clients.add(clientThread);
                pool.execute(clientThread);

            } else if(count > THREADCOUNT){
                System.out.println("full");
            }
        }
    }

    public static void main(String[] args) throws IOException {new Server();}
}
