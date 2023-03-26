package edu.school21.sockets.models;

import edu.school21.sockets.utils.Field;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

public class Tank {
    private final Image image;
    private int health = 100;
    private int x;
    private final int y;
    private final List<Bullet> bullets;

    public Tank(Image image, int x, int y, List<Bullet> bullets) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.bullets = bullets;
    }
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }
    public void moveLeft() {
        if (x - Field.SPEED >= Field.BORDER_LEN) {
            x -= Field.SPEED;
        } else {
            x = Field.BORDER_LEN;
        }
    }
    public void moveRight() {
        if (x + Field.SPEED <= Field.WIDTH - Field.TANK_WIDTH - Field.BORDER_LEN) {
            x += Field.SPEED;
        } else {
            x = Field.WIDTH - Field.TANK_WIDTH - Field.BORDER_LEN;
        }
    }
    public void shoot(int direction) {
        System.out.println("Shooting!");
        bullets.add(new Bullet(this, direction));
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
