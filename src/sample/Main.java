package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    private Player player;

    //temp
    private Peer player2;

    int STAGEWIDTH  = 500;
    int STAGEHEIGHT = 500;

    private GraphicsContext gc;
    private Canvas c;

    boolean running = true;

    private Client client;

    @Override
    public void start(Stage stage) throws IOException {
        Pane root = new Pane();
        c = new Canvas(STAGEWIDTH, STAGEHEIGHT);
        gc = c.getGraphicsContext2D();
        root.getChildren().add(c);
        Scene scene = new Scene(root, STAGEWIDTH, STAGEHEIGHT);

        player = new Player(450, 200, Color.BLUE);

        //NETWORK
        client = new Client();
        new Thread(client).start();

        //temp
        player2 = new Peer(client, gc, Color.RED);

        new AnimationTimer() {

            long lastTick = 0;

            @Override
            public void handle(long now) {

                if(running == true) {
                    stage.setScene(scene);
                    if(lastTick == 0) {
                        lastTick = now;
                        tick(gc, scene);
                        return;
                    }
                    if(now-lastTick > 1000000000/60) {
                        lastTick = now;
                        tick(gc, scene);
                    }
                }
            }

        }.start();
        stage.setTitle("Circle game");
        stage.setScene(scene);
        stage.show();
    }

    void tick(GraphicsContext gc, Scene scene) {

        setControls(scene);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 500, 500);

        player.tick(gc);
        client.setData("x"+player.getX()+" y"+player.getY());

        //temp
        player2.tick(gc);
    }
    void setControls(Scene scene){

        scene.addEventFilter(KeyEvent.KEY_PRESSED, keys ->
        {
            switch (keys.getCode()) {
                case W:
                    player.setVelY(-5);
                    break;
                case A:
                    player.setVelX(-5);
                    break;
                case S:
                    player.setVelY(5);
                    break;
                case D:
                    player.setVelX(5);
                    break;
            }
        });

        scene.addEventFilter(KeyEvent.KEY_RELEASED, keys ->
        {
            switch (keys.getCode()) {
                case W:
                    player.setVelY(0);
                    break;
                case A:
                    player.setVelX(0);
                    break;
                case S:
                    player.setVelY(0);
                    break;
                case D:
                    player.setVelX(0);
                    break;
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}