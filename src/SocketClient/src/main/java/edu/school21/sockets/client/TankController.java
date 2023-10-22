package edu.school21.sockets.client;

public interface TankController {
    void left();
    void right();
    void shoot();
    void giveUp();
    void readyToStart();
    void setEnemyStats(String input);
}
