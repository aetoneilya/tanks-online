package edu.school21.sockets.client;

import edu.school21.sockets.models.Tank;
import edu.school21.sockets.utils.Field;

import java.util.Scanner;

public class TankControllerImpl implements TankController {
    private Tank tank;

    private Runnable readyCallback;

    private Integer bulletsShotEnemy;

    private Integer bulletsHitsEnemy;

    public TankControllerImpl(Tank tank, Runnable readyCallback, Integer bulletsShotEnemy, Integer bulletsHitsEnemy) {
        this.tank = tank;
        this.readyCallback = readyCallback;
        this.bulletsShotEnemy = bulletsShotEnemy;
        this.bulletsHitsEnemy = bulletsHitsEnemy;
    }

    @Override
    public void left() {
        tank.moveRight();
    }

    @Override
    public void right() {
        tank.moveLeft();
    }

    @Override
    public void shoot() {
        tank.shoot(Field.DOWN);
    }

    @Override
    public void giveUp() {
        tank.setHealth(0);
    }

    @Override
    public void readyToStart() {
        readyCallback.run();
    }

    @Override
    public void setEnemyStats(String input) {
        Scanner scanner = new Scanner(input);
        scanner.next();
        bulletsShotEnemy = scanner.nextInt();
        bulletsHitsEnemy = scanner.nextInt();
        scanner.close();
    }
}
