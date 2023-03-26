package edu.school21.sockets.client;

import edu.school21.sockets.models.Tank;
import edu.school21.sockets.utils.Field;

public class MockTankController implements TankController{
    private Tank tank;

    public MockTankController() {
    }

    public MockTankController(Tank tank) {
        this.tank = tank;
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
}
