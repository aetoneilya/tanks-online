package edu.school21.sockets.app;

import edu.school21.sockets.models.Tank;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class TanksApplication extends Application {
    public static final int WIDTH = 1042;
    public static final int HEIGHT = 1042;
    public static final int TANK_WIDTH = 50;
    public static final int TANK_HEIGHT = 60;
    public static Canvas canvas;

    public static Tank player;
    public static Tank enemy;
    public static GraphicsContext gc;
    public static AnimationTimer animationTimer;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Tanks!");
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DARKRED);

        Image background = new Image("field.jpg", WIDTH, HEIGHT, false, false);
        stage.show();
        player = new Tank(new Image("BottomTank.png", TANK_WIDTH, TANK_HEIGHT, false, false),
                20, HEIGHT - 20 - TANK_HEIGHT);
        enemy = new Tank(new Image("TopTank.png", TANK_WIDTH, TANK_HEIGHT, false, false),
                WIDTH - 20 - TANK_WIDTH, 20 );

        scene.setOnKeyPressed(event -> {
            updteTank(player, event.getCode().toString());
            // send action info to server
        });

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    //if hp == 0 game over
                    gc.clearRect(0, 0, WIDTH, HEIGHT);
                    gc.drawImage(background, 0, 0);
                    player.draw(gc);
                    enemy.draw(gc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        animationTimer.start();

    }
    public void updteTank(Tank tank, String code) {
        switch (code) {
            case "LEFT":
                System.out.println("Left");
                tank.moveLeft();
                break;
            case "RIGHT":
                System.out.println("Right");
                tank.moveRight();
                break;
            case "SPACE":
                System.out.println("Shoot");
                // shoot
                break;
            default:
                System.out.println(code);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
