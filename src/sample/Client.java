
package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{

    private String gameData;
    private String serverResponse;

    BufferedReader in;
    PrintWriter out;

    public void setData(String data) {
        this.gameData = data;
    }

    public String getData() {
        return serverResponse;
    }

    Client() throws IOException {

        String ip = "192.168.0.21";
        int port = 5002;

        Socket s = new Socket(ip, port);

        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new PrintWriter(s.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while(true) {
                out.println(gameData);

                serverResponse = in.readLine();
                //System.out.println("user "+serverResponse+" ");
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}

