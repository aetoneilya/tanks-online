package edu.school21.server.client;

public class MockTankController implements TankController{
    @Override
    public void left() {
        System.out.println("Enemy tank move left");
    }

    @Override
    public void right() {
        System.out.println("Enemy tank move right");
    }

    @Override
    public void shoot() {
        System.out.println("Enemy tank shoot");
    }

    @Override
    public void giveUp() {
        System.out.println("Enemy tank gave up");
    }
}
