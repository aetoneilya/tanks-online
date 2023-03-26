package edu.school21.sockets.client;

import edu.school21.sockets.models.Tank;
import edu.school21.sockets.utils.Field;

public class TankControllerImpl implements TankController {
    private Tank tank;

    private Runnable readyCallback;

    public TankControllerImpl(Tank tank, Runnable readyCallback) {
        this.tank = tank;
        this.readyCallback = readyCallback;
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
}
