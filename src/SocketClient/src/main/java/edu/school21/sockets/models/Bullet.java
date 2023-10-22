package edu.school21.sockets.models;

import edu.school21.sockets.utils.Field;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
public class Bullet {
    private final Image imageUp = new Image("Bullet.png", Field.BULLET_WIDTH, Field.BULLET_HEIGHT, false, false);
    private final Image imageDown = new Image("BulletEnemy.png", Field.BULLET_WIDTH, Field.BULLET_HEIGHT, false, false);
    private final int x;
    private int y;
    private final int direction;

    public Bullet(Tank tank, int direction) {
        this.direction = direction;
        this.x = tank.getX() + Field.TANK_WIDTH/2;
        this.y = tank.getY();
        if (direction == Field.DOWN) {
            this.y = tank.getY() + Field.TANK_HEIGHT - Field.BULLET_HEIGHT;
        }
    }
    public boolean checkCollision(Tank tank) {
        boolean res = false;
        if ((tank.getX() <= x) &&
                ((tank.getX() + Field.TANK_WIDTH) >= (x + Field.BULLET_WIDTH))) {
            if ((direction == Field.UP && y <= tank.getY() + Field.TANK_HEIGHT) ||
                    (direction == Field.DOWN && tank.getY() <= y + Field.BULLET_HEIGHT)) {
                tank.setHealth(tank.getHealth() - Field.DAMAGE);
                if (direction == Field.UP) {
                    y = 0;
                } else {
                    y = Field.HEIGHT;
                }
                res = true;
            }
        }
        return res;
    }
    public void move() {
        this.y += direction * Field.BULLET_SPEED;
    }
    public void draw(GraphicsContext gc) {
        if (direction == Field.UP) {
            gc.drawImage(imageUp, x, y);
        } else {
            gc.drawImage(imageDown, x, y);
        }
    }
    public boolean isOut() {
        return (direction == Field.UP && y < Field.TANK_HEIGHT) ||
                (direction == Field.DOWN && y > Field.HEIGHT - Field.TANK_HEIGHT);
    }

    public int getDirection() {
        return direction;
    }
}
