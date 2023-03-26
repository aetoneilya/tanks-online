package edu.school21.sockets.models;

import edu.school21.sockets.utils.Field;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
public class Tank {
    private final Image image;
    private int health = 100;
    private int x;
    private final int y;

    public Tank(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }
    public void moveLeft() {
        if (x - Field.SPEED >= Field.BORDER_LEN) {
            x -= Field.SPEED;
        } else {
            x = 20;
        }
    }
    public void moveRight() {
        if (x + Field.SPEED <= Field.WIDTH - Field.TANK_WIDTH - Field.BORDER_LEN) {
            x += Field.SPEED;
        } else {
            x = Field.WIDTH - Field.TANK_WIDTH - Field.BORDER_LEN;
        }
    }
    public void shoot() {
        System.out.println("Shooting!");
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
