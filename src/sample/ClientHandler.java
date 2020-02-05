package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    ArrayList<ClientHandler> clients;
    private Socket         client;
    private BufferedReader in;
    private PrintWriter    out;
    private int            id;

    ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients, int id) throws IOException {
        this.client = clientSocket;
        this.id = id;
        this.clients = clients;
        in  = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter   (client.getOutputStream(), true);
    }

    @Override
    public void run() {
        try{
            while(true) {

                String data = in.readLine();
                outToAll(data);
                }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void outToAll(String data) {
        for(ClientHandler c : clients) {
            if(c.id != this.id) {
                c.out.println(data);
            }
        }
    }
}
