package sample;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;

public class Peer {

    private int x = 50;
    private int y = 50;
    private String data;
    private Client client;
    private Color color;

    GraphicsContext gc;

    Peer(Client client, GraphicsContext gc, Color color) {
        this.gc = gc;
        this.client = client;
        this.color = color;
    }

    public void tick(GraphicsContext gc) {

        try {
            parseData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("x: "+x+" y: "+y);

        gc.setFill(color);
        gc.fillOval(x,y,40,40);

    }

    private void parseData() throws IOException {


            String temp = client.getData();

            int div = temp.indexOf(" ");

            ArrayList<Character> X = new ArrayList<>();
            for (int i = 1; i < div; i++) {
                X.add(temp.charAt(i));
            }

            ArrayList<Character> Y = new ArrayList<>();
            for (int i = div + 2; i < temp.length(); i++) {
                Y.add(temp.charAt(i));
            }

            System.out.println("x: "+X.toString()+" y: "+Y.toString());

            if(!X.isEmpty() && !Y.isEmpty()) {

                x = Integer.parseInt(X.toString()
                        .replace("[", "")
                        .replace("]", "")
                        .replace(",", "")
                        .replace(" ", ""));

                y = Integer.parseInt(Y.toString()
                        .replace("[", "")
                        .replace("]", "")
                        .replace(",", "")
                        .replace(" ", ""));

            } else {
                x = 20;
                y = 20;
            }
    }

}
