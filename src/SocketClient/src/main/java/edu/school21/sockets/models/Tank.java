package edu.school21.sockets.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
public class Tank {
    private int SPEED = 5;
    private Image image;
    private int health = 100;
    private int x;
    private int y;

    public Tank(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }
    public void moveLeft() {
        if (x - SPEED >= 20) {
            x -= SPEED;
        } else {
            x = 20;
        }
    }
    public void moveRight() {
        if (x + SPEED <= 1042 - 20 - 50) {
            x += SPEED;
        } else {
            x = 1042 - 20;
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
