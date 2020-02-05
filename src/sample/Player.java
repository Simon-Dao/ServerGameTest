package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player {
    private int x;
    private int y;
    public int velX = 0;
    public int velY = 0;
    private Color fill;

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void setVelX(int v) {
        this.velX = v;
    }

    void setVelY(int v) {
        this.velY = v;
    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    Player(int x, int y, Color fill) {
        this.x = x;
        this.y = y;
        this.fill = fill;

    }

    void tick(GraphicsContext gc) {

        x += velX;
        y += velY;

        gc.setFill(fill);
        gc.fillOval(x, y, 40, 40);
    }
}
