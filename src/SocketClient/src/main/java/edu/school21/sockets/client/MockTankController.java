package edu.school21.sockets.client;

import edu.school21.sockets.models.Tank;

public class MockTankController implements TankController{
    private Tank tank;

    public MockTankController() {
    }

    public MockTankController(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void left() {
        tank.moveLeft();;
    }

    @Override
    public void right() {
        tank.moveRight();
    }

    @Override
    public void shoot() {
        tank.shoot();
    }

    @Override
    public void giveUp() {
        tank.setHealth(0);
    }
}
